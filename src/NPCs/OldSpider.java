package NPCs;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.NPC;
import Level.Player;
import Utils.Point;

import java.util.ArrayList;
import java.util.HashMap;

public class OldSpider extends NPC {

    public OldSpider(Point location, String animationDirection, ArrayList<String> messages, int offset) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("OldSpiderFinal.png"), 128, 128), animationDirection, messages);
        isInteractable = true;
        talkedToTime = 200;
        textboxOffsetX = offset;
        textboxOffsetY = 0;
    }

    public void update(Player player) {
        if (player.getX() > this.getX()) {
            this.currentAnimationName = "RIGHT";
        } else {
            this.currentAnimationName = "LEFT";
        }

        super.update(player);
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
           put("RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(0.75f)
                    .build()
           });
           put("LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0))
                    .withScale(0.75f)
                    .withImageEffect(ImageEffect.FLIP_HORIZONTAL)
                    .build()
    });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
