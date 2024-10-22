package EnhancedMapTiles;

import Builders.FrameBuilder;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.ImageEffect;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.TileType;
import java.util.HashMap;

public class RotatingPuzzlePiece extends EnhancedMapTile {
    private RotatingPuzzle puzzle;
    private Key key;
    protected KeyLocker keyLocker = new KeyLocker();
    private String imageFileName;

    public RotatingPuzzlePiece(RotatingPuzzle puzzle, float locationX, float locationY, Key key, String animationName,String fileName, int SpriteWidth, int SpriteHeight){
        // super(location.x, location.y, new SpriteSheet(ImageLoader.load("SwitchBox.png"), 16, 16), TileType.PASSABLE);
        super(locationX, locationY, new SpriteSheet(ImageLoader.load(fileName), SpriteWidth, SpriteHeight), TileType.PASSABLE);
        this.puzzle = puzzle;
        this.currentAnimationName = animationName;
        this.key = key;
        imageFileName = fileName;
    }

    // Rotate the piece 90 degrees clockwise from current position
    public void rotate() {
        switch(currentAnimationName) {
            case "DEFAULT":
                this.currentAnimationName = "RIGHT";
                break;
            case "RIGHT":
                this.currentAnimationName = "DOWN";
                break;
            case "DOWN":
                this.currentAnimationName = "LEFT";
                break;
            case "LEFT":
                this.currentAnimationName = "DEFAULT";
                break;
        }
    }

    public boolean isCorrect() {
        return this.currentAnimationName.equals("DEFAULT");
    }

    @Override
    public void update(Player player) {
        super.update(player);

        if (this.puzzleIntersects(player) && Keyboard.isKeyDown(key) && !keyLocker.isKeyLocked(key)) {
            keyLocker.lockKey(key);
            puzzle.respond(this, player);
        }

        if (Keyboard.isKeyUp(key) && keyLocker.isKeyLocked(key)) {
            keyLocker.unlockKey(key); 
        }
    }

    public boolean puzzleIntersects(Player player) {
        if(puzzle.getP1().intersects(player) || puzzle.getP2().intersects(player) || puzzle.getP3().intersects(player)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(3)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
            put("RIGHT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(3)
                        .withImageEffect(ImageEffect.ROTATE_90_CLOCKWISE)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
            put("DOWN", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(3)
                        .withImageEffect(ImageEffect.ROTATE_180_CLOCKWISE)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
            put("LEFT", new Frame[] {
                new FrameBuilder(spriteSheet.getSprite(0, 0), 0)
                        .withScale(3)
                        .withImageEffect(ImageEffect.ROTATE_270_CLOCKWISE)
                        .withBounds(1, 1, 14, 14)
                        .build(),
            });
        }};
    }

    //A testing method that displays the hitbox
    /*@Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);
        drawBounds(graphicsHandler, new Color(255, 0, 0, 100));
    }*/
    
}

