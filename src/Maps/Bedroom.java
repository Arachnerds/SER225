package Maps;

import Level.Map;
import Tilesets.CommonTileset;

public class Bedroom extends Map {

    public Bedroom() {
        super("bedroom.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(4, 10).getLocation();
    }
}