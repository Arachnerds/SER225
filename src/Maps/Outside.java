package Maps;

import Level.Map;
import Tilesets.CommonTileset;

public class Outside extends Map {

    public Outside() {
        super("outside.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(1, 3).getLocation();
    }
}