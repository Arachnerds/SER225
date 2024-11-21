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

public class DangerousWater extends EnhancedMapTile {

    public DangerousWater(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("DangerWater.png"), 16, 16), TileType.PASSABLE);
        this.currentAnimationName = "DEFAULT";
    }

    @Override
    public void update(Player player) {
        super.update(player);
        
        if (this.intersects(player) ) {
            player.killPlayer(this);
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
        }};
    }
}
