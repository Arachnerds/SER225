package Maps;

import Enemies.BossHandEnemy;
import Enemies.BossMainEnemy;
import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Enemies.RoachEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.FakeBark;
import EnhancedMapTiles.HorizontalMovingPlatform;
import EnhancedMapTiles.JumpPoint;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class Final extends Map {

    public Final() {
        super("final.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(10, 18).getLocation();
    }

    
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        Point lhandArea = new Point(this.getMapTile(5, 10).getX(), this.getMapTile(5, 10).getY()-20); // DETERMINE THESE LATER BASED ON MAP TILES LOCATION
        Point rhandArea = new Point(this.getMapTile(12, 10).getX(), this.getMapTile(12, 10).getY()-20);

        BossMainEnemy boss = new BossMainEnemy(this, 3);
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

        JumpPoint j2 = new JumpPoint(new Point(this.getMapTile(17, 12).getX(), this.getMapTile(18, 12).getY()));
        enhancedMapTiles.add(j2);

        return enhancedMapTiles;
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
