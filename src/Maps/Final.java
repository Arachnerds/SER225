package Maps;

import Enemies.BossHandEnemy;
import Enemies.BossMainEnemy;
import EnhancedMapTiles.JumpPoint;
import Level.*;
import Tilesets.KitchenTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class Final extends Map {

    private BossMainEnemy boss;

    public Final() {
        super("final.txt", new KitchenTileset());
        this.playerStartPosition = getMapTile(9, 17).getLocation();
    }

    
    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();

        Point lhandArea = new Point(this.getMapTile(3, 11).getX()-35, this.getMapTile(3, 10).getY()+60); // DETERMINE THESE LATER BASED ON MAP TILES LOCATION
        Point rhandArea = new Point(this.getMapTile(14, 11).getX()+20, this.getMapTile(14, 10).getY()+60);

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

        JumpPoint j1 = new JumpPoint(new Point(this.getMapTile(1, 12).getX()-40, this.getMapTile(1, 13).getY()));
        enhancedMapTiles.add(j1);

        JumpPoint j2 = new JumpPoint(new Point(this.getMapTile(19, 12).getX()-j1.getWidth()+40, this.getMapTile(18, 13).getY()));
        enhancedMapTiles.add(j2);

        JumpPoint j3 = new JumpPoint(new Point(this.getMapTile(9, 12).getX() + this.getMapTile(1, 1).getWidth()/2, this.getMapTile(8, 13).getY()));
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
