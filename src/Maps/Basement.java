package Maps;

import java.util.ArrayList;

import EnhancedMapTiles.BasementEndLevelTile;
import EnhancedMapTiles.BasementEndLevelTile2;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.CommonTileset;

public class Basement extends Map {

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(2, 13).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        BasementEndLevelTile basementEndLevelTile = new BasementEndLevelTile(getMapTile(68, 2).getLocation());
        enhancedMapTiles.add(basementEndLevelTile);

        BasementEndLevelTile2 basementEndLevelTile2 = new BasementEndLevelTile2(getMapTile(68, 1).getLocation());
        enhancedMapTiles.add(basementEndLevelTile2);

        return enhancedMapTiles;
    }
}