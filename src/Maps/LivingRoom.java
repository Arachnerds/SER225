package Maps;

import Enemies.FlyEnemy;
import Enemies.RoachEnemy;
import EnhancedMapTiles.JumpPoint;
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

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // List of messeges that will be displayed in text boxes, IN ORDER
        ArrayList<String> messeges = new ArrayList<String>();
        messeges.add("There are some weird images on the screen...");
        messeges.add("Press 1, 2, and 3 on the remote to move them.");
        messeges.add("The pictures must have some meaning though...");
        messeges.add("Try to remember if you saw them before!");

        // TRY TO MAKE ALL MESSEGES THE SAME SIZE AND THEN CHOOSE OFFEST VALUE TO CENTER THE TEXT BOX
        // Offest value is the last integer parameter in OldSpider Contructor to position the textboxes

        OldSpider spiderNPC = new OldSpider(getMapTile(66, 13).getLocation(), "LEFT", messeges, -50,"OldSpiderFinal.png");
        npcs.add(spiderNPC);

        return npcs;
    }
}
