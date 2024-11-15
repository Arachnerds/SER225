package Maps;

import Enemies.BossHandEnemy;
import Enemies.BossMainEnemy;
import EnhancedMapTiles.JumpPoint;
import Level.*;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class Final extends Map {

    private BossMainEnemy boss;

    public Final() {
        super("final.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(10, 18).getLocation();
    }

    
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        Point lhandArea = new Point(this.getMapTile(5, 10).getX(), this.getMapTile(5, 10).getY()-20); // DETERMINE THESE LATER BASED ON MAP TILES LOCATION
        Point rhandArea = new Point(this.getMapTile(12, 10).getX(), this.getMapTile(12, 10).getY()-20);

        BossMainEnemy boss = new BossMainEnemy(this, 5);
        this.boss = boss;
        BossHandEnemy lHand = new BossHandEnemy(lhandArea, Direction.RIGHT, boss, this);
        enemies.add(lHand);

        BossHandEnemy rHand = new BossHandEnemy(rhandArea, Direction.LEFT, boss, this);
        enemies.add(rHand);
        boss.setInitialHands(lHand, rHand);

        return enemies;
    }

    
    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        JumpPoint j1 = new JumpPoint(new Point(this.getMapTile(3, 12).getX(), this.getMapTile(3, 12).getY()));
        enhancedMapTiles.add(j1);

        JumpPoint j2 = new JumpPoint(new Point(this.getMapTile(17, 12).getX(), this.getMapTile(17, 12).getY()));
        enhancedMapTiles.add(j2);

        JumpPoint j3 = new JumpPoint(new Point(this.getMapTile(9, 12).getX() + this.getMapTile(1, 1).getWidth()/2, this.getMapTile(8, 12).getY()));
        enhancedMapTiles.add(j3);

        return enhancedMapTiles;
    }

    public BossMainEnemy getBoss() {
        return boss;
    }

    /*
    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        Walrus walrus = new Walrus(getMapTile(30, 10).getLocation().subtractY(13));
        npcs.add(walrus);

        return npcs;
    }
    **/
}
