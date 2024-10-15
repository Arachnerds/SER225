package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import SpriteFont.SpriteFont;
import GameObject.Sprite;

import java.awt.*;

public class ArachnerdsProject extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected Sprite arachnerdsImage;
    protected int scaledImageWidth;
    protected int scaledImageHeight;

    protected float fadeValue = 1;
    protected boolean isFadingIn = false;
    protected boolean isFadingOut = false;
    protected long fadeInDelayStartTime;
    protected long displayStartTime;
    protected long displayDuration = 2000;
    protected boolean isDelayComplete = false;

    public ArachnerdsProject(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        creditsLabel = new SpriteFont("An Arachnerds Project", 0, 0, "Times New Roman", 45, Color.white);
        creditsLabel.setOutlineColor(Color.black);
        creditsLabel.setOutlineThickness(3);

        arachnerdsImage = new Sprite(ImageLoader.load("arachnerds.jpg"), 0, 0);

        scaledImageWidth = arachnerdsImage.getWidth() / 2;
        scaledImageHeight = arachnerdsImage.getHeight() / 2;

        keyLocker.lockAllKeys();

        fadeValue = 0;
        fadeInDelayStartTime = System.currentTimeMillis();
    }

    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!isDelayComplete) {
            if (System.currentTimeMillis() - fadeInDelayStartTime >= 1000) {
                isFadingIn = true;
                isDelayComplete = true;
            }
            return;
        }

        if (isFadingIn) {
            fadeValue += 0.02f;
            if (fadeValue >= 1) {
                fadeValue = 1;
                isFadingIn = false;
                displayStartTime = System.currentTimeMillis();
            }
        }

        if (!isFadingIn && !isFadingOut) {
            if (System.currentTimeMillis() - displayStartTime >= displayDuration) {
                isFadingOut = true;
            }
        }

        if (isFadingOut) {
            fadeValue -= 0.02f;
            if (fadeValue <= 0) {
                fadeValue = 0;
                screenCoordinator.setGameState(GameState.INTRO_CUTSCENE);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);

        int imageX = (ScreenManager.getScreenWidth() - scaledImageWidth) / 2;
        int imageY = (ScreenManager.getScreenHeight() * 5 / 6 - scaledImageHeight) / 2;

        graphicsHandler.drawImage(arachnerdsImage.getImage(), imageX, imageY, scaledImageWidth, scaledImageHeight);

        creditsLabel.centerTextX(ScreenManager.getScreenWidth(), graphicsHandler.getGraphics());
        creditsLabel.setY(ScreenManager.getScreenHeight() * 2 / 3);

        creditsLabel.draw(graphicsHandler);

        Color fadeColor = new Color(0, 0, 0, (int) ((1 - fadeValue) * 255));
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
    }
}
