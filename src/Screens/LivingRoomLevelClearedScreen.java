package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import SpriteFont.SpriteFont;

import java.awt.*;

public class LivingRoomLevelClearedScreen extends Screen {
    protected SpriteFont winMessage;
    protected float fadeValue = 1;
    protected boolean isFadingIn = true;
    protected boolean isFadingOut = false;
    protected boolean isFadeComplete = false;
    protected long displayStartTime;
    protected long displayDuration = 4000;

    public LivingRoomLevelClearedScreen() {
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Level Complete!", 0, 0, "Times New Roman", 35, Color.white);
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
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.centerTextX(ScreenManager.getScreenWidth(), graphicsHandler.getGraphics());
        winMessage.centerTextY(ScreenManager.getScreenHeight(), graphicsHandler.getGraphics());
        winMessage.draw(graphicsHandler);

        if (fadeValue > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) (fadeValue * 255));
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
        }
    }
}
