package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;

import java.util.HashMap;

public class BedroomEndLevelTile extends EnhancedMapTile {
    private boolean isTransparent = false;

    public BedroomEndLevelTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("BedExitTile.png"), 16, 16), TileType.PASSABLE);
        this.currentAnimationName = "TRANSPARENT";
    }

    @Override
    public void update(Player player) {
        super.update(player);
        if (intersects(player) && "DEFAULT".equals(currentAnimationName)) {
            player.completeLevel();
        }
    }

    public void setTransparent(boolean transparent) {
        this.isTransparent = transparent;
        if (transparent) {
            this.currentAnimationName = "DEFAULT";
            this.tileType = TileType.PASSABLE;
        } else {
            this.currentAnimationName = "TRANSPARENT";
            this.tileType = TileType.PASSABLE;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("TRANSPARENT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 16, 16)
                        .build(),
            });
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(1, 1, 16, 16)
                        .build(),
            });
        }};
    }
}
