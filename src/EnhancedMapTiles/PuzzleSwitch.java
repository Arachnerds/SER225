package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import Utils.Point;
import java.util.HashMap;

public class PuzzleSwitch extends EnhancedMapTile{

    private int switchNumber;
    private SwitchesPuzzle puzzle;
    private String correctLocation;
    private Key key;
    protected KeyLocker keyLocker = new KeyLocker();

    public PuzzleSwitch(SwitchesPuzzle puzzle, Point location, int switchNumber, String correctLocation, Key key){
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("SwitchBox.png"), 16, 16), TileType.PASSABLE);
        this.switchNumber = switchNumber;
        this.puzzle = puzzle;
        this.currentAnimationName = "DEFAULT";
        this.key = key;
        this.correctLocation = correctLocation;
    }

    public void flip(){
        if (this.currentAnimationName.equals("DEFAULT")) {
            this.currentAnimationName = "SWITCHED";
        } else {
            this.currentAnimationName = "DEFAULT";   
        }
    }
    
    public int getSwitchNum(){
        return switchNumber;
    }

    @Override
    public void update(Player player) {
        super.update(player);

        if (intersects(player) && Keyboard.isKeyDown(key) && !keyLocker.isKeyLocked(key)) {
            keyLocker.lockKey(key);
            puzzle.respond(this);
        }

        if (Keyboard.isKeyUp(key) && keyLocker.isKeyLocked(key)) {
            keyLocker.unlockKey(key); 
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
                      
            put("SWITCHED", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 1), 40)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
        }};
    }

    public boolean isOn(){
        return this.correctLocation.equals(this.currentAnimationName);
    }

    //A testing method that displays the hitbox
    /*@Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }*/

    
}
