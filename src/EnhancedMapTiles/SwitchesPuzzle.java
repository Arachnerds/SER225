package EnhancedMapTiles;

import Engine.Key;
import Maps.Outside;
import Utils.Point;

public class SwitchesPuzzle {
    private PuzzleSwitch s1;
    private PuzzleSwitch s2;
    private PuzzleSwitch s3;
    private PuzzleSwitch s4;
    private Outside map;

    public SwitchesPuzzle(Outside map, Point loc1, Point loc2, Point loc3, Point loc4) {
        this.map = map;
        
        s1 = new PuzzleSwitch(this, loc1, 1, Key.ONE);
        s2 = new PuzzleSwitch(this, loc2, 2, Key.TWO);
        s3 = new PuzzleSwitch(this, loc3, 3, Key.THREE);
        s4 = new PuzzleSwitch(this, loc4, 4, Key.FOUR);

        //Setting the initial state of the puzzle
        s1.flip();
        s3.flip();
        
    }

    /* public void respond(PuzzleSwitch s) {
        // Flip the switch based on the provided switch instance
        if (s.getSwitchNum() == 1) {
            s1.flip();
        } else if (s.getSwitchNum() == 2) {
            s2.flip();
        } else if (s.getSwitchNum() == 3) {
            s3.flip();
        } else {
            s4.flip();
        }

        // Check if all switches are on, if so, hide them and trigger the map action
        if (s1.isOn() && s2.isOn() && s3.isOn() && s4.isOn()) {
            hideSwitches();
            map.hideFakeBark();
        }
    } */

    public void respond(PuzzleSwitch s){
        s.flip();
        if(s.getSwitchNum() == 1){
            s2.flip();
        }
        else if(s.getSwitchNum() == 2){
            s1.flip();
            s3.flip();
        }
        else if(s.getSwitchNum() == 3){
            s2.flip();
            s4.flip();
        }
        else{
            s3.flip();
        }
        if(s1.isOn()&&s2.isOn()&&s3.isOn()&&s4.isOn()){
            //System.out.println("You completed the puzzle!");
        }

        // Check if all switches are on, if so, hide them and trigger the map action
        if (s1.isOn() && s2.isOn() && s3.isOn() && s4.isOn()) {
            hideSwitches();
            map.hideFakeBark();
        }
    }

    private void hideSwitches() {
        // Set all switches to not visible
        s1.setVisible(false);
        s2.setVisible(false);
        s3.setVisible(false);
        s4.setVisible(false);
    }

    // Getter methods for each switch
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
