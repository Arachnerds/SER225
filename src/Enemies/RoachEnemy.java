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

import Engine.GraphicsHandler;
import java.awt.Color;

import java.util.HashMap;

// This class is for the black bug enemy
// enemy behaves like a Mario goomba -- walks forward until it hits a solid map tile, and then turns around
// if it ends up in the air from walking off a cliff, it will fall down until it hits the ground again, and then will continue walking
public class RoachEnemy extends Enemy {

    private float gravity = .5f;
    private float movementSpeed = .5f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private Point startPoint;
    private Map map;

    private AirGroundState prevAirGroundState;
    private AirGroundState prevprevAirGroundState;

    private int sinceGround;

    public RoachEnemy(Point location, Direction facingDirection, Map map) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("RoachSpriteSheetDraft1.png"), 128, 46), "WALK_LEFT", 1);
        this.setScale(.75f);
        this.setBounds(currentFrame);
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
            sinceGround = 0;
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "WALK_LEFT";
            sinceGround = 0;
        }
        airGroundState = AirGroundState.GROUND;

        prevAirGroundState = AirGroundState.GROUND;
        prevprevAirGroundState = AirGroundState.GROUND;

        sinceGround = 0;

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



        //check if movement bounds have been reached -> turn around
        if(this.getLocation().x >= (startPoint.x + 4*map.getMapTile(1, 1).getX())) {
            facingDirection = Direction.LEFT;
            currentAnimationName = "WALK_LEFT";
        } else if(this.getLocation().x<=(startPoint.x - 3*map.getMapTile(1, 1).getX())){
            facingDirection = Direction.RIGHT;
            currentAnimationName = "WALK_RIGHT";
        }

        // if on ground, walk forward based on facing direction
        if (airGroundState == AirGroundState.GROUND) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
                //currentAnimationName = "WALK_RIGHT";
            } else {
                moveAmountX -= movementSpeed;
                //currentAnimationName = "WALK_LEFT";
            }
        } else if(prevAirGroundState == AirGroundState.AIR){
            // add gravity (if in air, this will cause bug to fall)
             moveAmountY += gravity;
        }

        // move bug
        moveYHandleCollision(moveAmountY);
        moveXHandleCollision(moveAmountX);

        //this.drawBounds(graphicsHandler, Color.RED);

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
        if(movementSpeed < .5f){
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WEB_RIGHT";
            } else {
                currentAnimationName = "WEB_LEFT";
            }
        } else{
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WALK_RIGHT";
            } else {
                currentAnimationName = "WALK_LEFT";
            }
        }
        this.movementSpeed = movementSpeed;

    }

    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction,  MapEntity entityCollidedWith) {
        // if bug has collided into something while walking forward,
        // it turns around (changes facing direction)
        if (hasCollided) {
            /**if(entityCollidedWith instanceof Player){
                System.out.println("hit player");
            }*/
            //System.out.println("bug collision");

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

        //System.out.println("anim: " + currentAnimationName + " state: " + airGroundState + " prev: " +prevAirGroundState + " sinceGround: "+ sinceGround);
        prevprevAirGroundState = prevAirGroundState;
        prevAirGroundState = airGroundState;

        if (direction == Direction.DOWN) {
            if (hasCollided) {
                //System.out.println("ROACH HIT");
                airGroundState = AirGroundState.GROUND;
                gravity = 0;
                sinceGround = 0;
            } else {
                //System.out.println("ROACH AIR");
                sinceGround++;
                if(sinceGround >=20){
                    gravity = .5f;
                    airGroundState = AirGroundState.AIR;
                } else{
                    airGroundState = AirGroundState.GROUND;
                }
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{

            put("WALK_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                .withScale(.75f)
                //.withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(10, 1,128, 46)
                .build(),
        new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                .withScale(.75f)
                //withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                .withBounds(10, 1, 128, 46)
                .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(10, 1,128, 46)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(10, 1, 128, 46)
                            .build()
            });

            put("DEAD_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_VERTICAL)
                        .withBounds(10, 1,128, 46)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });

            put("DEAD_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withImageEffect(ImageEffect.FLIP_VERTICAL)
                        .withBounds(10, 1,128, 46)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(6, 6, 128, 46)
                        .build()*/
            });

            put("WEB_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 8)
                        .withScale(.75f)
                        .withBounds(10, 1, 128, 46)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            });
            put("WEB_RIGHT", new Frame[] {
            new FrameBuilder(spriteSheet.getSprite(1, 0), 8)
                    .withScale(.75f)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(10, 1, 128, 46)
                    .build(),
            /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
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
