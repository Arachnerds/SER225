package Level;

import Enemies.FireflyAttack;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Sound;
import EnhancedMapTiles.DangerousWater;
import GameObject.GameObject;
import GameObject.SpriteSheet;
import NPCs.FancyWalrus;
import Players.WalrusPlayer;
import Projectiles.Raindrop;
import Projectiles.Web;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Player extends GameObject {
    // values that affect player movement
    // these should be set in a subclass
    protected float walkSpeed = 0;
    protected float gravity = 0;
    protected float jumpHeight = 0;
    protected float jumpDegrade = 0;
    protected float terminalVelocityY = 0;
    protected float momentumYIncrease = 0;

    // values used to handle player movement
    protected float jumpForce = 0;
    protected float momentumY = 0;
    protected float moveAmountX, moveAmountY;
    protected float lastAmountMovedX, lastAmountMovedY;

    // values used to keep track of player's current state
    protected PlayerState playerState;
    protected PlayerState previousPlayerState;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;
    protected AirGroundState previousAirGroundState;
    protected LevelState levelState;

    // classes that listen to player events can be added to this list
    protected ArrayList<PlayerListener> listeners = new ArrayList<>();

    // define keys
    protected KeyLocker keyLocker = new KeyLocker();
    protected Key JUMP_KEY = Key.SPACE;
    protected Key MOVE_LEFT_KEY = Key.A;
    protected Key MOVE_RIGHT_KEY = Key.D;
    protected Key CROUCH_KEY = Key.S;
    protected Key CLIMB_KEY = Key.C;
    protected Key SHOOT_KEY = Key.Q;

    //Alternate keys to allow arrow key movement
    protected Key MOVE_LEFT_ALT_KEY = Key.LEFT;
    protected Key MOVE_RIGHT_ALT_KEY = Key.RIGHT;
    protected Key CROUCH_ALT_KEY = Key.DOWN;

    // flags
    protected boolean isInvincible = true; // if true, player cannot be hurt by enemies (good for testing)
    protected Timer exitClimbTimer = new Timer();
    protected boolean climbTimerStarted = false;
    protected boolean hitEnemy = false;
    protected boolean hasKey = false;
    public boolean isSwinging = false;

    //health
    private int playerHealth = 3;
    protected int invFrameCt = 0;

    // Instance variables to determine cooldown of projectile shooting for spider
    private int shootCooldownFrames;
    private final int MAX_COOLDOWN = 80;
    private boolean hasShotWeb = false;

    //Boolean to lock the player in place during basement tutorial
    private boolean inTutorial;
    private boolean phobiaChecked;

    public Player(SpriteSheet spriteSheet, float x, float y, String startingAnimationName) {
        super(spriteSheet, x, y, startingAnimationName);
        facingDirection = Direction.RIGHT;
        airGroundState = AirGroundState.AIR;
        previousAirGroundState = airGroundState;
        playerState = PlayerState.STANDING;
        previousPlayerState = playerState;
        levelState = LevelState.RUNNING;
        shootCooldownFrames = 0;
        inTutorial = false;
        phobiaChecked = false;
    }

    public void update() {
        moveAmountX = 0;
        moveAmountY = 0;

        if(this instanceof WalrusPlayer && phobiaChecked==false){
            ArrayList<NPC> NPCs = map.getNPCs();
            int numNPCs = NPCs.size();
            for(int i = 0;i<numNPCs;i++){
                NPC currentNPC = NPCs.get(i);
                currentNPC.setArachnophobiaEnabled(true);
                map.addNPC(new FancyWalrus(currentNPC.getLocation(), currentNPC.getCurrentAnimationName(), currentNPC.getMessages(), currentNPC.getTextboxOffsetX()));
            }
            phobiaChecked = true;
            
            
        }
        
        // if player is currently playing through level (has not won or lost)
        if (levelState == LevelState.RUNNING) {
            applyGravity();

            if(isInvincible){ //invincibility frames after being hit
                invFrameCt++;
                if(invFrameCt >= 60){
                    isInvincible = false;
                }
            }

            // update player's state and current actions, which includes things like determining how much it should move each frame and if its walking or jumping
            do {
                previousPlayerState = playerState;
                handlePlayerState();
            } while (previousPlayerState != playerState);

            previousAirGroundState = airGroundState;

            // move player with respect to map collisions based on how much player needs to move this frame
            lastAmountMovedX = super.moveXHandleCollision(moveAmountX);
            lastAmountMovedY = super.moveYHandleCollision(moveAmountY);

            handlePlayerAnimation();

            updateLockedKeys();

            // update player's animation
            super.update();
            handleInput();
            updateCooldown();
        }

        // if player has beaten level
        else if (levelState == LevelState.LEVEL_COMPLETED) {
            updateLevelCompleted();
        }

        // if player has lost level
        else if (levelState == LevelState.PLAYER_DEAD) {
            updatePlayerDead();
        }

        //A secret test key that completes the current level
        /* if(Keyboard.isKeyDown(Key.ZERO)){
            this.completeLevel();
        } */
    }

    // add gravity to player, which is a downward force
    protected void applyGravity() {
        moveAmountY += gravity + momentumY;
    }

    // based on player's current state, call appropriate player state handling method
    protected void handlePlayerState() {
        switch (playerState) {
            case STANDING:
                playerStanding();
                break;
            case WALKING:
                playerWalking();
                break;
            case CROUCHING:
                playerCrouching();
                break;
            case JUMPING:
                playerJumping();
                break;
            case CLIMBING:
                playerClimbing();
                break;
        }
    }

    // player STANDING state logic
    protected void playerStanding() {
        // if walk left or walk right key is pressed, player enters WALKING state
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_KEY)||Keyboard.isKeyDown(MOVE_LEFT_ALT_KEY) || Keyboard.isKeyDown(MOVE_RIGHT_ALT_KEY)) {
            if(!inTutorial){
                playerState = PlayerState.WALKING;
            }
            
        }

        // if jump key is pressed, player enters JUMPING state
        else if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed, player enters CROUCHING state
        else if (Keyboard.isKeyDown(CROUCH_KEY)||Keyboard.isKeyDown(CROUCH_ALT_KEY)) {
            playerState = PlayerState.CROUCHING;
        }

        else if (Keyboard.isKeyDown(CLIMB_KEY)) {
            int playerX = Math.round(getBounds().getX1());
            int playerY = Math.round(getBounds().getY1()) + Math.round(getBounds().getHeight() / 2f);
    
            MapTile leftTile = map.getTileByPosition(playerX - 1, playerY);
    
            MapTile rightTile = map.getTileByPosition(playerX + Math.round(getBounds().getWidth()), playerY);
    
            if ((leftTile != null && leftTile.getTileType() == TileType.CLIMBABLE) ||
                (rightTile != null && rightTile.getTileType() == TileType.CLIMBABLE)) {
                playerState = PlayerState.CLIMBING;
            }
        }
    }


    // player WALKING state logic
    protected void playerWalking() {
        // if walk left key is pressed, move player to the left
        if (Keyboard.isKeyDown(MOVE_LEFT_KEY)||Keyboard.isKeyDown(MOVE_LEFT_ALT_KEY)) {
            moveAmountX -= walkSpeed;
            facingDirection = Direction.LEFT;
        }

        // if walk right key is pressed, move player to the right
        else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)||Keyboard.isKeyDown(MOVE_RIGHT_ALT_KEY)) {
            moveAmountX += walkSpeed;
            facingDirection = Direction.RIGHT;
        } else if ((Keyboard.isKeyUp(MOVE_LEFT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_KEY))||(Keyboard.isKeyUp(MOVE_LEFT_ALT_KEY) && Keyboard.isKeyUp(MOVE_RIGHT_ALT_KEY))) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }

        // if crouch key is pressed,
        else if (Keyboard.isKeyDown(CROUCH_KEY)||Keyboard.isKeyDown(CROUCH_ALT_KEY)) {
            playerState = PlayerState.CROUCHING;
        }
    }

    // player CROUCHING state logic
    protected void playerCrouching() {
        // if crouch key is released, player enters STANDING state
        if (Keyboard.isKeyUp(CROUCH_KEY)&&Keyboard.isKeyUp(CROUCH_ALT_KEY)) {
            playerState = PlayerState.STANDING;
        }

        // if jump key is pressed, player enters JUMPING state
        if (Keyboard.isKeyDown(JUMP_KEY) && !keyLocker.isKeyLocked(JUMP_KEY)) {
            keyLocker.lockKey(JUMP_KEY);
            playerState = PlayerState.JUMPING;
        }
    }

    // player JUMPING state logic
    public void playerJumping() {
        // if last frame player was on ground and this frame player is still on ground, the jump needs to be setup
        if ((previousAirGroundState == AirGroundState.GROUND && airGroundState == AirGroundState.GROUND) || (airGroundState == AirGroundState.AIR && hitEnemy)) {

            // sets animation to a JUMP animation based on which way player is facing
            currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";

            // player is set to be in air and then player is sent into the air
            if (hitEnemy) {
                jumpForce = (jumpHeight/3) * 2;
            } else {
                jumpForce = jumpHeight;
            }
            airGroundState = AirGroundState.AIR;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }
        }

        // if player is in air (currently in a jump) and has more jumpForce, continue sending player upwards
        else if (airGroundState == AirGroundState.AIR) {
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }

            // allows you to move left and right while in the air
            if(!inTutorial){
                if (Keyboard.isKeyDown(MOVE_LEFT_KEY)||Keyboard.isKeyDown(MOVE_LEFT_ALT_KEY)) {
                    moveAmountX -= walkSpeed;
                } else if (Keyboard.isKeyDown(MOVE_RIGHT_KEY)||Keyboard.isKeyDown(MOVE_RIGHT_ALT_KEY)) {
                    moveAmountX += walkSpeed;
                }
            }

            // if player is falling, increases momentum as player falls so it falls faster over time
            if (moveAmountY > 0) {
                increaseMomentum();
            }
        }

        // if player last frame was in air and this frame is now on ground, player enters STANDING state
        else if (previousAirGroundState == AirGroundState.AIR && airGroundState == AirGroundState.GROUND) {
            playerState = PlayerState.STANDING;
        }

        hitEnemy = false;

        //if the player was swinging they are allowed a "double jump"
        if(isSwinging &&  !keyLocker.isKeyLocked(JUMP_KEY)&& Keyboard.isKeyDown(Key.SPACE)){
            isSwinging = false;
            //System.out.println("AHHHHHHHHHHHHHHHHHHHH");
            keyLocker.lockKey(Key.SPACE); //Try locking the key in the anchor class instead??
            jumpForce = jumpHeight;
            airGroundState = AirGroundState.AIR;
            if (jumpForce > 0) {
                moveAmountY -= jumpForce;
                jumpForce -= jumpDegrade;
                if (jumpForce < 0) {
                    jumpForce = 0;
                }
            }
    }
    }
    
    
    protected void playerClimbing() {
        int playerX = Math.round(getBounds().getX1());
        int playerY = Math.round(getBounds().getY1()) + Math.round(getBounds().getHeight() / 2f);
        
        MapTile leftTile = map.getTileByPosition(playerX - 1, playerY);
        MapTile rightTile = map.getTileByPosition(playerX + Math.round(getBounds().getWidth()), playerY);
        boolean isNextToClimbable = (leftTile != null && leftTile.getTileType() == TileType.CLIMBABLE) ||
                                    (rightTile != null && rightTile.getTileType() == TileType.CLIMBABLE);
        
        if (Keyboard.isKeyUp(CLIMB_KEY)) {
            playerState = PlayerState.STANDING;
            return;
        }
    
        if (Keyboard.isKeyDown(Key.W)||Keyboard.isKeyDown(Key.UP)) {
            moveAmountY -= walkSpeed;
    
            if (facingDirection == Direction.LEFT) {
                moveAmountX -= walkSpeed;
            } else if (facingDirection == Direction.RIGHT) {
                moveAmountX += walkSpeed;
            }
        }
    
        if (isNextToClimbable) {
            if (climbTimerStarted) {
                climbTimerStarted = false;
                exitClimbTimer.cancel();
                exitClimbTimer = new Timer();
            }
        } else if (!climbTimerStarted) {
            climbTimerStarted = true;
            exitClimbTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!isNextToClimbable) {
                        playerState = PlayerState.STANDING;
                    }
                    climbTimerStarted = false;
                }
            },200);
        }
    }    

    // while player is in air, this is called, and will increase momentumY by a set amount until player reaches terminal velocity
    protected void increaseMomentum() {
        momentumY += momentumYIncrease;
        if (momentumY > terminalVelocityY) {
            momentumY = terminalVelocityY;
        }
    }

    protected void updateLockedKeys() {
        if (Keyboard.isKeyUp(JUMP_KEY)) {
            keyLocker.unlockKey(JUMP_KEY);
        }
    }

    // anything extra the player should do based on interactions can be handled here
    protected void handlePlayerAnimation() {
        if (playerState == PlayerState.STANDING) {
            // sets animation to a STAND animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "STAND_RIGHT" : "STAND_LEFT";

            // handles putting goggles on when standing in water
            // checks if the center of the player is currently touching a water tile
            int centerX = Math.round(getBounds().getX1()) + Math.round(getBounds().getWidth() / 2f);
            int centerY = Math.round(getBounds().getY1()) + Math.round(getBounds().getHeight() / 2f);
            MapTile currentMapTile = map.getTileByPosition(centerX, centerY);
            if (currentMapTile != null && currentMapTile.getTileType() == TileType.WATER) {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "SWIM_STAND_RIGHT" : "SWIM_STAND_LEFT";
            }
        }
        else if (playerState == PlayerState.WALKING) {
            // sets animation to a WALK animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "WALK_RIGHT" : "WALK_LEFT";
        }
        else if (playerState == PlayerState.CROUCHING) {
            // sets animation to a CROUCH animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "CROUCH_RIGHT" : "CROUCH_LEFT";
        }
        else if (playerState == PlayerState.JUMPING) {
            // if player is moving upwards, set player's animation to jump. if player moving downwards, set player's animation to fall
            if (lastAmountMovedY <= 0) {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "JUMP_RIGHT" : "JUMP_LEFT";
            } else {
                this.currentAnimationName = facingDirection == Direction.RIGHT ? "FALL_RIGHT" : "FALL_LEFT";
            }
        }
        else if (playerState == PlayerState.CLIMBING) {
            // sets animation to a CROUCH animation based on which way player is facing
            this.currentAnimationName = facingDirection == Direction.RIGHT ? "CLIMB_RIGHT" : "CLIMB_LEFT";
        }
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) { }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if player collides with a map tile below it, it is now on the ground
        // if player does not collide with a map tile below, it is in air
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                momentumY = 0;
                airGroundState = AirGroundState.GROUND;
            } else {
                playerState = PlayerState.JUMPING;
                airGroundState = AirGroundState.AIR;
            }
        }

        // if player collides with map tile upwards, it means it was jumping and then hit into a ceiling -- immediately stop upwards jump velocity
        else if (direction == Direction.UP) {
            if (hasCollided) {
                jumpForce = 0;
            }
        }
    }

    // other entities can call this method to hurt the player
    public void hurtPlayer(MapEntity mapEntity) {
        if (!isInvincible) {
            // if map entity is an enemy, kill player on touch
            if (mapEntity instanceof Enemy || mapEntity instanceof Raindrop || mapEntity instanceof FireflyAttack) {
                playerHealth--;
                if(playerHealth<= 0){
                    levelState = LevelState.PLAYER_DEAD;
                }
                isInvincible = true;
                invFrameCt = 0;
            }

        }
    }

    public void killPlayer(EnhancedMapTile enhancedMapTile) {
        if (!isInvincible) {
            if (enhancedMapTile instanceof DangerousWater) {
                int damage = Math.min(playerHealth, 3);
                playerHealth -= damage;
    
                if (playerHealth <= 0) {
                    levelState = LevelState.PLAYER_DEAD;
                }
    
                // Activate invincibility frames
                isInvincible = true;
                invFrameCt = 0;
            }
        }
    }    

    public void setIfPlayerHasAttacked(Enemy enemy) {
        enemy.hasBeenAttacked = false;
    }

    public void setHitEnemy(boolean bool) {
        this.hitEnemy = bool;
    }

    // other entities can call this to tell the player they beat a level
    public void completeLevel() {
        levelState = LevelState.LEVEL_COMPLETED;
    }

    // if player has beaten level, this will be the update cycle
    public void updateLevelCompleted() {
        for (PlayerListener listener : listeners) {
                listener.onLevelCompleted();
        }
    }

    // if player has died, this will be the update cycle
    public void updatePlayerDead() {
        // change player animation to DEATH
        if (!currentAnimationName.startsWith("DEATH")) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "DEATH_RIGHT";
            } else {
                currentAnimationName = "DEATH_LEFT";
            }
            super.update();
        }
        // if death animation not on last frame yet, continue to play out death animation
        else if (currentFrameIndex != getCurrentAnimation().length - 1) {
          super.update();
        }
        // if death animation on last frame (it is set up not to loop back to start), player should continually fall until it goes off screen
        else if (currentFrameIndex == getCurrentAnimation().length - 1) {
            if (map.getCamera().containsDraw(this)) {
                moveY(3);
            } else {
                // tell all player listeners that the player has died in the level
                for (PlayerListener listener : listeners) {
                    listener.onDeath();
                }
            }
        }
    }

    // Method called when shoot key is pressed
        // The spider will fire a web projectile based on spider direction and location
        private void shootWebProjectile() {
        int webX;
        float webVelocity;

        if (facingDirection == Direction.RIGHT) {
            webX = Math.round(this.getX()) + (getWidth()/2);
            webVelocity = 5.5f;
        } else {
            webX = Math.round(this.getX());
            webVelocity = -5.5f;
        }

        // define where web will spawn on the map (y location) relative to spider's location
        int webY = Math.round(getY() + getHeight()/3);

        Web web;
        if (facingDirection.equals(Direction.RIGHT)) {
            web = new Web(new Point(webX, webY), webVelocity, 0, 60, 200, "WebRight.png", 7, 9);
        } else {
            web = new Web(new Point(webX, webY), webVelocity, 0, 60, 200, "WebLeft.png", 7, 9);
        }

        // add fireball enemy to the map for it to spawn in the level
        map.addProjectile(web);
        
        shootCooldownFrames = 0; 

        super.update();

    }

    // When shoot key is pressed, fire projectile and lock the shoot key
    public void handleInput() { 
        if (Keyboard.isKeyDown(SHOOT_KEY) && !keyLocker.isKeyLocked(SHOOT_KEY)) {
            shootWebProjectile();
            hasShotWeb = true;
            Sound.playSoundEffect(Sound.SoundEffect.WEB);
            keyLocker.lockKey(SHOOT_KEY);
        }
    }

    // Update the cooldown timer in the update loop, decreases the frames with each loop
    private void updateCooldown() {
        if (shootCooldownFrames < MAX_COOLDOWN && hasShotWeb) {
            shootCooldownFrames++; 
        }

        // Unlock the key once cooldown is over and the key is released
        if (shootCooldownFrames >= MAX_COOLDOWN && Keyboard.isKeyUp(SHOOT_KEY)) {
            keyLocker.unlockKey(SHOOT_KEY);
            hasShotWeb = false;
        }
    }

    public boolean hasShotWeb() {
        return hasShotWeb;
    }

    public int getShootCooldownFrames() {
        return shootCooldownFrames;
    }



    public PlayerState getPlayerState() {
        return playerState;
    }

    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    public AirGroundState getAirGroundState() {
        return airGroundState;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public void setFacingDirection(Direction facingDirection) {
        this.facingDirection = facingDirection;
    }

    public void setLevelState(LevelState levelState) {
        this.levelState = levelState;
    }

    public void setHasKey(boolean hasKey) {
        this.hasKey = hasKey;
    }

    public boolean hasKey() {
        return this.hasKey;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    public void setGravity(float g){
        this.gravity = g;
    }

    public float getMoveAmountY() {
        return moveAmountY;
    }

    // Uncomment this to have game draw player's bounds to make it easier to visualize
    // PLAYER HITBOX
   /*  public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    } */

    public boolean isWalking(){
        return playerState == PlayerState.WALKING;
    }

    public float getWalkSpeed(){
        return walkSpeed;
    }

    public void setWalkSpeed(float speed){
        walkSpeed = speed;
    }

    public void setMomentumY(float f) {
        this.momentumY = f;
    }

   
    public KeyLocker getKeyLocker(){
        return keyLocker;
    }

    public int getPlayerLives(){
        return playerHealth;
    }

    public void setInTutorial(boolean bool){
        inTutorial = bool;
    }
  
}