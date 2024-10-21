package Enemies;

import Builders.FrameBuilder;
import Enemies.DinosaurEnemy.DinosaurState;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.EnemyState;
import Level.Map;
import Level.MapEntity;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.awt.Color;

import java.util.HashMap;

// This class is for the green dinosaur enemy that shoots fireballs
// It walks back and forth between two set points (startLocation and endLocation)
// Every so often (based on shootTimer) it will shoot a Fireball enemy
public class FireflyEnemy extends Enemy {

    private float gravity = 0f;
    protected float movementSpeed = 1f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    // timer is used to determine how long dinosaur freezes in place before shooting fireball
    protected int shootWaitTimer;

    // timer is used to determine when a fireball is to be shot out
    protected int shootTimer;

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    
    protected DinosaurState dinosaurState;
    protected DinosaurState previousDinosaurState;
    

    private Point startPoint;
    private boolean webbed;

    public FireflyEnemy(Point startLocation, Direction facingDirection, Map map) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("FireflySpriteSheetDraft1.png"), 128, 87), "WALK_UP", 1);
        this.startPoint = startLocation;
        this.startFacingDirection = facingDirection;
        startPoint = startLocation;
        webbed = false;

        dinosaurState = DinosaurState.WALK;
        previousDinosaurState = dinosaurState;

        this.initialize();
    }

 
    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_UP";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_DOWN";
        }
        airGroundState = AirGroundState.AIR;
    }

    @Override
    public void update(Player player) {
        float moveAmountX = 0;
        float moveAmountY = 0;

        if (this.enemyState == EnemyState.DEAD) {
            
            if (!hasBeenKilled) {
                hasBeenKilled = true;
                this.setY(this.y + this.getHeight()/4);
            }
            
            if (this.facingDirection == Direction.LEFT) {
                this.currentAnimationName = "DEAD_LEFT";
            } else {
                this.currentAnimationName = "DEAD_RIGHT";
            }
            gravity = 1f;
            moveAmountY += gravity;
            moveYHandleCollision(moveAmountY);
            super.update(player);
            return;
        } else {

        // add gravity (if in air, this will cause bug to fall)
        moveAmountY += gravity;
        

             // if shoot timer is up and dinosaur is not currently shooting, set its state to SHOOT
             if (shootWaitTimer == 0 && dinosaurState != DinosaurState.SHOOT_WAIT) {
                dinosaurState = DinosaurState.SHOOT_WAIT;
             }
             else {
                shootWaitTimer--;
             }
        


        // if dinosaur is walking, determine which direction to walk in based on facing direction
        if (dinosaurState == DinosaurState.WALK && !webbed) {
             //check if movement bounds have been reached -> turn around
            if (this.getLocation().y >= (startPoint.y + 3*map.getMapTile(1, 1).getY())){
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_DOWN";
            } else if(this.getLocation().y<=(startPoint.y - 3*map.getMapTile(1, 1).getY())){
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_UP";
            }

            // if on ground, walk forward based on facing direction
            if (airGroundState == AirGroundState.AIR) {
                if (facingDirection == Direction.RIGHT) {
                    moveAmountY += movementSpeed;
                } else {
                    moveAmountY -= movementSpeed;
                }
            }
        }

        // if dinosaur is waiting to shoot, it first turns read for a set number of frames
        // after this waiting period is over, the fireball is actually shot out
        if (dinosaurState == DinosaurState.SHOOT_WAIT && !webbed) {
            if (previousDinosaurState == DinosaurState.WALK) {
                shootTimer = 30;
                boolean playerToRight = player.getX()>=this.x;
                currentAnimationName = playerToRight ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer == 0) {
                dinosaurState = DinosaurState.SHOOT;
            }
            else {
                shootTimer--;
            }
        }

        boolean playerInRange = false;
        if(player.getX()>=this.x-400 && player.getX()<=this.x+400){ //check that player is within range
            playerInRange = true;
        }

        // this is for actually having the dinosaur spit out the fireball
        if (dinosaurState == DinosaurState.SHOOT && playerInRange && !webbed) {
            // define where fireball will spawn on map (x location) relative to dinosaur enemy's location
            // and define its movement speed
            int fireballX;
            float movementSpeed;
            boolean playerToRight = player.getX()>=this.x;
            if (playerToRight) {
                fireballX = Math.round(getX()) + getWidth();
                movementSpeed = 1.5f;
            } else {
                fireballX = Math.round(getX() - 21);
                movementSpeed = -1.5f;
            }

            // define where fireball will spawn on the map (y location) relative to dinosaur enemy's location
            int fireballY = Math.round(getY()) + 4;

            // create Fireball enemy
            FireflyAttack fireball = new FireflyAttack(new Point(fireballX, fireballY), movementSpeed, 120, "FireflyAttackDraft1.png", 64, 64);

            map.addProjectile(fireball);

            // change dinosaur back to its WALK state after shooting, reset shootTimer to wait a certain number of frames before shooting again
            dinosaurState = DinosaurState.WALK;

            // reset shoot wait timer so the process can happen again (dino walks around, then waits, then shoots)
            shootWaitTimer = 70;
        }

        // move bug
        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);
        
        super.update(player);

        previousDinosaurState = dinosaurState;
        }

    }

    // Getter method to return movement speed of dinosaur
    @Override
    public float getOriginalMovementSpeed() {
        return originalMovementSpeed;
    }

    // Setter method to set temporary movement speed of dinosaur
    @Override
    public void setMovementSpeed(float movementSpeed) {
        if(movementSpeed < .5f){
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WEB_RIGHT";
            } else {
                currentAnimationName = "WEB_LEFT";
            }
            webbed = true;
        } else{
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_UP";
            } else {
                currentAnimationName = "WALK_DOWN";
            }
            webbed = false;
        }
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction,  MapEntity entityCollidedWith) {
        // if bug has collided into something while walking forward,
        // it turns around (changes facing direction)
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_DOWN";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_UP";
            }
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if bug is colliding with the ground, change its air ground state to GROUND
        // if it is not colliding with the ground, it means that it's currently in the air, so its air ground state is changed to AIR
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_UP";
                //airGroundState = AirGroundState.GROUND;
            } else {
                //facingDirection = Direction.LEFT;
                //currentAnimationName = "WALK_DOWN";
                airGroundState = AirGroundState.AIR;
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_DOWN", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(.75f)
                            .withBounds(1, 1, 126, 80)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()**/
            });

            put("WALK_UP", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withScale(.75f)
                            .withBounds(1, 1, 126, 80)
                            .build(),
                            /** 
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()**/
            });

            put("SHOOT_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                             .withScale(.75f)
                             .withBounds(1, 1, 126, 80)
                            .build(),
            });

            put("SHOOT_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(1, 0))
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                             .withBounds(1, 1, 126, 80)
                            .build(),
            });

            put("WEB_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(2, 0), 8)
                        .withScale(.75f) 
                        .withBounds(1, 1, 126, 80)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });
            put("WEB_RIGHT", new Frame[] {
            new FrameBuilder(spriteSheet.getSprite(2, 0), 8)
                    .withScale(.75f)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(1, 1, 126, 80)
                    .build(),
            /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                    .withScale(2)
                    .withBounds(6, 6, 12, 7)
                    .build()*/
             });

             put("DEAD_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withBounds(1, 1, 126, 80)
                        .withImageEffect(ImageEffect.FLIP_VERTICAL)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });

            put("DEAD_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withImageEffect(ImageEffect.FLIP_VERTICAL)
                        .withBounds(1, 1, 126, 80)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });
        }};
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
    /**
    public enum DinosaurState {
        WALK, SHOOT_WAIT, SHOOT
    }*/
}
