package Maps;

import Level.Map;
import Tilesets.KitchenTileset;

public class Kitchen extends Map {

    public Kitchen() {
        super("kitchen.txt", new KitchenTileset());
        this.playerStartPosition = getMapTile(4, 10).getLocation();
    }
}