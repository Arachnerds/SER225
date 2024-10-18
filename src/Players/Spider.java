package Players;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Keyboard;
import Engine.Sound;
import Engine.Sound.SoundEffect;
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
        private final int MAX_COOLDOWN = 70;
        private boolean hasShotWeb = false;

        
        public Spider(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("SpiderSpriteSheetDraft1.png"), 128, 128), x, y, "STAND_RIGHT");
        //this.setScale(.1f);
        gravity = .5f;
        terminalVelocityY = 6f;
        jumpHeight = 12.5f;
        jumpDegrade = .5f;
        walkSpeed = 3.5f;
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
            webVelocity = 5.5f;
        } else {
            webX = Math.round(this.getX());
            webVelocity = -5.5f;
        }

        // define where web will spawn on the map (y location) relative to spider's location
        int webY = Math.round(getY() + getHeight()/3);

        Web web;
        if (facingDirection.equals(Direction.RIGHT)) {
            web = new Web(new Point(webX, webY), webVelocity, 0, 60, 120, "WebRight.png", 7, 9);
        } else {
            web = new Web(new Point(webX, webY), webVelocity, 0, 60, 120, "WebLeft.png", 7, 9);
        }

        // add fireball enemy to the map for it to spawn in the level
        map.addProjectile(web);
        
        shootCooldownFrames = 0; 

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
            hasShotWeb = true;
            Sound.startSFX(Sound.SoundEffect.WEB);
            keyLocker.lockKey(SHOOT_KEY);
        }
    }

    // Update the cooldown timer in the update loop, decreases the frames with each loop
    private void updateCooldown() {
        if (shootCooldownFrames < MAX_COOLDOWN && hasShotWeb) {
            shootCooldownFrames++; 
        }

        // Unlock the key once cooldown is over and the key is released
        if (shootCooldownFrames >= MAX_COOLDOWN && Keyboard.isKeyUp(SHOOT_KEY)) {
            keyLocker.unlockKey(SHOOT_KEY);
            hasShotWeb = false;
        }
    }

    public boolean hasShotWeb() {
        return hasShotWeb;
    }

    public int getShootCooldownFrames() {
        return shootCooldownFrames;
    }

    /**public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }**/

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("JUMP_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("JUMP_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("FALL_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("FALL_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0))
                            .withScale(.75f)
                            .withBounds(0,10, 128, 90)
                            .build()
            });

            put("CROUCH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0))
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 10, 128, 90)
                            .build()
            });

            put("DEATH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_VERTICAL)
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
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withImageEffect(ImageEffect.FLIP_VERTICAL)
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
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 1), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 2), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 3), 7)
                            .withScale(.75f)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });

            put("CLIMB_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 1), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 2), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 3), 7)
                            .withScale(.75f)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(0, 9, 128, 100)
                            .build()
            });
        }};
    }
}
