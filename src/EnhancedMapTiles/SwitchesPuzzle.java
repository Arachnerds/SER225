package EnhancedMapTiles;

import Utils.Point;

public class SwitchesPuzzle {
    PuzzleSwitch s1;
    PuzzleSwitch s2;
    PuzzleSwitch s3;
    PuzzleSwitch s4;

    public SwitchesPuzzle(Point l){
        s1 = new PuzzleSwitch(this,l,1);
        s2 = new PuzzleSwitch(this,l,2);
        s3 = new PuzzleSwitch(this,l,3);
        s4 = new PuzzleSwitch(this,l,4);
    }

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
