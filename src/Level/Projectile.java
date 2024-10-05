package Level;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;

public class Projectile extends MapEntity {

    // Movement speed and existence frames instance variables
    protected float movementSpeedX;
    protected float movementSpeedY;
    protected int existenceFrames;

    // Contructor to create projetile object with set movement speed and existence frames
    public Projectile(Point location, float movementSpeedX, float movementSpeedY, int existenceFrames, String spritePath) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load(spritePath), 7, 9), "DEFAULT");
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
        this.existenceFrames = existenceFrames;
        initialize();
    }

    // Updates the projectile for the existence time of the object
    @Override
    public void update() {
        if (existenceFrames <= 0) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        } else {
            moveXHandleCollision(movementSpeedX);
            moveYHandleCollision(movementSpeedY);
            super.update();
            existenceFrames--;
        }
    }

    // Method to remove projectile when collides with solid tile
    @Override
    public void onEndCollisionCheckX(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    // Method to remove projectile when collides with solid tile
    @Override
    public void onEndCollisionCheckY(boolean hasCollided, Direction direction, MapEntity entityCollidedWith) {
        if (hasCollided) {
            this.mapEntityStatus = MapEntityStatus.REMOVED;
        }
    }

    // Method to remove projectile when touches enemy
    public void touchedEnemy(Enemy enemy) {
        this.mapEntityStatus = MapEntityStatus.REMOVED;
    }

    // Loads projectile animations from sprite sheet
    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(1, 1, 5, 5)
                            .build()
            });
        }};
    }
}
