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
public class MosquitoEnemy extends Enemy {

    private float gravity = 0f;
    protected float movementSpeed = 3f;

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

    private Point aim;
    private Point[] anchors = new Point[4];

    //private double jumpStartPos;
    //private boolean goodToLand;

    public MosquitoEnemy(Point startLocation, Direction facingDirection, Map map) {
        super(startLocation.x, startLocation.y, new SpriteSheet(ImageLoader.load("MosquitoSpritesheetDraft1.png"), 144, 159), "STAND_LEFT", 1);
        this.startPoint = startLocation;
        this.startFacingDirection = facingDirection;
        startPoint = startLocation;
        webbed = false;

        dinosaurState = DinosaurState.SHOOT_WAIT;
        previousDinosaurState = DinosaurState.SHOOT;

        //create points for the mosquito to choose between based on starting location
        anchors[0] = new Point(startPoint.x - 250f, startPoint.y - 125f);
        anchors[1] = new Point(startPoint.x + 250f, startPoint.y - 125f);
        anchors[2] = new Point(startPoint.x + 250f, startPoint.y + 125f);
        anchors[3] = new Point(startPoint.x - 250f, startPoint.y + 215f);

        /*for (Point point : anchors) {
            System.out.println("Point: " + point);
        }*/
        //jumpStartPos = 30;
        //goodToLand = true;

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
                //System.out.println("swap to move");
            }
            else {
                jumpWaitTimer--;
            }
        }


        // if jumping
        if (dinosaurState == DinosaurState.SHOOT && !webbed) { //grasshopper jumps

            if(previousDinosaurState == DinosaurState.SHOOT_WAIT){ //if starting jump

                /** 
                if (this.getLocation().x >= (startPoint.x + 5*map.getMapTile(1, 1).getX())){ //determine direction
                    facingDirection = Direction.LEFT;
                } else if(this.getLocation().x<=(startPoint.x - 5*map.getMapTile(1, 1).getX())){
                    facingDirection = Direction.RIGHT;
                }*/

                int aiming = (int) (Math.random() * (3));//randomize point choice
                //System.out.println("random: " + aiming);
                aim = anchors[aiming];
                //System.out.println("aim = "+ aim + " curr pos: "+ this.x +"," +this.y);


                if(aim.x<this.getLocation().x){ //determine direction
                    this.facingDirection = Direction.LEFT;
                    currentAnimationName = "STAND_LEFT";
                } else{
                    facingDirection = Direction.RIGHT;
                    currentAnimationName = "STAND_RIGHT";
                }


            }

             //still moving 
                boolean checkx = false;
                boolean checky = false;
                //move relevant y position

                if ((this.y > aim.y + 5)) { //jumping up
                    moveAmountY -=  movementSpeed;
                    //System.out.println("up+");

                } else if(this.y < aim.y - 5){ //move down
                    
                    moveAmountY += movementSpeed;
                    //System.out.println("down+");
                } else{
                    checky = true;
                }

                //move relevant x position
                if((this.x > aim.x + 5)){ //move left
                    moveAmountX -= movementSpeed;
                    //System.out.print("left");
                    
                } else if( this.x < aim.x - 5){ //move right
                    
                    moveAmountX += movementSpeed;
                    //System.out.print("right");
                    
                } else{
                    checkx = true;
                }

                if(checky && checkx){
                    dinosaurState = DinosaurState.SHOOT_WAIT;
                    previousDinosaurState = DinosaurState.SHOOT;
                    return;
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
            
                if (direction == Direction.RIGHT) {
                    facingDirection = Direction.LEFT;
                    currentAnimationName = "STAND_LEFT";
                } else {
                    facingDirection = Direction.RIGHT;
                    currentAnimationName = "STAND_RIGHT";
                }
                
                dinosaurState = DinosaurState.SHOOT;
                previousDinosaurState = DinosaurState.SHOOT_WAIT;
            
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
            put("STAND_LEFT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(.75f)
                            .withBounds(1, 2, 139, 126)
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
                            .withBounds(1, 2, 139, 126)
                            .build(),
                            /** 
                    new FrameBuilder(spriteSheet.getSprite(0, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(4, 2, 5, 13)
                            .build()**/
            });

            put("WEB_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                        .withScale(.75f) 
                        .withBounds(1, 2, 139, 126)
                        .build(),
                /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(2)
                        .withBounds(6, 6, 12, 7)
                        .build()*/
            }); 
            put("WEB_RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(1, 0), 14)
                    .withScale(.75f)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .withBounds(1, 2, 139, 126)
                    .build(),
            /**new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                    .withScale(2)
                    .withBounds(6, 6, 12, 7)
                    .build() */
             });

             put("DEAD_LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                        .withScale(.75f)
                        .withBounds(1, 2, 139, 126)
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
                        .withBounds(1, 2, 139, 126)
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
