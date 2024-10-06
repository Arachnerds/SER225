package Maps;

import Level.Map;
import Tilesets.CommonTileset;
import java.util.ArrayList;
import Level.Projectile;
import Projectiles.Raindrop;

public class Outside extends Map {

    private static final int[] X_SPAWN_POINTS = {2, 10, 18, 24, 32, 40, 48, 56, 64};
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

                Raindrop raindrop = new Raindrop(getMapTile(x + xSpawnShift, y).getLocation(), 1, 3, 0, "Raindrop.png", getMapTile(x + xSpawnShift, 24).getLocation(), 17, 24);
                projectiles.add(raindrop);
            }
        }
        return projectiles;  
    }
}