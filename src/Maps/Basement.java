package Maps;

import Enemies.FlyEnemy;
import Enemies.PillbugEnemy;
import Enemies.FireflyEnemy;
import EnhancedMapTiles.Anchor;
import EnhancedMapTiles.BasementEndLevelTile;
import EnhancedMapTiles.DoorKey;
import EnhancedMapTiles.JumpPoint;
import EnhancedMapTiles.RotatingPuzzle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Direction;
import java.util.ArrayList;

public class Basement extends Map {

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(4, 10).getLocation();
    }

   
     @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        /* FlyEnemy flyEnemy = new FlyEnemy(getMapTile(5, 1).getLocation().addY(5), Direction.LEFT);
        enemies.add(flyEnemy); */

        FlyEnemy flyEnemy = new FlyEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.RIGHT, this);
        enemies.add(flyEnemy);

       // RoachEnemy roachEnemy = new RoachEnemy(getMapTile(10, 14).getLocation().subtractY(5), Direction.RIGHT, this);
        //enemies.add(roachEnemy);

        FireflyEnemy fireflyEnemy = new FireflyEnemy(getMapTile(10, 9).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(fireflyEnemy);

       // PillbugEnemy pillEnemy = new PillbugEnemy(getMapTile(10, 13).getLocation().subtractY(5), Direction.RIGHT, this);
       // enemies.add(pillEnemy);
         
        /* BugEnemy bugEnemy = new BugEnemy(getMapTile(5, 14).getLocation().subtractY(5), Direction.RIGHT);
        enemies.add(bugEnemy); */

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        BasementEndLevelTile basementEndLevelTile = new BasementEndLevelTile(getMapTile(68, 2).getLocation());
        enhancedMapTiles.add(basementEndLevelTile);

        //Going to want to switch this back to an anchor, it is a jumppoint now for testing
        JumpPoint testJumpPoint = new JumpPoint(getMapTile(10, 10).getLocation(),"left");
        enhancedMapTiles.add(testJumpPoint);

        DoorKey key = new DoorKey(getMapTile(10, 13).getLocation());
        enhancedMapTiles.add(key);

        RotatingPuzzle puzzle = new RotatingPuzzle(this, getMapTile(7, 13).getLocation());
        enhancedMapTiles.add(puzzle.getP1());
        enhancedMapTiles.add(puzzle.getP2());
        enhancedMapTiles.add(puzzle.getP3());

        Anchor testAnchor = new Anchor(getMapTile(20, 9).getLocation());
        enhancedMapTiles.add(testAnchor);

        return enhancedMapTiles;
    }
}