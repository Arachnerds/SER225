package Projectiles;

import java.util.HashMap;
import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.*;
import Utils.Direction;
import Utils.Point;

// Create a web that represents a projectile
public class Raindrop extends Projectile {

    //private Point startPoint;
    private Point resetPoint;

    // Create the web using location, move speed, sprite, web time, and projectile existance time
    public Raindrop(Point location, float movementSpeedX, float movementSpeedY, int existenceFrames, String spritePath, Point resetPoint, int spriteWidth, int spriteHeight) {
        super(location, movementSpeedX, movementSpeedY, existenceFrames, spritePath, spriteWidth, spriteHeight);
        this.resetPoint = resetPoint;
        this.isUpdateOffScreen = true;
    }


    @Override
    public void update(Player player) {
        super.update(player); // Continue the regular movement logic
        
        // Check for collision with the player
        if (this.intersects(player) ) {
            player.hurtPlayer(this);
        }
    }

    // Method to reset projectile when collides with solid tile
    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (hasCollided) {
            this.setX(resetPoint.x);
            this.setY(resetPoint.y);
            //this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    // Method to reset projectile when collides with solid tile
    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (hasCollided) {
            this.setX(resetPoint.x);
            this.setY(resetPoint.y);
            //this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    // Loads projectile animations from sprite sheet
    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(1.5f)
                            .withBounds(1, 1, 17, 26)
                            .build()
            });
        }};
    }
}