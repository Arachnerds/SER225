package Maps;

import EnhancedMapTiles.JumpPoint;
import EnhancedMapTiles.TVPuzzle1;
import EnhancedMapTiles.TVPuzzle2;
import EnhancedMapTiles.TVPuzzle3;
import EnhancedMapTiles.TVRemote;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.LivingRoomTileset;
import java.util.ArrayList;

public class LivingRoom extends Map {

    public LivingRoom() {
        super("living_room.txt", new LivingRoomTileset());
        this.playerStartPosition = getMapTile(4, 10).getLocation();
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        // Create puzzles
        TVPuzzle1 tvPuzzle1 = new TVPuzzle1(getMapTile(57, 10).getLocation());
        enhancedMapTiles.add(tvPuzzle1);

        TVPuzzle2 tvPuzzle2 = new TVPuzzle2(getMapTile(61, 10).getLocation());
        enhancedMapTiles.add(tvPuzzle2);

        TVPuzzle3 tvPuzzle3 = new TVPuzzle3(getMapTile(65, 10).getLocation());
        enhancedMapTiles.add(tvPuzzle3);

        // Create TVRemote and link puzzles
        TVRemote tvRemote = new TVRemote(getMapTile(62, 13).getLocation(), tvPuzzle1, tvPuzzle2, tvPuzzle3);
        enhancedMapTiles.add(tvRemote);

        JumpPoint testJumpPoint = new JumpPoint(getMapTile(19, 12).getLocation(),"left");
        enhancedMapTiles.add(testJumpPoint);

        return enhancedMapTiles;
    }
}
