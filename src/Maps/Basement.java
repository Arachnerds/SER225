package Maps;

import Level.Map;
import Tilesets.CommonTileset;
import Utils.Point;

public class Basement extends Map {

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = new Point (2, 11);
    }
}