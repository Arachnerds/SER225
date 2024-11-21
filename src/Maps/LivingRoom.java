package Maps;

import Enemies.FlyEnemy;
import Enemies.RoachEnemy;
import EnhancedMapTiles.Barrier;
import EnhancedMapTiles.FakeLivingRoomDoor;
import EnhancedMapTiles.JumpPoint;
import EnhancedMapTiles.LivingRoomEndTile;
import EnhancedMapTiles.TVPuzzle1;
import EnhancedMapTiles.TVPuzzle2;
import EnhancedMapTiles.TVPuzzle3;
import EnhancedMapTiles.TVRemote;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import NPCs.OldSpider;
import Tilesets.LivingRoomTileset;
import Utils.Direction;
import java.util.ArrayList;

public class LivingRoom extends Map {

    public LivingRoom() {
        super("living_room.txt", new LivingRoomTileset());
        this.playerStartPosition = getMapTile(4, 10).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        FlyEnemy flyEnemy = new FlyEnemy(getMapTile(15, 18).getLocation().subtractY(25), Direction.LEFT, this);
        enemies.add(flyEnemy);

        RoachEnemy roachEnemy = new RoachEnemy(getMapTile(28, 15).getLocation().subtractY(5), Direction.LEFT, this);
        enemies.add(roachEnemy);

        RoachEnemy roachEnemy2 = new RoachEnemy(getMapTile(41, 15).getLocation().subtractY(5), Direction.LEFT, this);
        enemies.add(roachEnemy2);

        FlyEnemy flyEnemy2 = new FlyEnemy(getMapTile(48, 14).getLocation().subtractY(25), Direction.RIGHT, this);
        enemies.add(flyEnemy2);

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        for (int y = 19; y >= 0; y--) {
            Barrier barrier = new Barrier(getMapTile(0, y).getLocation());
            enhancedMapTiles.add(barrier);
        }

        for (int y = 19; y >= 0; y--) {
            FakeLivingRoomDoor fakeLivingRoomDoor = new FakeLivingRoomDoor(getMapTile(79, y).getLocation());
            enhancedMapTiles.add(fakeLivingRoomDoor);
        }

        // Create puzzles
        TVPuzzle1 tvPuzzle1 = new TVPuzzle1(getMapTile(57, 10).getLocation());
        enhancedMapTiles.add(tvPuzzle1);

        TVPuzzle2 tvPuzzle2 = new TVPuzzle2(getMapTile(61, 10).getLocation());
        enhancedMapTiles.add(tvPuzzle2);

        TVPuzzle3 tvPuzzle3 = new TVPuzzle3(getMapTile(65, 10).getLocation());
        enhancedMapTiles.add(tvPuzzle3);

        // Create TVRemote and link puzzles
        TVRemote tvRemote = new TVRemote(this, getMapTile(62, 13).getLocation(), tvPuzzle1, tvPuzzle2, tvPuzzle3);
        enhancedMapTiles.add(tvRemote);

        JumpPoint testJumpPoint = new JumpPoint(getMapTile(19, 12).getLocation(),"left");
        enhancedMapTiles.add(testJumpPoint);

        LivingRoomEndTile livingRoomEndTile = new LivingRoomEndTile(getMapTile(97, 18).getLocation());
        enhancedMapTiles.add(livingRoomEndTile);

        return enhancedMapTiles;
    }

    public void hideDoor() {
        for (EnhancedMapTile tile : this.getEnhancedMapTiles()) {
            if (tile instanceof FakeLivingRoomDoor) {
                ((FakeLivingRoomDoor) tile).setTransparent(true);
            }
        }
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // List of messeges that will be displayed in text boxes, IN ORDER
        ArrayList<String> messeges = new ArrayList<String>();
        messeges.add("The door ahead is locked... (T)");
        messeges.add("The pictures on the screen might help unlock it. (T)");
        messeges.add("Press 1, 2, and 3 on the remote to move them. (T)");
        messeges.add("They must have some meaning though... (T)");
        messeges.add("Oh and one more thing... (T)");
        messeges.add("The man you're looking for is in the room ahead. (T)");
        messeges.add("I wasn't able to get him last time... (T)");
        messeges.add("but I think you've got the best chance of anyone. (T)");
        messeges.add("I'll be waiting for you at the front door. (T)");
        messeges.add("Go get him, my boy. Make me proud. (T)");

        OldSpider spiderNPC = new OldSpider(getMapTile(66, 13).getLocation(), "LEFT", messeges, -50);
        npcs.add(spiderNPC);

        return npcs;
    }
}
