package EnhancedMapTiles;

import Engine.Key;
import Level.Map;
import Level.Player;
import Utils.Point;

public class RotatingPuzzle {

    private RotatingPuzzlePiece p1;
    private RotatingPuzzlePiece p2;
    private RotatingPuzzlePiece p3;
    private Map map;

    public RotatingPuzzle(Map map, Point location) {
        this.map = map;

        // Create the pieces of the rotating puzzle
        // CURRENT POSITIONING/LOCATION OF PEICES IS NOT CORRECT
        // THEY NEED TO OVERLAP AND BE CENTERED ON EACHOTHER
        p1 = new RotatingPuzzlePiece(this, location.x, location.y, Key.ONE, "RIGHT");
        p2 = new RotatingPuzzlePiece(this, location.x + map.getMapTile(1, 1).getWidth(), location.y, Key.TWO, "LEFT");
        p3 = new RotatingPuzzlePiece(this, location.x + map.getMapTile(1, 1).getWidth()*2, location.y, Key.THREE, "DOWN");
    }

    public void respond(RotatingPuzzlePiece p, Player player) {
        // Rotate passed puzzle piece
        p.rotate();

        // Check if all peices are in correct location, then handle what happens on the map
        if (p1.isCorrect() && p2.isCorrect() && p3.isCorrect() && player.hasKey()) {
            hideSwitches();
            player.setHasKey(false);
        }
    }

    private void hideSwitches() {
        // Set all switches to not visible
        p1.setVisible(false);
        p2.setVisible(false);
        p3.setVisible(false);
    }

    public RotatingPuzzlePiece getP1() {
        return p1;
    }

    public RotatingPuzzlePiece getP2() {
        return p2;
    }

    public RotatingPuzzlePiece getP3() {
        return p3;
    }
}
