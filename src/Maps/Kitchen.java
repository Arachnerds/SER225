package Maps;

import java.util.ArrayList;

import Enemies.Fire;
import EnhancedMapTiles.KitchenEndLevelTile;
import EnhancedMapTiles.PushableBlock;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.KitchenTileset;
import Utils.Direction;

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

        KitchenEndLevelTile kitchenEndLevelTile = new KitchenEndLevelTile(getMapTile(91, 13).getLocation());
        enhancedMapTiles.add(kitchenEndLevelTile);

        PushableBlock fryingPan = new PushableBlock(getMapTile(40, 11).getLocation().subtractY(5), "FryingPan.png", 22, 10, 0, 5, 22, 6);
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