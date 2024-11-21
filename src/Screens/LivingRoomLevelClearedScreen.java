package Screens;

import Engine.GraphicsHandler;
import Engine.ImageLoader;
import Engine.Screen;
import Engine.ScreenManager;
import GameObject.Sprite;
import Game.ScreenCoordinator;

import java.awt.*;

public class LivingRoomLevelClearedScreen extends Screen {
    protected Sprite cutscene;
    private boolean isArachnophobiaEnabled;
    protected float fadeValue = 1;
    protected boolean isFadingIn = true;
    protected boolean isFadingOut = false;
    protected boolean isFadeComplete = false;
    protected long displayStartTime;
    protected long displayDuration = 4000;

    public LivingRoomLevelClearedScreen(ScreenCoordinator screenCoordinator) {
        this.isArachnophobiaEnabled = screenCoordinator.getArachnophobiaEnabled();
        String cutsceneImage = isArachnophobiaEnabled ? "level5CutsceneWalrus.png" : "level5Cutscene.png";
        this.cutscene = new Sprite(ImageLoader.load(cutsceneImage), 0, 0);
        initialize();
    }

    @Override
    public void initialize() {
        fadeValue = 1;
        isFadingIn = true;
        isFadingOut = false;
        isFadeComplete = false;
    }

    @Override
    public void update() {
        if (isFadingIn) {
            fadeValue -= 0.02f;
            if (fadeValue <= 0) {
                fadeValue = 0;
                isFadingIn = false;
                isFadeComplete = true;
                displayStartTime = System.currentTimeMillis();
            }
        }

        if (isFadeComplete && !isFadingOut) {
            if (System.currentTimeMillis() - displayStartTime >= displayDuration) {
                isFadingOut = true;
            }
        }

        if (isFadingOut) {
            fadeValue += 0.02f;
            if (fadeValue >= 1) {
                fadeValue = 1;
                isFadingOut = false;
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        cutscene.draw(graphicsHandler);

        if (fadeValue > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) (fadeValue * 255));
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
        }
    }
}
