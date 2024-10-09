package EnhancedMapTiles;

import Engine.Key;
import Utils.Point;
import Maps.Outside;

public class SwitchesPuzzle {
    private PuzzleSwitch s1;
    private PuzzleSwitch s2;
    private PuzzleSwitch s3;
    private PuzzleSwitch s4;
    private Outside map;
    
    public SwitchesPuzzle(Outside map, Point loc1, Point loc2, Point loc3, Point loc4) {
        this.map = map; // Store the map reference
        s1 = new PuzzleSwitch(this, loc1, 1, "SWITCHED", Key.ONE);
        s2 = new PuzzleSwitch(this, loc2, 2, "DEFAULT", Key.TWO);
        s3 = new PuzzleSwitch(this, loc3, 3, "SWITCHED", Key.THREE);
        s4 = new PuzzleSwitch(this, loc4, 4, "SWITCHED", Key.FOUR);
    }

    public void respond(PuzzleSwitch s) {
        if (s.getSwitchNum() == 1) {
            s1.flip();
        } else if (s.getSwitchNum() == 2) {
            s2.flip();
        } else if (s.getSwitchNum() == 3) {
            s3.flip();
        } else {
            s4.flip();
        }

        // Check if the puzzle is completed
        if (s1.isOn() && s2.isOn() && s3.isOn() && s4.isOn()) {
            System.out.println("You completed the puzzle!");
            hideSwitches();
            map.hideFakeBark();
        }
    }

    // Method to hide the switches
    private void hideSwitches() {
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
    }

    // Getters for each switch
    public PuzzleSwitch getS1() {
        return s1;
    }

    public PuzzleSwitch getS2() {
        return s2;
    }

    public PuzzleSwitch getS3() {
        return s3;
    }

    public PuzzleSwitch getS4() {
        return s4;
    }
}
