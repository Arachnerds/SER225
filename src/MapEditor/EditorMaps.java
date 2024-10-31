package MapEditor;

import Level.Map;
import Maps.*;

import java.util.ArrayList;

public class EditorMaps {
    public static ArrayList<String> getMapNames() {
        return new ArrayList<String>() {{
            add("TestMap");
            add("TitleScreen");
            add("Basement");
            add("Outside");
            add("Bedroom");
            add("Final");
        }};
    }

    public static Map getMapByName(String mapName) {
        switch(mapName) {
            case "TestMap":
                return new TestMap();
            case "TitleScreen":
                return new TitleScreenMap();
            case "Basement":
                return new Basement();
            case "Outside":
                return new Outside();
            case "Bedroom":
                return new Bedroom();
            case "Final":
                return new Final();
            default:
                throw new RuntimeException("Unrecognized map name");
        }
    }
}
