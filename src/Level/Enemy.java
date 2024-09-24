package Level;

import GameObject.Frame;
import GameObject.SpriteSheet;
import java.util.HashMap;

// This class is a base class for all enemies in the game -- all enemies should extend from it
public abstract class Enemy extends MapEntity {

    protected boolean isFrozen;
    protected int freezeFrames;
    private float movementSpeed;

    public Enemy(float x, float y, SpriteSheet spriteSheet, String startingAnimation) {
        super(x, y, spriteSheet, startingAnimation);
    }

    public Enemy(float x, float y, HashMap<String, Frame[]> animations, String startingAnimation) {
        super(x, y, animations, startingAnimation);
    }

    public Enemy(float x, float y, Frame[] frames) {
        super(x, y, frames);
    }

    public Enemy(float x, float y, Frame frame) {
        super(x, y, frame);
    }

    public Enemy(float x, float y) {
        super(x, y);
    }


    // Abstract methods so all subclasses must implement, needed for freezing mechanic
    public abstract float getOriginalMovementSpeed();

    public abstract void setMovementSpeed(float movementSpeed);

    @Override
    public void initialize() {
        super.initialize();
    }

    // Updates enemies, checks for player intersection and projectile intersection
    // Death and Freeze mechanics are called
    public void update(Player player) {
        if (isFrozen) {
            freezeFrames--; 
            if (freezeFrames <= 0) {
                isFrozen = false; 
                setMovementSpeed(movementSpeed);
                return;
            }
        } else {
            super.update();
        }

        if (intersects(player)) {
            touchedPlayer(player);
        }
        
        for (Projectile projectile : map.getProjectiles()) {
            if (intersects(projectile)) {
                projectile.touchedEnemy(this);
            }
        }
    }

    // A subclass can override this method to specify what it does when it touches the player
    public void touchedPlayer(Player player) {
        player.hurtPlayer(this);
    }

    // Stores the original movespeed of enemy, sets movespeed of enemy to 0 for a number of updated frames
    public void freeze(int frames) {
        this.movementSpeed = getOriginalMovementSpeed();
        this.freezeFrames = frames;
        this.isFrozen = true;
        this.setMovementSpeed(0); // Set movement speed to 0
    }
}
