package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.NPC;
import Level.Player;
import Level.TileType;
import NPCs.OldSpider;
import Utils.Point;
import java.util.HashMap;

public class TVRemote extends EnhancedMapTile {

    private TVPuzzle1 tvPuzzle1;
    private TVPuzzle2 tvPuzzle2;
    private TVPuzzle3 tvPuzzle3;
    private KeyLocker keyLocker = new KeyLocker();

    // Constructor that accepts location and puzzles
    public TVRemote(Point location, TVPuzzle1 tvPuzzle1, TVPuzzle2 tvPuzzle2, TVPuzzle3 tvPuzzle3) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("remote.png"), 32, 32), TileType.PASSABLE);
        this.tvPuzzle1 = tvPuzzle1;
        this.tvPuzzle2 = tvPuzzle2;
        this.tvPuzzle3 = tvPuzzle3;
    }

    @Override
    public void update(Player player) {
        super.update(player);

        // Check if the player is in the hitbox of the TVRemote
        if (intersects(player)) {
            // Check Key.ONE for TVPuzzle1
            if (Keyboard.isKeyDown(Key.ONE) && !keyLocker.isKeyLocked(Key.ONE)) {
                keyLocker.lockKey(Key.ONE);
                tvPuzzle1.cycleFrame(); // cycle frame for TVPuzzle1
            }
            if (Keyboard.isKeyUp(Key.ONE) && keyLocker.isKeyLocked(Key.ONE)) {
                keyLocker.unlockKey(Key.ONE);
            }

            // Check Key.TWO for TVPuzzle2
            if (Keyboard.isKeyDown(Key.TWO) && !keyLocker.isKeyLocked(Key.TWO)) {
                keyLocker.lockKey(Key.TWO);
                tvPuzzle2.cycleFrame(); // cycle frame for TVPuzzle2
            }
            if (Keyboard.isKeyUp(Key.TWO) && keyLocker.isKeyLocked(Key.TWO)) {
                keyLocker.unlockKey(Key.TWO);
            }

            // Check Key.THREE for TVPuzzle3
            if (Keyboard.isKeyDown(Key.THREE) && !keyLocker.isKeyLocked(Key.THREE)) {
                keyLocker.lockKey(Key.THREE);
                tvPuzzle3.cycleFrame(); // cycle frame for TVPuzzle3
            }
            if (Keyboard.isKeyUp(Key.THREE) && keyLocker.isKeyLocked(Key.THREE)) {
                keyLocker.unlockKey(Key.THREE);
            }

            // Check if all three puzzles are on the DEFAULT frame
            if (tvPuzzle1.isOnDefault() && tvPuzzle2.isOnDefault() && tvPuzzle3.isOnDefault()) {
                player.completeLevel();
            }
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(3)
                        .withBounds(0, 0, 32, 32)
                        .build(),
            });
        }};
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
    }
}
