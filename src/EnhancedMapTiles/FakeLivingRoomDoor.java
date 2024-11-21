package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class FakeLivingRoomDoor extends EnhancedMapTile {
    private boolean isTransparent = false;

    public FakeLivingRoomDoor(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("FakeLivDoor.png"), 16, 16), TileType.NOT_PASSABLE);
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
                        .withBounds(1, 1, 16, 16)
                        .build(),
            });
            put("TRANSPARENT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(1, 1, 16, 16)
                        .build(),
            });
        }};
    }
}
