package Projectiles;

import Level.*;
import Utils.Point;

// Create a web that represents a projectile
public class Web extends Projectile {

    // Instance variable for how long the enemy will be webbed(stuck)
    private int webTime;

    // Create the web using location, move speed, sprite, web time, and projectile existance time
    public Web(Point location, float movementSpeedX, float movementSpeedY, int existenceFrames, int webTime, String spritePath) {
        super(location, movementSpeedX, movementSpeedY, existenceFrames, spritePath);
        this.webTime = webTime;
    }

    // Method that calls Projectile touched enemy method to remove the projectile
    // Then the freezeEnemy method is invoked
    @Override
    public void touchedEnemy(Enemy enemy) {
        super.touchedEnemy(enemy);
        this.freezeEnemy(enemy);
    }

    // Method to freeze the enemy that intersected with the web
    private void freezeEnemy(Enemy enemy) {
        enemy.freeze(webTime);
    }
}