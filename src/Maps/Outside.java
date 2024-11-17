package Maps;

import Enemies.PillbugEnemy;
import Enemies.RoachEnemy;
import EnhancedMapTiles.FakeBark;
import EnhancedMapTiles.JumpPoint;
import EnhancedMapTiles.OutsideEndLevelTile;
import EnhancedMapTiles.SwitchesPuzzle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import Level.Projectile;
import NPCs.OldSpider;
import Projectiles.Raindrop;
import Tilesets.CommonTileset;
import Utils.Direction;
import java.util.ArrayList;

public class Outside extends Map {

    private static final int[] X_SPAWN_POINTS = {6, 12, 18, 24, 30, 36};
    private static final int[] Y_SPAWN_POINTS = {24, 30, 36, 42};
    private boolean alternateSpawn = false;

    public Outside() {
        super("outside.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(8, 48).getLocation();
    }

    @Override
    public ArrayList<Projectile> loadProjectiles() {
        ArrayList<Projectile> projectiles = new ArrayList<>();

        for (int y : Y_SPAWN_POINTS) {
            int xSpawnShift;
            if (alternateSpawn) {
                xSpawnShift = 4;
                alternateSpawn = false;
            } else {
                xSpawnShift = 0;
                alternateSpawn = true;
            }
            for (int x : X_SPAWN_POINTS) {
                Raindrop raindrop = new Raindrop(
                    getMapTile(x + xSpawnShift, y).getLocation(), 
                    1, 3, 0, 
                    "Raindrop.png", 
                    getMapTile(x + xSpawnShift, 24).getLocation(), 
                    17, 24
                );
                projectiles.add(raindrop);
            }
        }
        return projectiles;  
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        /* FlyEnemy flyEnemy = new FlyEnemy(getMapTile(51, 18).getLocation().subtractY(25), Direction.RIGHT, this);
        enemies.add(flyEnemy); */
        PillbugEnemy pillbug = new PillbugEnemy(getMapTile(54, 18).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(pillbug);


        RoachEnemy roachEnemy = new RoachEnemy(getMapTile(30, 46).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(roachEnemy);

        RoachEnemy roachEnemy2 = new RoachEnemy(getMapTile(41, 46).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(roachEnemy2);
         
        /* BugEnemy bugEnemy = new BugEnemy(getMapTile(5, 14).getLocation().subtractY(5), Direction.RIGHT);
        enemies.add(bugEnemy); */

        //A pillbug inside the tree - glitches outside of the tree
        /* PillbugEnemy pillbug2 = new PillbugEnemy(getMapTile(64, 36).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(pillbug2); */


        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        OutsideEndLevelTile outsideEndLevelTile = new OutsideEndLevelTile(getMapTile(4, 18).getLocation());
        enhancedMapTiles.add(outsideEndLevelTile);

        for (int x = 61; x <= 70; x++) {
            for (int y = 15; y <= 48; y++) {
                FakeBark fakeBark = new FakeBark(getMapTile(x, y).getLocation());
                enhancedMapTiles.add(fakeBark);
            }
        }

        for (int x = 62; x <= 69; x++) {
            FakeBark fakeBark1 = new FakeBark(getMapTile(x, 14).getLocation());
            enhancedMapTiles.add(fakeBark1);
        }

        for (int x = 63; x <= 68; x++) {
            FakeBark fakeBark2 = new FakeBark(getMapTile(x, 13).getLocation());
            enhancedMapTiles.add(fakeBark2);
        }

        enhancedMapTiles.add(new FakeBark(getMapTile(60, 48).getLocation()));
        enhancedMapTiles.add(new FakeBark(getMapTile(60, 47).getLocation()));
        enhancedMapTiles.add(new FakeBark(getMapTile(60, 17).getLocation()));
        enhancedMapTiles.add(new FakeBark(getMapTile(60, 18).getLocation()));

        SwitchesPuzzle puzzle = new SwitchesPuzzle(
            this, 
            getMapTile(56, 48).getLocation().addY(-30),
            getMapTile(57, 48).getLocation().addY(-30),
            getMapTile(58, 48).getLocation().addY(-30),
            getMapTile(59, 48).getLocation().addY(-30)
        );

        enhancedMapTiles.add(puzzle.getS1());
        enhancedMapTiles.add(puzzle.getS2());
        enhancedMapTiles.add(puzzle.getS3());
        enhancedMapTiles.add(puzzle.getS4());

        JumpPoint testJumpPoint = new JumpPoint(getMapTile(43, 15).getLocation(),"right");
        enhancedMapTiles.add(testJumpPoint);

        JumpPoint testJumpPoint2 = new JumpPoint(getMapTile(37, 15).getLocation(),"right");
        enhancedMapTiles.add(testJumpPoint2);
        
        JumpPoint testJumpPoint3 = new JumpPoint(getMapTile(32, 15).getLocation(),"right");
        enhancedMapTiles.add(testJumpPoint3);

        JumpPoint testJumpPoint4 = new JumpPoint(getMapTile(25, 15).getLocation(),"right");
        enhancedMapTiles.add(testJumpPoint4);

        JumpPoint testJumpPoint5 = new JumpPoint(getMapTile(18, 15).getLocation(),"right");
        enhancedMapTiles.add(testJumpPoint5);

        JumpPoint testJumpPoint6 = new JumpPoint(getMapTile(11, 15).getLocation(),"right");
        enhancedMapTiles.add(testJumpPoint6);

        return enhancedMapTiles;
    }

    public void hideFakeBark() {
        for (EnhancedMapTile tile : this.getEnhancedMapTiles()) {
            if (tile instanceof FakeBark) {
                ((FakeBark) tile).setTransparent(true);
            }
        }
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // List of messeges that will be displayed in text boxes, IN ORDER
        ArrayList<String> messeges = new ArrayList<String>();
        messeges.add("What took you so long? Not bad for an old spider on his 7th knee replacement, huh? (T)");
        messeges.add("It seems there is nowhere to go... (T)");
        messeges.add("See those sticks over there? ... (T)");
        messeges.add("Use 1, 2, 3, 4 to flip the sticks... (T)");
        messeges.add("Try to find the right combination! (T)");

        // TRY TO MAKE ALL MESSEGES THE SAME SIZE AND THEN CHOOSE OFFEST VALUE TO CENTER THE TEXT BOX
        // Offest value is the last integer parameter in OldSpider Contructor to position the textboxes

        OldSpider spiderNPC = new OldSpider(getMapTile(51, 47).getLocation().addY(13), "LEFT", messeges, -45);
        npcs.add(spiderNPC);

        return npcs;
    }
}
