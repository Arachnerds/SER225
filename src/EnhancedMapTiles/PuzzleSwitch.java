package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.awt.Color;
import java.util.HashMap;

public class PuzzleSwitch extends EnhancedMapTile{
    private int switchNumber;
    private SwitchesPuzzle puzzle;


    public PuzzleSwitch(SwitchesPuzzle puzzle, Point location, int switchNumber){
        //This 80 times switch number just spaces them out in a nice line. Will need to be removed later
        super(location.x + 80*switchNumber, location.y, new SpriteSheet(ImageLoader.load("SwitchBox.png"), 16, 16), TileType.PASSABLE);
        this.switchNumber = switchNumber;
        this.puzzle = puzzle;
        /* if(switchNumber == 2 || switchNumber == 4){
            this.setCurrentAnimationName("OFF");
        } */
    }

    public void flip(){
        if(this.isOn()){
            this.setCurrentAnimationName("DEFAULT");
        }
        else{
            this.setCurrentAnimationName("ON");
        }
    }
    
    public int getSwitchNum(){
        return switchNumber;
    }

    @Override
    public void update(Player player) {
        super.update(player);

        if (intersects(player) && Keyboard.isKeyDown(Key.K)) {
            puzzle.respond(this);
        }    
    }


    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
                      
            put("ON", new Frame[] {
                
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
        }};
    }

    public boolean isOn(){
        return this.getCurrentAnimationName().equals("ON");
    }

    //A testing method that displays the hitbox
    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }

    
}
