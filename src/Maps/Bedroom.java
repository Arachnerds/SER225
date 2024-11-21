package Maps;

import Enemies.FireflyEnemy;
import EnhancedMapTiles.Barrier;
import EnhancedMapTiles.BedroomEndLevelTile;
import EnhancedMapTiles.FakeDoor;
import EnhancedMapTiles.FakeDoorknob;
import EnhancedMapTiles.FireflyJar;
import EnhancedMapTiles.JumpPoint;
import EnhancedMapTiles.RotatingPuzzle;
import Level.Enemy;
import Level.EnhancedMapTile;
import Level.Map;
import Level.NPC;
import NPCs.OldSpider;
import Tilesets.CommonTileset;
import Utils.Direction;
import java.util.ArrayList;

public class Bedroom extends Map {

    public Bedroom() {
        super("bedroom.txt", new CommonTileset());
        this.playerStartPosition = getMapTile(66, 8).getLocation();
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        
        FireflyEnemy fireflyEnemy = new FireflyEnemy(getMapTile(25, 11).getLocation().subtractY(5), Direction.LEFT, this);
        enemies.add(fireflyEnemy);

        FireflyEnemy fireflyEnemy2 = new FireflyEnemy(getMapTile(15, 17).getLocation().subtractY(5), Direction.LEFT, this);
        enemies.add(fireflyEnemy2);

        return enemies;
    }

    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        for (int y = 19; y >= 0; y--) {
            Barrier barrier = new Barrier(getMapTile(0, y).getLocation());
            enhancedMapTiles.add(barrier);
        }

        // Create a JumpPoint with custom hitbox
        float customHitboxX = -100;  // Shift left by 100 pixels
        float customHitboxY = -100;  // Shift up by 100 pixels
        int customHitboxWidth = 200; // Width of 200 pixels
        int customHitboxHeight = 300; // Height of 200 pixels

        // Using the custom hitbox constructor
        JumpPoint customJumpPoint = new JumpPoint(getMapTile(7, 8).getLocation(), customHitboxX, customHitboxY, customHitboxWidth, customHitboxHeight);

        /* DoorKey key = new DoorKey(getMapTile(41, 4).getLocation());
        
        enhancedMapTiles.add(key); */

        //41, 2 addY 24 gets it right on the shelf
        FireflyJar jar = new FireflyJar(getMapTile(39, 2).getLocation().addY(24));
        enhancedMapTiles.add(jar);
        enhancedMapTiles.add(jar.getExtraHitbox());
        enhancedMapTiles.add(jar.getDoorKey());

        RotatingPuzzle puzzle = new RotatingPuzzle(this, getMapTile(7, 11).getLocation().addX(20));
        
        enhancedMapTiles.add(puzzle.getP1());
        enhancedMapTiles.add(puzzle.getP2());
        enhancedMapTiles.add(puzzle.getP3());
        enhancedMapTiles.add(puzzle.getLockCase());
        
        enhancedMapTiles.add(customJumpPoint);

        FakeDoor fakeDoor = new FakeDoor(getMapTile(1, 1).getLocation());
        enhancedMapTiles.add(fakeDoor);

        FakeDoorknob fakeDoorknob = new FakeDoorknob(getMapTile(7, 9).getLocation());
        enhancedMapTiles.add(fakeDoorknob);

        BedroomEndLevelTile bedroomEndLevelTile = new BedroomEndLevelTile(getMapTile(1, 18).getLocation());
        enhancedMapTiles.add(bedroomEndLevelTile);

        return enhancedMapTiles;
    }

    public void hideFakeDoor() {
        for (EnhancedMapTile tile : this.getEnhancedMapTiles()) {
            if (tile instanceof FakeDoor) {
                ((FakeDoor) tile).setTransparent(true);
            }
            if (tile instanceof FakeDoorknob) {
                ((FakeDoorknob) tile).setTransparent(true);
            }
            if (tile instanceof BedroomEndLevelTile) {
                ((BedroomEndLevelTile) tile).setTransparent(true);
            }
        }
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        // List of messeges that will be displayed in text boxes, IN ORDER
        ArrayList<String> messeges = new ArrayList<String>();
        messeges.add("I hope you got the key already!... (T)");
        messeges.add("Try webbing up to the doorknob... (T)");
        messeges.add("Use 1, 2, 3 to align the lock... (T)");
        messeges.add("Then head through the door. (T)");

        // TRY TO MAKE ALL MESSEGES THE SAME SIZE AND THEN CHOOSE OFFEST VALUE TO CENTER THE TEXT BOX
        // Offest value is the last integer parameter in OldSpider Contructor to position the textboxes

        OldSpider spiderNPC = new OldSpider(getMapTile(10, 17).getLocation().addY(6), "LEFT", messeges, -50);
        npcs.add(spiderNPC);

        return npcs;
    }
}