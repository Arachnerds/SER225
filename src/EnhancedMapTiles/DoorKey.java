package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Sound;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.MapEntityStatus;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.util.HashMap;

public class DoorKey extends EnhancedMapTile {

    private float locationMax;
    private float locationMin;
    private boolean keyMovingUp = true;

    public DoorKey(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("DoorKey.png"), 13, 16), TileType.PASSABLE);
        this.locationMax = location.y + 3;
        this.locationMin = location.y - 3;
    }

    @Override
    public void update(Player player) {
        super.update(player);

        if (keyMovingUp) {
            this.setLocation(this.getX(), this.getY() + 0.2f);

            if (this.getY() >= locationMax) {
                keyMovingUp = false;
            }
        } else {
            this.setLocation(this.getX(), this.getY() - 0.2f);

            if (this.getY() <= locationMin) {
                keyMovingUp = true;
            }
        }

        if (intersects(player)) {
            Sound.playSoundEffect(Sound.SoundEffect.KEY);
            player.setHasKey(true);
            this.setMapEntityStatus(MapEntityStatus.REMOVED);
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(2)
                        .withBounds(1, 1, 13, 16)
                        .build(),
            });
        }};
    }
}
