package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class FakeDoor extends EnhancedMapTile {
    private boolean isTransparent = false;

    public FakeDoor(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("FakeDoor.png"), 128, 288), TileType.PASSABLE);
        this.currentAnimationName = "DEFAULT";
    }

    public void setTransparent(boolean transparent) {
        this.isTransparent = transparent;
        if (transparent) {
            this.currentAnimationName = "TRANSPARENT";
            this.tileType = TileType.PASSABLE;
        } else {
            this.currentAnimationName = "DEFAULT";
            this.tileType = TileType.NOT_PASSABLE;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(0, 0, 128, 288)
                        .build(),
            });
            put("TRANSPARENT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40) // Frame 1 for TRANSPARENT
                        .withScale(3)
                        .withBounds(0, 0, 128, 288)
                        .build(),
            });
        }};
    }
}
