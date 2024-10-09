package Level;

import Engine.GraphicsHandler;
import GameObject.Frame;
import GameObject.SpriteSheet;

import java.util.HashMap;

// This class is a base class for all enhanced map tiles in the game -- all enhanced map tiles should extend from it
public class EnhancedMapTile extends MapTile {
    private boolean visible = true; // Add a visibility property

    public EnhancedMapTile(float x, float y, HashMap<String, Frame[]> animations, TileType tileType) {
        super(x, y, animations, tileType);
    }

    public EnhancedMapTile(float x, float y, SpriteSheet spriteSheet, TileType tileType) {
        super(x, y, spriteSheet, tileType);
    }

    public EnhancedMapTile(float x, float y, Frame[] frames, TileType tileType) {
        super(x, y, frames, tileType);
    }

    public EnhancedMapTile(float x, float y, Frame frame, TileType tileType) {
        super(x, y, frame, tileType);
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    // Add a method to hide the tile
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void update(Player player) {
        super.update();
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // Only draw the tile if it is visible
        if (visible) {
            super.draw(graphicsHandler);
        }
    }
}
