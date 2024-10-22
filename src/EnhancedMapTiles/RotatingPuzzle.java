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

        //Notes on positioning:
        //Counting pixels on sprite sheets middle is 5 pixels further right/down
        //Multiply by the scale of 3
        //Subtract 3(1) (Something about the way I'm counting)
        //Same idea for inner ring, but do that on top of the adjustment for the middle ring
        p1 = new RotatingPuzzlePiece(this, location.x, location.y, Key.ONE, "RIGHT","OuterKeyRing.png",18,18);
        p2 = new RotatingPuzzlePiece(this, location.x + 12, location.y+12, Key.TWO, "LEFT","MiddleKeyRing.png",10,10);
        p3 = new RotatingPuzzlePiece(this, location.x + 12+6, location.y+12+6, Key.THREE, "DOWN","InnerKeyRing.png",6,6);
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
