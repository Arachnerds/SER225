package EnhancedMapTiles;

import Engine.Key;
import Utils.Point;

public class SwitchesPuzzle {
    PuzzleSwitch s1;
    PuzzleSwitch s2;
    PuzzleSwitch s3;
    PuzzleSwitch s4;

    public SwitchesPuzzle(Point l){
        s1 = new PuzzleSwitch(this,l,1, "SWITCHED", Key.ONE);
        s2 = new PuzzleSwitch(this,l,2, "DEFAULT", Key.TWO);
        s3 = new PuzzleSwitch(this,l,3, "SWITCHED", Key.THREE);
        s4 = new PuzzleSwitch(this,l,4, "SWITCHED", Key.FOUR);
    }

    public void respond(PuzzleSwitch s) {
        if(s.getSwitchNum() == 1){
            s1.flip();
        } else if(s.getSwitchNum() == 2){
            s2.flip();
        } else if(s.getSwitchNum() == 3){
            s3.flip();
        } else {
            s4.flip();
        }

        if(s1.isOn() && s2.isOn() && s3.isOn() && s4.isOn()){
            System.out.println("You completed the puzzle!");
        }
    }

    public PuzzleSwitch getS1(){
        return s1;
    }
    public PuzzleSwitch getS2(){
        return s2;
    }
    public PuzzleSwitch getS3(){
        return s3;
    }
    public PuzzleSwitch getS4(){
        return s4;
    }
}
