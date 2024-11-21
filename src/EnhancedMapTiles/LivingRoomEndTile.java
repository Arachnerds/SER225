package EnhancedMapTiles;

import java.util.HashMap;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;

public class LivingRoomEndTile extends EnhancedMapTile {
    public LivingRoomEndTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("LivingRoomEndTile.png"), 16, 16), TileType.PASSABLE);
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (intersects(player)) {
            player.completeLevel();
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
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 16, 16)
                        .build(),
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 16, 16)
                        .build()
            });
        }};
    }
}
