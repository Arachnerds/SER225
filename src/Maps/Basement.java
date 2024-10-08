package Maps;

import Enemies.FlyEnemy;
import Enemies.RoachEnemy;
import EnhancedMapTiles.Anchor;
import EnhancedMapTiles.BasementEndLevelTile;
import EnhancedMapTiles.JumpPoint;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Direction;
import java.util.ArrayList;

public class Basement extends Map {

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(2, 13).getLocation();
    }

   
     @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        /* FlyEnemy flyEnemy = new FlyEnemy(getMapTile(5, 1).getLocation().addY(5), Direction.LEFT);
        enemies.add(flyEnemy); */

        FlyEnemy flyEnemy = new FlyEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.RIGHT, this);
        enemies.add(flyEnemy);

        RoachEnemy roachEnemy = new RoachEnemy(getMapTile(5, 14).getLocation().subtractY(5), Direction.RIGHT, this);
        enemies.add(roachEnemy);
         
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


        Anchor testAnchor = new Anchor(getMapTile(20, 10).getLocation());
        enhancedMapTiles.add(testAnchor);

        return enhancedMapTiles;
    }
}