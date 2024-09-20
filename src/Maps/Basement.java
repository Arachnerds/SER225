package Maps;

import Level.Map;
import Tilesets.CommonTileset;

public class Basement extends Map {

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(2, 13).getLocation();
    }
}