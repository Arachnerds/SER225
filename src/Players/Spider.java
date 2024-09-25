package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;
import Projectiles.Web;
import Utils.Direction;
import Utils.Point;

import java.awt.Color;
import java.util.HashMap;

// This is the class for the Spider player character
// basically just sets some values for physics and then defines animations
public class Spider extends Player {
        // Instance variables to determine cooldown of projectile shooting for spider
        private int shootCooldownFrames;
        private final int MAX_COOLDOWN = 50;

        
        public Spider(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("SpiderSpriteDraft1.png"), 850, 563), x, y, "STAND_RIGHT");
        this.setScale(.1f);
        gravity = .5f;
        terminalVelocityY = 6f;
        jumpHeight = 14.5f;
        jumpDegrade = .5f;
        walkSpeed = 2.3f;
        momentumYIncrease = .5f;
        shootCooldownFrames = 0;
    }

        // Method called when shoot key is pressed
        // The spider will fire a web projectile based on spider direction and location
        private void shootWebProjectile() {
        int webX;
        float webVelocity;

        if (facingDirection == Direction.RIGHT) {
            webX = Math.round(this.getX()) + (getWidth()/2);
            webVelocity = 5f;
        } else {
            webX = Math.round(this.getX());
            webVelocity = -5f;
        }

        // define where web will spawn on the map (y location) relative to spider's location
        int webY = Math.round(getY() + getHeight()/3);

        Web web;
        if (facingDirection.equals(Direction.RIGHT)) {
            web = new Web(new Point(webX, webY), webVelocity, 50, 150, "WebRight.png");
        } else {
            web = new Web(new Point(webX, webY), webVelocity, 50, 150, "WebLeft.png");
        }

        // add fireball enemy to the map for it to spawn in the level
        map.addProjectile(web);
        
        shootCooldownFrames = MAX_COOLDOWN; 

        super.update();

    }

    // 
    public void update() {
        super.update();
        handleInput();
        updateCooldown();
    }

    // When shoot key is pressed, fire projectile and lock the shoot key
    public void handleInput() { 
        if (Keyboard.isKeyDown(SHOOT_KEY) && !keyLocker.isKeyLocked(SHOOT_KEY)) {
            shootWebProjectile();
            keyLocker.lockKey(SHOOT_KEY);
        }
    }

    // Update the cooldown timer in the update loop, decreases the frames with each loop
    private void updateCooldown() {
        if (shootCooldownFrames > 0) {
            shootCooldownFrames--; 
        }

        // Unlock the key once cooldown is over and the key is released
        if (shootCooldownFrames <= 0 && Keyboard.isKeyUp(SHOOT_KEY)) {
            keyLocker.unlockKey(SHOOT_KEY);
        }
    }

   /** public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }*/

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withBounds(8, 9, 850, 563)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 850, 563)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(.1f)
                            .withBounds(8, 9, 850, 563)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withBounds(8, 9, 8, 9)
                            .build()*/
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 14)
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 850, 563)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(1, 1), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 14)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build()*/
            });

            put("JUMP_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withBounds(8, 9, 850, 563)
                            .build()
            });

            put("JUMP_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 850, 563)
                            .build()
            });

            put("FALL_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withBounds(8, 9, 850, 563)
                            .build()
            });

            put("FALL_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 850, 563)
                            .build()
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withBounds(8, 12, 850, 563)
                            .build()
            });

            put("CROUCH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 12, 850, 563)
                            .build()
            });

            put("DEATH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.1f)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                            .withScale(3)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                            .withScale(3)
                            .build()*/
            });

            put("DEATH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    /**new FrameBuilder(spriteSheet.getSprite(5, 1), 8)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(5, 2), -1)
                            .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .build()*/
            });

            put("SWIM_STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("SWIM_STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 9, 8, 9)
                            .build()
            });

            put("CLIMB_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withBounds(8, 12, 850, 563)
                            .build()
            });

            put("CLIMB_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.1f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(8, 12, 850, 563)
                            .build()
            });
        }};
    }
}
