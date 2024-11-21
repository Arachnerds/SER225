package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class Barrier extends EnhancedMapTile {

    public Barrier(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("Barrier.png"), 16, 16), TileType.NOT_PASSABLE);
        this.currentAnimationName = "DEFAULT";
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(0, 0, 16, 16)
                        .build(),
            });
        }};
    }
}
