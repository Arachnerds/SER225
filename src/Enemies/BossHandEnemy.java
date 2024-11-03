package Enemies;

import Builders.FrameBuilder;
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

import java.util.HashMap;


public class BossHandEnemy extends Enemy {

    protected float movementSpeed = 1f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    private Point startPoint;
    private boolean webbed;

    private int holdFrames; 
    private int currentHoldFrameCount;

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    protected HandState handState;
    protected HandState previousHandState;
    protected BossMainEnemy enemyMain;

    private Point sweepStartPointLeft;
    private Point sweepStartPointRight;

    public BossHandEnemy(Point startLocation, Direction facingDirection, BossMainEnemy head, Map map) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("FireflyAttackDraft1.png"), 64, 64), "WALK_RIGHT", 1);
        this.startPoint = startLocation;
        this.webbed = false;
        this.enemyMain = head;
        this.startFacingDirection = facingDirection;
        this.holdFrames = 30;

        // Need to change the exact point locations to be based on the map tiles of the table platform !!!!!!!!!!!!!
        // If starts at the left point, then the right point will be the end point for the sweep range, vice versa
        this.sweepStartPointLeft = new Point(startLocation.x - 100, startLocation.y); 
        this.sweepStartPointRight = new Point(startLocation.x + 100, startLocation.y);

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

        switch (handState) {

            case SLAM_DOWN:
                moveAmountY = movementSpeed;
                break;

            case SLAM_HOLD:
                currentHoldFrameCount++;
                if (currentHoldFrameCount >= holdFrames) {
                    handState = HandState.SLAM_UP;
                    currentHoldFrameCount = 0; 
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
                break;

            case SWEEP_LEFT:
                moveAmountX = -movementSpeed; 
                if (this.getX() <= sweepStartPointRight.x) {
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
                if (this.getX() >= sweepStartPointLeft.x) {
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

        // This will communicate to the boss to check if both hands are idle
        enemyMain.handleHandsIdleState();

        previousHandState = handState;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (handState == HandState.SLAM_DOWN && hasCollided) {
            handState = HandState.SLAM_HOLD; 
            currentHoldFrameCount = 0;
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
        if (facingDirection == Direction.LEFT) {
            handState = HandState.SWEEP_LEFT;
            this.currentAnimationName = "SWEEP_LEFT"; 
            this.setX(sweepStartPointLeft.x);
            this.setY(sweepStartPointLeft.y);
        } else {
            handState = HandState.SWEEP_RIGHT;
            this.currentAnimationName = "SWEEP_RIGHT"; 
            this.setX(sweepStartPointRight.x);
            this.setY(sweepStartPointRight.y);
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

    public enum HandState {
        IDLE, SLAM_DOWN, SLAM_HOLD, SLAM_UP, SWEEP_LEFT, SWEEP_RIGHT, DEAD/*CLAP_DOWN, CLAP_SWEEP, CLAP_HOLD, CLAP_UP*/
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("IDLE_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build(),
            });

            put("IDLE_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
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
}
