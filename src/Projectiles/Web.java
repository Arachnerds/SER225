package Projectiles;

import Builders.FrameBuilder;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.*;
import Utils.Point;
import java.util.HashMap;

// Create a web that represents a projectile
public class Web extends Projectile {

    // Instance variable for how long the enemy will be webbed(stuck)
    private int webTime;

    // Create the web using location, move speed, sprite, web time, and projectile existance time
    public Web(Point location, float movementSpeedX, float movementSpeedY, int existenceFrames, int webTime, String spritePath, int spriteWidth, int spriteHeight) {
        super(location, movementSpeedX, movementSpeedY, existenceFrames, spritePath, spriteWidth, spriteHeight);
        this.webTime = webTime;
    }

    // Method that calls Projectile touched enemy method to remove the projectile
    // Then the freezeEnemy method is invoked
    @Override
    public void touchedEnemy(Enemy enemy) {
        this.freezeEnemy(enemy);
        this.mapEntityStatus = MapEntityStatus.REMOVED;
    }

    // Method to freeze the enemy that intersected with the web
    private void freezeEnemy(Enemy enemy) {
        enemy.freeze(webTime);
    }

    // Loads projectile animations from sprite sheet
    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(3)
                            .withBounds(1, 1, 5, 5) //5 ,5 
                            .build()
            });
        }};
    }
}