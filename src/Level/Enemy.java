package Level;

import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.AirGroundState;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.StopWatch;

// This class is a base class for all enemies in the game -- all enemies should extend from it
public abstract class Enemy extends MapEntity {

    protected boolean isFrozen;
    protected int freezeFrames;
    protected int health;
    private float tempMovementSpeed;
    protected boolean hasBeenAttacked = false;
    protected boolean hasBeenKilled = false;
    protected boolean attackCooldownOn = false;
    protected EnemyState enemyState;
    
    
    protected float coolDown;

    public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation, int health) {
        super(x, y, spriteSheet, startingAnimation);
        this.health = health;
        this.enemyState = EnemyState.ALIVE;
    }

    public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public Enemy(float x, float y, Frame[] frames) {
        super(x, y, frames);
    }

    public Enemy(float x, float y, Frame frame) {
        super(x, y, frame);
    }

    public Enemy(float x, float y) {
        super(x, y);
    }


    // Abstract methods so all subclasses must implement, needed for freezing mechanic
    public abstract float getOriginalMovementSpeed();

    public abstract void setMovementSpeed(float movementSpeed);

    @Override
    public void initialize() {
        super.initialize();
    }

    // Updates enemies, checks for player intersection and projectile intersection
    // Death and Freeze mechanics are called
    public void update(Player player) {
        if (player.airGroundState == AirGroundState.GROUND) {
            hasBeenAttacked = false;
        }

        if (isFrozen) {
            freezeFrames--; 
            if (freezeFrames <= 0) {
                isFrozen = false; 
                setMovementSpeed(tempMovementSpeed);
            }
        }
            
        super.update();

        if (this.enemyState != EnemyState.DEAD) {
            if (intersects(player)) {
                touchedPlayer(player);
            }
        }
        
        for (Projectile projectile : map.getProjectiles()) {
            if (intersects(projectile)) {
                projectile.touchedEnemy(this);
            }
        }

        if(hasBeenAttacked){
            coolDown++;
            if(coolDown>=30f){
                attackCooldownOn = false;
            }
        }

    }

    // A subclass can override this method to specify what it does when it touches the player
    public void touchedPlayer(Player player) {

        // if enemy is webbed (frozen) and the player is falling onto the enemy, the enemy will die
        if (isFrozen && player.airGroundState.equals(AirGroundState.AIR) && player.moveAmountY > 0 && !hasBeenAttacked) {
            if (this.health == 1) {
                this.enemyState = EnemyState.DEAD;
            }

            this.health--;
            player.hitEnemy = true;
            hasBeenAttacked = true;
            player.momentumY = 0.1f;
            player.playerJumping();
            attackCooldownOn = true;
            coolDown = 0;

        } else if(!attackCooldownOn &&!isFrozen) {
            player.hurtPlayer(this);
        }
    }

    // Stores the original movespeed of enemy, sets movespeed of enemy to 0 for a number of updated frames
    public void freeze(int frames) {
        this.tempMovementSpeed = getOriginalMovementSpeed();
        this.freezeFrames = frames;
        this.isFrozen = true;
        this.setMovementSpeed(0); // Set movement speed to 0
    }
}
