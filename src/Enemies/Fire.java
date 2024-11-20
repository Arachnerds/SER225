package Enemies;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import EnhancedMapTiles.PushableBlock;
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
import java.awt.Color;
import java.util.HashMap;

public class Fire extends Enemy {

    private float gravity = .5f;
    private float movementSpeed = .5f;
    private float originalMovementSpeed = movementSpeed;
    private Direction startFacingDirection;
    private Direction facingDirection;
    private AirGroundState airGroundState;
    private PushableBlock fryingPan;
    private boolean coveredByPan;

    public Fire(Point location, Direction facingDirection) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Fire.png"), 28, 30), "WALK_LEFT", 1);
        this.startFacingDirection = facingDirection;
        this.initialize();
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
        airGroundState = AirGroundState.GROUND;
    }

    @Override
    public void update(Player player) {
        if(fryingPan == null){
            coveredByPan = false;
        }
        else{
            if(this.intersects(fryingPan)){
                coveredByPan = true;
            }
            else{
                coveredByPan = false;
            }
        }


        float moveAmountX = 0;
        float moveAmountY = 0;

        // add gravity (if in air, this will cause bug to fall)
        moveAmountY += gravity;

        // if on ground, walk forward based on facing direction
        if (airGroundState == AirGroundState.GROUND) {
            if (facingDirection == Direction.RIGHT) {
                moveAmountX += movementSpeed;
            } else {
                moveAmountX -= movementSpeed;
            }
        }

        // move bug
        moveYHandleCollision(moveAmountY);
        //moveXHandleCollision(moveAmountX);

        super.update(player);
    }


    @Override
    public void touchedPlayer(Player player) {

        //Can harm the player if the flame is not covered
        if(!attackCooldownOn && !coveredByPan) {
            player.hurtPlayer(this);
        }
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

    // Getter method to return movement speed of bug
    @Override
    public float getOriginalMovementSpeed() {
        return originalMovementSpeed;
    }

    // Setter method to set temporary movement speed of bug
    @Override
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(4)
                            .withBounds(1, 8, 22, 22)
                            .build(),
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(4)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 8, 22, 22)
                            .build(),
            });
        }};
    }

    @Override
     public void draw(GraphicsHandler graphicsHandler) {
        if(!coveredByPan){
            super.draw(graphicsHandler);
            //drawBounds(graphicsHandler, new Color(0, 0, 255, 100));
        }
    }

    public void setPan(PushableBlock pan){
        fryingPan = pan;
    }
}

