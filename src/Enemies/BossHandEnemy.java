package Enemies;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.EnemyState;
import Level.MapEntity;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;

// This class is for the green dinosaur enemy that shoots fireballs
// It walks back and forth between two set points (startLocation and endLocation)
// Every so often (based on shootTimer) it will shoot a Fireball enemy
public class BossHandEnemy extends Enemy {

    private float gravity = 1f;
    protected float movementSpeed = 1f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    private Point startPoint;
    private boolean webbed;

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected HandState handState;
    protected HandState previousHandState;
    protected BossMainEnemy enemyMain;

    public BossHandEnemy(Point startLocation, Direction facingDirection, BossMainEnemy head) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("FireflyAttackDraft1.png"), 64, 64), "WALK_RIGHT", 1);
        this.startPoint = startLocation;
        this.webbed = false;
        this.enemyMain = head;
        this.startFacingDirection = facingDirection;
        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();

        handState = HandState.IDLE;
        previousHandState = handState;

        facingDirection = startFacingDirection; //facing direction will be determined by palm
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "IDLE_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "IDLE_LEFT";
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
        }

        //MOST CODE KEPT FOR REEFERENCE, LIKELY UNNECESSARY. FEEL FREE TO REMOVE
        /** 
        // if shoot timer is up and dinosaur is not currently shooting, set its state to SHOOT
        if (shootWaitTimer == 0 && dinosaurState != DinosaurState.SHOOT_WAIT) {
            dinosaurState = DinosaurState.SHOOT_WAIT;
        }
        else {
            shootWaitTimer--;
        }

        // if dinosaur is walking, determine which direction to walk in based on facing direction
        if (dinosaurState == DinosaurState.WALK) {
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
                moveXHandleCollision(movementSpeed);
            } else {
                currentAnimationName = "WALK_LEFT";
                moveXHandleCollision(-movementSpeed);
            }

            // if dinosaur reaches the start or end location, it turns around
            // dinosaur may end up going a bit past the start or end location depending on movement speed
            // this calculates the difference and pushes the enemy back a bit so it ends up right on the start or end location
            if (getX1() + getWidth() >= endBound) {
                float difference = endBound - (getX2());
                moveXHandleCollision(-difference);
                facingDirection = Direction.LEFT;
            } else if (getX1() <= startBound) {
                float difference = startBound - getX1();
                moveXHandleCollision(difference);
                facingDirection = Direction.RIGHT;
            }
        }

        // if dinosaur is waiting to shoot, it first turns read for a set number of frames
        // after this waiting period is over, the fireball is actually shot out
        if (dinosaurState == DinosaurState.SHOOT_WAIT) {
            if (previousDinosaurState == DinosaurState.WALK) {
                shootTimer = 65;
                currentAnimationName = facingDirection == Direction.RIGHT ? "SHOOT_RIGHT" : "SHOOT_LEFT";
            } else if (shootTimer == 0) {
                dinosaurState = DinosaurState.SHOOT;
            }
            else {
                shootTimer--;
            }
        }

        // this is for actually having the dinosaur spit out the fireball
        if (dinosaurState == DinosaurState.SHOOT) {
            // define where fireball will spawn on map (x location) relative to dinosaur enemy's location
            // and define its movement speed
            int fireballX;
            float movementSpeed;
            if (facingDirection == Direction.RIGHT) {
                fireballX = Math.round(getX()) + getWidth();
                movementSpeed = 1.5f;
            } else {
                fireballX = Math.round(getX() - 21);
                movementSpeed = -1.5f;
            }

            // define where fireball will spawn on the map (y location) relative to dinosaur enemy's location
            int fireballY = Math.round(getY()) + 4;

            // create Fireball enemy
            Fireball fireball = new Fireball(new Point(fireballX, fireballY), movementSpeed, 60);

            // add fireball enemy to the map for it to spawn in the level
            map.addEnemy(fireball);

            // change dinosaur back to its WALK state after shooting, reset shootTimer to wait a certain number of frames before shooting again
            dinosaurState = DinosaurState.WALK;

            // reset shoot wait timer so the process can happen again (dino walks around, then waits, then shoots)
            shootWaitTimer = 130;
        }
            */


        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);

        super.update(player);

        previousHandState = handState;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if dinosaur enemy collides with something on the x axis, it turns around and walks the other way
        if (hasCollided) {
            /** 
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }*/
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if bug is colliding with the ground, change its air ground state to GROUND
        // if it is not colliding with the ground, it means that it's currently in the air, so its air ground state is changed to AIR
        if (direction == Direction.DOWN) {
            if (hasCollided) {
                airGroundState = AirGroundState.GROUND;
            } else {
                airGroundState = AirGroundState.AIR;
            }
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
            //currentAnimationName = "WALK_UP";
         } else {
            //currentAnimationName = "WALK_DOWN";
            }
         webbed = false;
        }
     this.movementSpeed = movementSpeed;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("IDLE_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("IDLE_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()
            });

            put("DOWN_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });

            put("DOWN_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });

            put("SLAM_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("SLAM_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("WEBBED_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("WEBBED_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("SWEEP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("SWEEP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("CLAP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("CLAP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

        }};
    }

    public enum HandState {
       IDLE, SLAM_DOWN, SLAM_HOLD, SLAM_UP, SWEEP_DOWN, SWEEP, SWEEP_UP, CLAP_DOWN, CLAP_SWEEP, CLAP_HOLD, CLAP_UP, DEAD
    }
}
