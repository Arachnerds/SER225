package Enemies;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Enemy;
import Level.EnemyState;
import Level.Map;
import Level.MapEntity;
import Level.MapEntityStatus;
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.awt.Color;
import java.util.HashMap;


public class BossHandEnemy extends Enemy {

    protected float movementSpeed = 3f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    private Point startPoint;

    private int holdFrames; 
    private int currentHoldFrameCount;

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected HandState handState;
    protected HandState previousHandState;
    protected BossMainEnemy enemyMain;

    private Point sweepStartPointLeft;
    private Point sweepStartPointRight;

    public BossHandEnemy(Point startLocation, Direction facingDirection, BossMainEnemy head, Map map) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("BossHandSpriteSheetDraft1.png"), 128, 128), "IDLE_LEFT", 1);
        this.startPoint = startLocation;
        this.enemyMain = head;
        this.startFacingDirection = facingDirection;
        this.holdFrames = 100;

        // Need to change the exact point locations to be based on the map tiles of the table platform !!!!!!!!!!!!!
        // If starts at the left point, then the right point will be the end point for the sweep range, vice versa
        this.sweepStartPointLeft = new Point(map.getMapTile(0, 15).getX()-60, map.getMapTile(0, 15).getY()+40); 
        this.sweepStartPointRight = new Point(map.getMapTile(18, 15).getX(), map.getMapTile(18, 15).getY()+40);

        this.initialize();
    }

    @Override
    public void initialize() {
        super.initialize();

        handState = HandState.IDLE;
        previousHandState = handState;

        facingDirection = startFacingDirection; // facing direction will be determined by palm
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
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
            super.update(player);
            return;
        }

        super.update(player);

        switch (handState) {

            case SLAM_DOWN:
                moveAmountY = movementSpeed;
                break;

            case SLAM_HOLD:
            currentHoldFrameCount++;
                if (!isFrozen) {
                    if (this.facingDirection == Direction.LEFT) {
                        this.currentAnimationName = "SLAM_LEFT";
                    } else {
                        this.currentAnimationName = "SLAM_RIGHT";
                    }

                    if (currentHoldFrameCount >= holdFrames) {
                        handState = HandState.SLAM_UP;
                        currentHoldFrameCount = 0; 
                    }
                }
                break;

            case SLAM_UP:
                moveAmountY = -movementSpeed;
                if (this.getY() <= startPoint.y) { 
                    handState = HandState.IDLE; 
                    if (facingDirection == Direction.RIGHT) {
                        currentAnimationName = "IDLE_RIGHT";
                    } else if (facingDirection == Direction.LEFT) {
                        currentAnimationName = "IDLE_LEFT";
                    }
                    this.setY(startPoint.y); 
                }
                break;

            case IDLE:
                if (enemyMain.checkIfHandsIdle()) {
                    enemyMain.handleHandsIdleState();
                }
                break;

            case SWEEP_LEFT:
                moveAmountX = -movementSpeed; 
                if (this.getX() <= sweepStartPointLeft.x) {
                    handState = HandState.IDLE; 
                    if (facingDirection == Direction.RIGHT) {
                        currentAnimationName = "IDLE_RIGHT";
                    } else if (facingDirection == Direction.LEFT) {
                        currentAnimationName = "IDLE_LEFT";
                    }
                    this.setX(startPoint.x);
                    this.setY(startPoint.y);
                }
                break;
    
            case SWEEP_RIGHT:
                moveAmountX = movementSpeed; 
                if (this.getX() >= sweepStartPointRight.x) {
                    handState = HandState.IDLE; 
                    if (facingDirection == Direction.RIGHT) {
                        currentAnimationName = "IDLE_RIGHT";
                    } else if (facingDirection == Direction.LEFT) {
                        currentAnimationName = "IDLE_LEFT";
                    }
                    this.setX(startPoint.x);
                    this.setY(startPoint.y);
                }
                break;
        }


        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);

        super.update(player);

        previousHandState = handState;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (handState == HandState.SLAM_DOWN && (hasCollided || (this.getY() + this.getHeight()) >= map.getMapTile(0, 19).getY()+45)) {
            handState = HandState.SLAM_HOLD; 
            currentHoldFrameCount = 0;
        }
    }

    
    @Override
    public void touchedPlayer(Player player) {

        if (isFrozen && player.getAirGroundState().equals(AirGroundState.AIR) && player.getMoveAmountY() > 0 && !hasBeenAttacked) {
            
            this.handState = HandState.DEAD;
            this.enemyState = EnemyState.DEAD;  

            player.setMomentumY(0.1f);
            player.setHitEnemy(true); 
            player.playerJumping();

            this.setMapEntityStatus(MapEntityStatus.REMOVED);

            enemyMain.bossTakeDamage(1);  
            
            enemyMain.respawnHands();

        } else if (!attackCooldownOn && !isFrozen) {
            player.hurtPlayer(this);
        }
    }

    // Method used to change state, and animation for the slam attack
    public void slamHand() {
        handState = HandState.SLAM_DOWN;
        currentHoldFrameCount = 0; 
        if (facingDirection == Direction.LEFT) {
            this.currentAnimationName = "SLAM_LEFT";
        } else {
            this.currentAnimationName = "SLAM_RIGHT";
        }
    }

    // Method used to change state, location, and animation for sweep attack
    public void sweepHand() {
        currentHoldFrameCount = 0; 
        if (facingDirection == Direction.LEFT) {
            handState = HandState.SWEEP_LEFT;
            this.currentAnimationName = "SWEEP_LEFT"; 
            this.setX(sweepStartPointRight.x);
            this.setY(sweepStartPointRight.y);
        } else {
            handState = HandState.SWEEP_RIGHT;
            this.currentAnimationName = "SWEEP_RIGHT"; 
            this.setX(sweepStartPointLeft.x);
            this.setY(sweepStartPointLeft.y);
        }
    }


    // Getter method to return movement speed of dinosaur
    @Override
    public float getOriginalMovementSpeed() {
        return originalMovementSpeed;
    }

    @Override
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void freeze(int frames) {
        if (handState == HandState.SLAM_HOLD) {
            super.freeze(frames);
            if (facingDirection == Direction.LEFT) {
                currentAnimationName = "WEBBED_LEFT";
            } else {
                currentAnimationName = "WEBBED_RIGHT";
            }
        }
    }

    public enum HandState {
        IDLE, SLAM_DOWN, SLAM_HOLD, SLAM_UP, SWEEP_LEFT, SWEEP_RIGHT, DEAD/*CLAP_DOWN, CLAP_SWEEP, CLAP_HOLD, CLAP_UP*/
    }

    /*public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }*/

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("IDLE_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(1.25f)
                            .withBounds(32, 32, 64, 64)
                            .build(),
            });

            put("IDLE_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(1.25f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(32, 32, 64, 64)
                            .build(),
            });

            put("SLAM_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0))
                        .withScale(1.25f)
                        .withBounds(20,20, 94, 100)
                        .build(),
            });

            put("SLAM_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0))
                        .withScale(1.25f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(15, 20, 94, 100)
                        .build(),
            });

            put("WEBBED_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(2, 0))
                        .withScale(1.25f)
                        .withBounds(20, 20, 94, 100)
                        .build(),
            });

            put("WEBBED_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(2, 0))
                        .withScale(1.25f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(15, 20, 94, 100)
                        .build(),
            });

            put("SWEEP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(3, 0))
                        .withScale(1.25f)
                        .withBounds(30, 5, 60, 123)
                        .build(),
            });

            put("SWEEP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(3, 0))
                        .withScale(1.25f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(38, 5, 60, 123)
                        .build(),
            });

            // Did not add a clap attack
            put("CLAP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(4, 0))
                        .withScale(1.25f)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

            put("CLAP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(4, 0))
                        .withScale(1.25f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(4, 2, 5, 13)
                        .build(),
            });

        }};
    }
}
