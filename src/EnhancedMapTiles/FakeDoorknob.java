package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class FakeDoorknob extends EnhancedMapTile {
    private boolean isTransparent = false;

    public FakeDoorknob(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("FakeDoorknob.png"), 32, 32), TileType.JUMP_THROUGH_PLATFORM);
        this.currentAnimationName = "DEFAULT";
    }

    public void setTransparent(boolean transparent) {
        this.isTransparent = transparent;
        if (transparent) {
            this.currentAnimationName = "TRANSPARENT";
            this.tileType = TileType.PASSABLE;
        } else {
            this.currentAnimationName = "DEFAULT";
            this.tileType = TileType.JUMP_THROUGH_PLATFORM;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(9, 15, 24, 18)
                        .build(),
            });
            put("TRANSPARENT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40) // Frame 1 for TRANSPARENT
                        .withScale(3)
                        .withBounds(9, 15, 24, 18)
                        .build(),
            });
        }};
    }
}
