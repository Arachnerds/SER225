package Maps;

import Level.EnhancedMapTile;
import Level.Map;
import Tilesets.CommonTileset;
import java.util.ArrayList;

import EnhancedMapTiles.SwitchesPuzzle;
import Level.Projectile;
import Projectiles.Raindrop;

public class Outside extends Map {

    private static final int[] X_SPAWN_POINTS = {2, 10, 18, 24, 32, 40};
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
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        // Create a single instance of SwitchesPuzzle with locations for each switch
        SwitchesPuzzle puzzle = new SwitchesPuzzle(
            this, // Pass the reference to this map
            getMapTile(58, 47).getLocation(), // Location for switch 1 (top-left)
            getMapTile(59, 47).getLocation(), // Location for switch 2 (top-right)
            getMapTile(58, 48).getLocation(), // Location for switch 3 (bottom-left)
            getMapTile(59, 48).getLocation()  // Location for switch 4 (bottom-right)
        );

        // Add all four switches from the single puzzle instance to the map
        enhancedMapTiles.add(puzzle.getS1());
        enhancedMapTiles.add(puzzle.getS2());
        enhancedMapTiles.add(puzzle.getS3());
        enhancedMapTiles.add(puzzle.getS4());

        return enhancedMapTiles;
    }

    // Method to remove or hide an EnhancedMapTile from the map by setting it to invisible
    public void removeEnhancedMapTile(EnhancedMapTile tile) {
        tile.setVisible(false);
    }
}
