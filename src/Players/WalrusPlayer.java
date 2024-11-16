package Players;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.Player;
import java.util.HashMap;

import Engine.GraphicsHandler;
import java.awt.Color;

// This is the class for the Spider player character
// basically just sets some values for physics and then defines animations
public class WalrusPlayer extends Player {
        public WalrusPlayer(float x, float y) {
        super(new SpriteSheet(ImageLoader.load("WalSpiderSpritesheetDraft1.png"), 24, 24), x, y-30, "STAND_RIGHT");
        //this.setScale(.1f);
        gravity = .5f;
        terminalVelocityY = 6f;
        jumpHeight = 12.5f;
        jumpDegrade = .5f;
        walkSpeed = 3.5f;
        momentumYIncrease = .5f;
        }

       
    public void update() {
        super.update();
        //System.out.println("player pos: " + this.x +" "+ this.y);
        /* handleInput();
        updateCooldown(); */
    }

    /*public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }*/

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("STAND_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3f)
                        .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("STAND_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0))
                        .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("WALK_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 7)
                                .withScale(3)
                                .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 7)
                                .withScale(3)
                                .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 7)
                                .withScale(3)
                                 .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 7)
                                 .withScale(3)
                                 .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("WALK_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(1, 0), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 1), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 2), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(1, 3), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("JUMP_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                    .withScale(3)
                    .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("JUMP_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("FALL_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                    .withScale(3)
                    .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("FALL_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 1))
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("CROUCH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0))
                    .withScale(3)
                        .withBounds(1, 3, 22, 20)
                            .build()
            });

            put("CROUCH_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(2, 0))
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 20)
                            .build()
            });

            put("DEATH_RIGHT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(0, 0), 8)
                    .withScale(3)
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
                    .withScale(3)
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
                    .withScale(3)
                    .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 1), 7)
                    .withScale(3)
                    .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 2), 7)
                    .withScale(3)
                    .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 3), 7)
                    .withScale(3)
                    .withBounds(1, 3, 22, 18)
                            .build()
            });

            put("CLIMB_LEFT", new Frame[] {
                    new FrameBuilder(spriteSheet.getSprite(3, 0), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 1), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 2), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build(),
                    new FrameBuilder(spriteSheet.getSprite(3, 3), 7)
                    .withScale(3)
                            .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                            .withBounds(1, 3, 22, 18)
                            .build()
            });
        }};
    }
}
