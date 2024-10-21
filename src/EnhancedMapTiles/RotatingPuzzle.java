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

        //Rough estimate:
        //Pass in a location for the outer ring
        //The middle ought to be +4 x and y from that
        //The innermost ring should be +6 from the outer ring
        p1 = new RotatingPuzzlePiece(this, location.x, location.y, Key.ONE, "RIGHT","GoldBox.png");
        p2 = new RotatingPuzzlePiece(this, location.x + 4+20, location.y, Key.TWO, "LEFT","GoldBox.png");
        p3 = new RotatingPuzzlePiece(this, location.x + 6+40, location.y, Key.THREE, "DOWN","GoldBox.png");
    }

    public void respond(RotatingPuzzlePiece p, Player player) {
        // Rotate passed puzzle piece
        if(player.hasKey()){
            p.rotate();
        }
        

        // Check if all peices are in correct location, then handle what happens on the map
        if (p1.isCorrect() && p2.isCorrect() && p3.isCorrect() && player.hasKey()) {
            hideSwitches();
            player.setHasKey(false);
            System.out.println("You solved the puzzle!");
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
