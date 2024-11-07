package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Utils.Point;
import java.util.HashMap;
import java.util.Random;

public class TVPuzzle2 extends EnhancedMapTile {
    private String[] frames = {"DEFAULT", "SECOND", "THIRD"};
    private int currentFrameIndex = 0;

    public TVPuzzle2(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("TVPuzzle2.png"), 48, 48), TileType.PASSABLE);  
        Random rand = new Random();
        currentFrameIndex = rand.nextInt(2) + 1;
        currentAnimationName = frames[currentFrameIndex];
    }

    public void cycleFrame() {
        currentFrameIndex = (currentFrameIndex + 1) % frames.length;
        currentAnimationName = frames[currentFrameIndex];
    }

    public boolean isOnDefault() {
        return currentFrameIndex == 0;
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(3)
                        .withBounds(1, 1, 48, 48)
                        .build(),
            });
            put("SECOND", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 0)
                        .withScale(3)
                        .withBounds(1, 1, 48, 48)
                        .build(),
            });
            put("THIRD", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 2), 0)
                        .withScale(3)
                        .withBounds(1, 1, 48, 48)
                        .build(),
            });
        }};
    }
}
