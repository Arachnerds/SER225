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
public class PillbugEnemy extends Enemy {

    private float gravity =0f;
    private float movementSpeed = 5f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private Point startPoint;
    private Map map;
    private boolean stand;
    private int standwait;


    private AirGroundState prevAirGroundState;
    private AirGroundState prevprevAirGroundState;
    private int sinceGround;

    public PillbugEnemy(Point location, Direction facingDirection, Map map) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("PillBugSpriteSheetDraft1.png"), 128, 87), "ROLL_LEFT", 1);
        this.setScale(.75f);
        this.setBounds(currentFrame);
        this.startFacingDirection = facingDirection;
        startPoint = location;
        this.initialize();
        this.map = map;
        stand = false;
        standwait = 0;
    }

    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
      
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "ROLL_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "ROLL_LEFT";
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

        if(stand){ //if currently standing
            standwait++; //increment counter
            //System.out.println("w: "+ standwait);
            if(standwait>=50f){//if counter passed, start rolling
                //System.out.println("should walk");
                if(facingDirection == Direction.LEFT) {
                    //facingDirection = Direction.LEFT;
                    currentAnimationName = "ROLL_LEFT";
                    stand = false;
                } else if(facingDirection == Direction.RIGHT){
                    //facingDirection = Direction.RIGHT;
                    currentAnimationName = "ROLL_RIGHT";
                    stand = false;
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
                }
            }
        } else{ //if not standing, moving
                //check if movement bounds have been reached -> turn around
            if(this.getLocation().x >= (startPoint.x + 4*map.getMapTile(1, 1).getX())) {
                facingDirection = Direction.LEFT;
                 currentAnimationName = "STAND_LEFT";
                 stand =true;
                 standwait = 0;
            } else if(this.getLocation().x<=(startPoint.x - 3*map.getMapTile(1, 1).getX())){
                 facingDirection = Direction.RIGHT;
                 currentAnimationName = "STAND_RIGHT";
                 stand = true;
                 standwait = 0;
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
            }
            //System.out.println(this.getLocation());
        }

        if(prevAirGroundState == AirGroundState.AIR){
            // add gravity (if in air, this will cause bug to fall)
             moveAmountY += gravity;
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
        if(movementSpeed < .5f){
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "WEB_RIGHT";
                stand = false;
                standwait = 0;
            } else {
                currentAnimationName = "WEB_LEFT";
                stand = false;
                standwait = 0;
            }
        } else{
            if (facingDirection == Direction.RIGHT) {
                currentAnimationName = "STAND_RIGHT";
                stand = true;
                standwait = 0;
            } else {
                currentAnimationName = "STAND_LEFT";
                stand = true;
                standwait = 0;
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
                currentAnimationName = "STAND_LEFT";
            } else {
                facingDirection = Direction.RIGHT;
                currentAnimationName = "STAND_RIGHT";
            }
            stand = true;
            standwait = 0;
        }
    }

    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        // if bug is colliding with the ground, change its air ground state to GROUND
        // if it is not colliding with the ground, it means that it's currently in the air, so its air ground state is changed to AIR

        //System.out.println("anim: " + currentAnimationName + " state: " + airGroundState + " sinceGround: "+ sinceGround);
        prevprevAirGroundState = prevAirGroundState;
        prevAirGroundState = airGroundState;

        if (direction == Direction.DOWN) {
            if (hasCollided) {
                //System.out.println("hit");
                airGroundState = AirGroundState.GROUND;
                gravity = 0;
                sinceGround = 0;
            } else {
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

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                             .withBounds(0, 13, 125, 62)
                             .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                            .withBounds(0, 13, 125, 62)
                            .build()
            });

            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 13,125, 62)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 13, 125, 62)
                            .build()
            });

            put("DEAD_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_VERTICAL)
                        .withBounds(0, 13,125, 62)
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
                        .withBounds(0, 13,125, 62)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(6, 6, 128, 46)
                        .build()*/
            });

            put("WEB_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(2, 0), 8)
                        .withScale(.75f)
                        .withBounds(0, 13, 125, 62)
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
                    .withBounds(0, 13, 125, 62)
                    .build(),
            /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                    .withScale(2)
                    .withBounds(6, 6, 12, 7)
                    .build()*/
             });

             put("ROLL_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 6)
                        .withScale(.75f)
                         .withBounds(25, 9, 87, 76)
                         .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 6)
                        .withScale(.75f)
                        .withBounds(19, 0, 85, 87)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 2), 6)
                        .withScale(.75f)
                        .withBounds(25, 9, 87, 76)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 6)
                        .withScale(.75f)
                        .withBounds(32, 0, 85, 87)
                        .build()
            });

            put("ROLL_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 6)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(25, 9, 87, 76)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 1), 6)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(19, 0, 85, 87)
                        .build(),
                        new FrameBuilder(spriteSheet.getSprite(1, 2), 6)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(25, 9, 87, 76)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 3), 6)
                        .withScale(.75f)
                        .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                        .withBounds(32, 0, 85, 87)
                        .build()
            });
        }};
    }

    public void SetAirState(){
        airGroundState = AirGroundState.GROUND;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }
}