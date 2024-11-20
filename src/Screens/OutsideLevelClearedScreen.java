package Screens;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Screen;
import Engine.ScreenManager;
import GameObject.Sprite;
import Game.ScreenCoordinator;

import java.awt.*;

// This class is for the level cleared screen for basement levels
public class OutsideLevelClearedScreen extends Screen {
    protected Sprite cutscene; // The cutscene image sprite
    private boolean isArachnophobiaEnabled;

    // Fade effect variables
    protected float fadeValue = 1; // Fully black at start
    protected boolean isFadingIn = true;
    protected boolean isFadingOut = false;
    protected boolean isFadeComplete = false;
    protected long displayStartTime;
    protected long displayDuration = 4000; // Duration to display cutscene (4 seconds)

    public OutsideLevelClearedScreen(ScreenCoordinator screenCoordinator) {
        // Retrieve arachnophobia mode directly from the ScreenCoordinator
        this.isArachnophobiaEnabled = screenCoordinator.getArachnophobiaEnabled();
        String cutsceneImage = isArachnophobiaEnabled ? "levelThreeCutsceneWalrus.png" : "levelThreeCutscene.png";
        this.cutscene = new Sprite(ImageLoader.load(cutsceneImage), 0, 0);

        initialize();
    }

    @Override
    public void initialize() {
        fadeValue = 1; // Start fully black
        isFadingIn = true; // Begin with fade-in
        isFadingOut = false;
        isFadeComplete = false;
    }

    @Override
    public void update() {
        // Handle fade-in
        if (isFadingIn) {
            fadeValue -= 0.02f; // Gradually reduce opacity
            if (fadeValue <= 0) {
                fadeValue = 0;
                isFadingIn = false;
                isFadeComplete = true;
                displayStartTime = System.currentTimeMillis(); // Start timer for display duration
            }
        }

        // Handle fade-out after display duration
        if (isFadeComplete && !isFadingOut) {
            if (System.currentTimeMillis() - displayStartTime >= displayDuration) {
                isFadingOut = true;
            }
        }

        if (isFadingOut) {
            fadeValue += 0.02f; // Gradually increase opacity
            if (fadeValue >= 1) {
                fadeValue = 1;
                isFadingOut = false; // Transition complete
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // Draw the cutscene image
        cutscene.draw(graphicsHandler);

        // Draw the fade effect overlay
        if (fadeValue > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) (fadeValue * 255));
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
        }
    }
}
