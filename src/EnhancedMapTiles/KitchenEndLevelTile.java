package EnhancedMapTiles;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import Builders.FrameBuilder;
import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import GameObject.Frame;
import GameObject.SpriteSheet;
import Level.EnhancedMapTile;
import Level.Player;
import Level.Textbox;
import Level.TileType;
import Utils.Point;

public class KitchenEndLevelTile extends EnhancedMapTile {

    private List<Textbox> dialogueSequence;
    private int currentTextboxIndex;
    private boolean showingDialogue;

    private KeyLocker keyLocker;
    private Key progressKey;

    public KitchenEndLevelTile(Point location) {
        super(location.x, location.y, new SpriteSheet(ImageLoader.load("KitchenExit.png"), 16, 16), TileType.PASSABLE);

        dialogueSequence = new ArrayList<>();
        dialogueSequence.add(createTextbox("Another spider? You just don't know when to quit, do you? (T)"));
        dialogueSequence.add(createTextbox("Your grandfather tried to stop me, and look how that ended for him. (T)"));
        dialogueSequence.add(createTextbox("Do you think you're any different? Do you think you can win where he failed? (T)"));
        dialogueSequence.add(createTextbox("Fine. If you're so eager to follow in his footsteps, I'll crush you just like I crushed him. (T)"));
        dialogueSequence.add(createTextbox("Let's finish this. (T)"));

        currentTextboxIndex = 0;
        showingDialogue = false;

        keyLocker = new KeyLocker();
        progressKey = Key.T;
    }

    @Override
    public void update(Player player) {
        super.update(player);

        if (!showingDialogue && intersects(player)) {
            showingDialogue = true;
            player.setInTutorial(true);
        }

        if (showingDialogue) {
            if (Keyboard.isKeyDown(progressKey) && !keyLocker.isKeyLocked(progressKey)) {
                keyLocker.lockKey(progressKey);
                currentTextboxIndex++;
                if (currentTextboxIndex >= dialogueSequence.size()) {
                    showingDialogue = false;
                    player.completeLevel();
                }
            }

            if (Keyboard.isKeyUp(progressKey)) {
                keyLocker.unlockKey(progressKey);
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        super.draw(graphicsHandler);

        if (showingDialogue && currentTextboxIndex < dialogueSequence.size()) {
            Textbox currentTextbox = dialogueSequence.get(currentTextboxIndex);

            int targetX = 500;
            int targetY = 200;

            centerTextbox(currentTextbox, targetX, targetY, graphicsHandler);

            currentTextbox.draw(graphicsHandler);
        }
    }

    @Override
    public HashMap<String, Frame[]> loadAnimations(SpriteSheet spriteSheet) {
        return new HashMap<String, Frame[]>() {{
            put("DEFAULT", new Frame[]{
                new FrameBuilder(spriteSheet.getSprite(0, 0), 40)
                        .withScale(3)
                        .withBounds(1, 1, 16, 16)
                        .build()
            });
        }};
    }

    private Textbox createTextbox(String text) {
        Textbox textbox = new Textbox(text);
        textbox.setFillColor(Color.BLACK);
        textbox.setTextColor(Color.RED);
        textbox.setBorderColor(Color.RED);
        textbox.setBorderThickness(3);
        textbox.setFontName("Times New Roman");
        return textbox;
    }

    private void centerTextbox(Textbox textbox, int targetX, int targetY, GraphicsHandler graphicsHandler) {
        Dimension textboxSize = textbox.getCalculatedBoxSize(graphicsHandler.getGraphics());
        int offsetX = targetX - (textboxSize.width / 2);
        int offsetY = targetY - (textboxSize.height / 2);
        textbox.setLocation(offsetX, offsetY);
    }
}
