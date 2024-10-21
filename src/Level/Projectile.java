package Level;

import Engine.ImageLoader;
import GameObject.SpriteSheet;
import Utils.Direction;
import Utils.Point;

public class Projectile extends MapEntity {

    // Movement speed and existence frames instance variables
    protected float movementSpeedX;
    protected float movementSpeedY;
    protected int existenceFrames;
    private boolean doesExpire;

    // Contructor to create projetile object with set movement speed and existence frames
    public Projectile(Point location, float movementSpeedX, float movementSpeedY, int existenceFrames, String spritePath, int spriteWidth, int spriteHeight) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load(spritePath), spriteWidth, spriteHeight), "DEFAULT");
        this.movementSpeedX = movementSpeedX;
        this.movementSpeedY = movementSpeedY;
        this.existenceFrames = existenceFrames;

        if (existenceFrames == 0) {
            doesExpire = false;
        } else {
            doesExpire = true;
        }

        initialize();
    }

    // Updates the projectile for the existence time of the object
    public void update(Player player) {
        if (doesExpire) {
            if (existenceFrames <= 0) {
                this.mapEntityStatus = MapEntityStatus.REMOVED;
            } else {
                moveXHandleCollision(movementSpeedX);
                moveYHandleCollision(movementSpeedY);
                super.update();
                existenceFrames--;
            }
        } else {
            moveXHandleCollision(movementSpeedX);
            moveYHandleCollision(movementSpeedY);
            super.update();
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

    public void touchedEnemy(Enemy enemy) {

    }
}
