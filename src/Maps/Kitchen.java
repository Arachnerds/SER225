package Maps;

import Enemies.Fire;
import EnhancedMapTiles.Barrier;
import EnhancedMapTiles.KitchenEndLevelTile;
import EnhancedMapTiles.PushableBlock;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.KitchenTileset;
import Utils.Direction;
import java.util.ArrayList;

public class Kitchen extends Map {

    private Fire fire;

    public Kitchen() {
        super("kitchen.txt", new KitchenTileset());
        this.playerStartPosition = getMapTile(90, 13).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        fire = new Fire(getMapTile(49, 10).getLocation(), Direction.LEFT);
        enemies.add(fire);

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        for (int y = 21; y >= 0; y--) {
            Barrier barrier = new Barrier(getMapTile(0, y).getLocation());
            enhancedMapTiles.add(barrier);
        }

        for (int y = 21; y >= 0; y--) {
            Barrier barrier = new Barrier(getMapTile(99, y).getLocation());
            enhancedMapTiles.add(barrier);
        }

        KitchenEndLevelTile kitchenEndLevelTile = new KitchenEndLevelTile(getMapTile(91, 13).getLocation());
        enhancedMapTiles.add(kitchenEndLevelTile);

        PushableBlock fryingPan = new PushableBlock(getMapTile(40, 11).getLocation().subtractY(5), "FryingPan.png", 22, 10, 0, 5, 22, 5);
        enhancedMapTiles.add(fryingPan);
        enhancedMapTiles.add(fryingPan.getExtraHitbox());
        fryingPan.setScale(9);
        fryingPan.getExtraHitbox().setScale(9);
        fryingPan.setWebAttachmentAdjustmentX(-100);
        fryingPan.setDragRange(200);
            
        fire.setPan(fryingPan);

        return enhancedMapTiles;
    }
}