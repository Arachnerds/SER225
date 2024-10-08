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
import Level.Player;
import Utils.AirGroundState;
import Utils.Direction;
import Utils.Point;

import java.util.HashMap;

// This class is for the custom fly enemy
// enemy behaves like a Mario goomba -- walks forward until it hits a solid map tile, and then turns around
// if it ends up in the air from walking off a cliff, it will fall down until it hits the ground again, and then will continue walking
public class FlyEnemy extends Enemy{

    private float gravity = 0f;
    private float movementSpeed = .5f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private Point startPoint;
    private Map map;

    public FlyEnemy(Point location, Direction facingDirection, Map map) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("FlySpriteDraft1.png"), 757, 539), "WALK_LEFT", 1);
        this.setScale(0.1f);
        this.startFacingDirection = facingDirection;
        startPoint = location;
        this.initialize();
        this.map = map;
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "WALK_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
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

            super.update(player);
            return;
        }

        // add gravity (if in air, this will cause bug to fall)
        moveAmountY += gravity;
        
        //check if movement bounds have been reached -> turn around
        if (this.getLocation().x >= (startPoint.x + 3*map.getMapTile(1, 1).getX())){
            facingDirection = Direction.LEFT;
        } else if(this.getLocation().x<=(startPoint.x - 3*map.getMapTile(1, 1).getX())){
            facingDirection = Direction.RIGHT;
        }

        // if on ground, walk forward based on facing direction
        if (airGroundState == AirGroundState.AIR) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }
        }

        // move bug
        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);
        
        super.update(player);
    }

    // Getter method to return movement speed of dinosaur
    @Override
    public float getOriginalMovementSpeed() {
        return originalMovementSpeed;
    }

    // Setter method to set temporary movement speed of dinosaur
    @Override
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction,  MapEntity entityCollidedWith) {
        // if bug has collided into something while walking forward,
        // it turns around (changes facing direction)
        if (hasCollided) {
            if (direction == Direction.RIGHT) {
                facingDirection = Direction.LEFT;
                currentAnimationName = "WALK_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "WALK_RIGHT";
            }
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

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.1f)
                            .withBounds(50, 6, 650, 450)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                            .withScale(2)
                            .withBounds(6, 6, 12, 7)
                            .build()*/
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(50, 6, 650, 450)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                            .withScale(2)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(6, 6, 12, 7)
                            .build()**/
            });

            put("DEAD_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.1f)
                        .withBounds(50, 6, 650, 450)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });

            put("DEAD_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.1f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withImageEffect(ImageEffect.FLIP_VERTICAL)
                        .withBounds(50, 6, 650, 450)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 1), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });
        }};
    }

       /**public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }*/
}
