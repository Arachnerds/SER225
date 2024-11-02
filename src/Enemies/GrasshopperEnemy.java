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
public class GrasshopperEnemy extends Enemy {

    private float gravity = 1f;
    protected float movementSpeed = 2f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    protected Direction facingDirection;
    protected AirGroundState airGroundState;

    // timer is used to determine how long dinosaur freezes in place before shooting fireball || PAUSE TO JUMP
    protected int jumpWaitTimer;

    // can be either WALK or SHOOT based on what the enemy is currently set to do
    
    protected DinosaurState dinosaurState;
    protected DinosaurState previousDinosaurState;
    

    private Point startPoint;
    private boolean webbed;
    private double aim;
    private double jumpStartPos;
    private boolean goodToLand;

    public GrasshopperEnemy(Point startLocation, Direction facingDirection, Map map) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("GrasshopperSpritesheetDraft1.png"), 211, 210), "STAND_LEFT", 1);
        this.startPoint = startLocation;
        this.startFacingDirection = facingDirection;
        startPoint = startLocation;
        webbed = false;

        dinosaurState = DinosaurState.SHOOT_WAIT;
        previousDinosaurState = DinosaurState.SHOOT;
        jumpStartPos = 30;
        goodToLand = true;

        this.initialize();
    }

 
    @Override
    public void initialize() {
        super.initialize();
        facingDirection = startFacingDirection;
        if (facingDirection == Direction.RIGHT) {
            currentAnimationName = "STAND_RIGHT";
        } else if (facingDirection == Direction.LEFT) {
            currentAnimationName = "STAND_LEFT";
        }
        airGroundState = AirGroundState.AIR;
    }

    @Override
    public void update(Player player) { //SHOOT = JUMP ; WALK = X ; SHOOT_WAIT = WAIT
        float moveAmountX = 0;
        float moveAmountY = 0;

        //System.out.println(dinosaurState+ " - " + previousDinosaurState + " - " + jumpWaitTimer);

        if (this.enemyState == EnemyState.DEAD) {
            
            if (!hasBeenKilled) {
                hasBeenKilled = true;
                this.setY(this.y + this.getHeight()/4);
                gravity = 1f;
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
        
    
        // if waiting
        if (dinosaurState == DinosaurState.SHOOT_WAIT && !webbed) {
            if (previousDinosaurState == DinosaurState.SHOOT) { //if just landed
                jumpWaitTimer = 40;

                //boolean playerToRight = player.getX()>=this.x;
                //currentAnimationName = playerToRight ? "JUMP_RIGHT" : "JUMP_LEFT";

            } else if (jumpWaitTimer == 0) { //timer is up, jump
                dinosaurState = DinosaurState.SHOOT;
                //previousDinosaurState = DinosaurState.SHOOT_WAIT
            }
            else {
                jumpWaitTimer--;
            }
        }

        /**boolean playerInRange = false;
        if(player.getX()>=this.x-400 && player.getX()<=this.x+400){ //check that player is within range
            playerInRange = true;
        }*/

        // if jumping
        if (dinosaurState == DinosaurState.SHOOT && !webbed) { //grasshopper jumps

            if(previousDinosaurState == DinosaurState.SHOOT_WAIT){ //if starting jump

                if (this.getLocation().x >= (startPoint.x + 5*map.getMapTile(1, 1).getX())){ //determine direction
                    facingDirection = Direction.LEFT;
                } else if(this.getLocation().x<=(startPoint.x - 5*map.getMapTile(1, 1).getX())){
                    facingDirection = Direction.RIGHT;
                }

                double aiming = (double)(Math.random() * 600);//randomize jump length
                System.out.println("aiming = "+ aiming);
                if(this.facingDirection == Direction.LEFT){ //aim jump
                    aim = this.x - aiming;
                    currentAnimationName = "JUMP_LEFT";
                } else{
                    aim = this.x + aiming;
                    currentAnimationName = "JUMP_RIGHT";
                }
                jumpStartPos = this.x;
                goodToLand = false;

                //System.out.println(aim +" - " + jumpStartPos);
            }

            //System.out.println(aim +" - " + this.x);

            if(facingDirection == Direction.RIGHT){
                System.out.println("heading right?");
                if ((this.x - jumpStartPos) < ((aim-jumpStartPos)/2)) { //jumping up
                    moveAmountY -= 2* movementSpeed;
                    //System.out.print((this.x - jumpStartPos) +" <? " +(aim-jumpStartPos)/2 );
                } else{
                    //System.out.print(" head down!");
                    moveAmountY += movementSpeed;
                    goodToLand = true;
                }
            } else{
                System.out.println("heading left?");
                if((this.x - aim) > (((jumpStartPos-aim)/2))){
                    moveAmountY -= 2* movementSpeed;
                    System.out.print((this.x - aim) +" >? " +(((jumpStartPos-aim)/2)));
                } else{
                    System.out.print(" head down!");
                    moveAmountY += movementSpeed;
                    goodToLand = true;
                }
            }


            if (facingDirection == Direction.RIGHT) { //move in direction
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            } 
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
                currentAnimationName = "STAND_RIGHT";
            } else {
                currentAnimationName = "STAND_LEFT";
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
            if(dinosaurState == DinosaurState.SHOOT){
                if (direction == Direction.RIGHT) {
                    facingDirection = Direction.LEFT;
                    currentAnimationName = "JUMP_LEFT";
                } else {
                    facingDirection = Direction.RIGHT;
                    currentAnimationName = "JUMP_RIGHT";
                }
            } else{
                if (direction == Direction.RIGHT) {
                    facingDirection = Direction.LEFT;
                    currentAnimationName = "STAND_LEFT";
                } else {
                    facingDirection = Direction.RIGHT;
                    currentAnimationName = "STAND_RIGHT";
                }
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

                if(enemyState != EnemyState.DEAD && previousDinosaurState == DinosaurState.SHOOT && goodToLand){
                    dinosaurState = DinosaurState.SHOOT_WAIT; //grasshopper has landed, start wait timer
                    previousDinosaurState = DinosaurState.SHOOT;
                    jumpWaitTimer = 40;
                    if(this.facingDirection == Direction.LEFT){
                        currentAnimationName = "STAND_LEFT";
                    } else{
                        currentAnimationName = "STAND_RIGHT";
                    }
                }

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
            put("STAND_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(.75f)
                            .withBounds(27, 31, 176, 86)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withBounds(4, 2, 5, 13)
                            .build()**/
            });

            put("STAND_RIGHT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withScale(.75f)
                            .withBounds(27, 31, 176, 86)
                            .build(),
                            /** 
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()**/
            });

            
            put("JUMP_LEFT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(.75f)
                            .withBounds(27, 31, 176, 86)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 20)
                        .withScale(.75f)
                        .withBounds(28, 16, 170, 170)
                        .build()
            });

            put("JUMP_RIGHT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(27, 31, 176, 86)
                            .build(),
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(28, 16, 170, 170)
                            .build(),
            });

            put("WEB_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(2, 0), 14)
                        .withScale(.75f) 
                        .withBounds(27, 31, 176, 86)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            }); 
            put("WEB_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(2, 0), 14)
                    .withScale(.75f)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(27, 31, 176, 86)
                    .build(),
            /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                    .withScale(2)
                    .withBounds(6, 6, 12, 7)
                    .build() */
             });

             put("DEAD_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withBounds(27, 31, 176, 86)
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
                        .withBounds(27, 31, 176, 86)
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
    
}
