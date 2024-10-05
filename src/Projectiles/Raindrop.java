package Projectiles;

import Level.*;
import Utils.Point;

// Create a web that represents a projectile
public class Raindrop extends Projectile {

    // Create the web using location, move speed, sprite, web time, and projectile existance time
    public Raindrop(Point location, float movementSpeedX, float movementSpeedY, int existenceFrames, String spritePath) {
        super(location, movementSpeedX, movementSpeedY, existenceFrames, spritePath);
        
    }

}