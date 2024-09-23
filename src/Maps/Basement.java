package Maps;

import EnhancedMapTiles.Anchor;
import EnhancedMapTiles.BasementEndLevelTile;
import EnhancedMapTiles.BasementEndLevelTile2;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.CommonTileset;
import Utils.Direction;

import java.util.ArrayList;

//import Enemies.BugEnemy;
//import Enemies.DinosaurEnemy;
import Enemies.FlyEnemy;
import Enemies.RoachEnemy;

public class Basement extends Map {

    public Basement() {
        super("basement.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(2, 13).getLocation();
    }

   
     @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        //FlyEnemy flyEnemy = new FlyEnemy(getMapTile(5, 1).getLocation().addY(5), Direction.LEFT);
        //enemies.add(flyEnemy);

        FlyEnemy flyEnemy = new FlyEnemy(getMapTile(16, 10).getLocation().subtractY(25), Direction.RIGHT);
        enemies.add(flyEnemy);

        RoachEnemy roachEnemy = new RoachEnemy(getMapTile(5, 14).getLocation().subtractY(5), Direction.RIGHT);
        enemies.add(roachEnemy);

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        BasementEndLevelTile basementEndLevelTile = new BasementEndLevelTile(getMapTile(68, 2).getLocation());
        enhancedMapTiles.add(basementEndLevelTile);

        BasementEndLevelTile2 basementEndLevelTile2 = new BasementEndLevelTile2(getMapTile(68, 1).getLocation());
        enhancedMapTiles.add(basementEndLevelTile2);

        Anchor testAnchor = new Anchor(getMapTile(10, 10).getLocation());
        enhancedMapTiles.add(testAnchor);

        return enhancedMapTiles;
    }
}