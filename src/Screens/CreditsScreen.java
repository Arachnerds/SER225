package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

public class CreditsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected Sprite titleScreen = new Sprite(ImageLoader.load("creditsScreen.png"), 0, 0);
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont brooksLabel;
    protected SpriteFont callandraLabel;
    protected SpriteFont ericLabel;
    protected SpriteFont ryanLabel;
    protected SpriteFont backLabel;
    protected boolean backSelected = true;

    protected float fadeValue = 1;
    protected boolean isFadingIn = true;
    protected boolean isFadingOut = false;
    protected long fadeStartTime;
    protected long fadeDuration = 2000;

    protected float colorTransition = 0;
    protected boolean increasing = true;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        background = new TitleScreenMap();
        background.setAdjustCamera(false);

        creditsLabel = new SpriteFont("Arachnerds Inc.", 0, 150, "Times New Roman", 45, Color.white);
        creditsLabel.setOutlineColor(Color.black);
        creditsLabel.setOutlineThickness(3);

        brooksLabel = new SpriteFont("Brooks Jackson - Full Stack", 0, 250, "Times New Roman", 35, Color.white);
        brooksLabel.setOutlineColor(Color.black);
        brooksLabel.setOutlineThickness(3);

        callandraLabel = new SpriteFont("Callandra Ruiter - Full Stack", 0, 300, "Times New Roman", 35, Color.white);
        callandraLabel.setOutlineColor(Color.black);
        callandraLabel.setOutlineThickness(3);

        ericLabel = new SpriteFont("Eric May - Full Stack", 0, 350, "Times New Roman", 35, Color.white);
        ericLabel.setOutlineColor(Color.black);
        ericLabel.setOutlineThickness(3);

        ryanLabel = new SpriteFont("Ryan Slattery - Full Stack", 0, 400, "Times New Roman", 35, Color.white);
        ryanLabel.setOutlineColor(Color.black);
        ryanLabel.setOutlineThickness(3);

        backLabel = new SpriteFont("Back", 25, 25, "Times New Roman", 30, Color.white);
        backLabel.setOutlineColor(Color.black);
        backLabel.setOutlineThickness(3);
        
        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
        background.update(null);

        // Handle fade-in
        if (isFadingIn) {
            fadeValue -= 0.02f;
            if (fadeValue <= 0) {
                fadeValue = 0;
                isFadingIn = false;
                fadeStartTime = System.currentTimeMillis();
            }
        }

        if (increasing) {
            colorTransition += 0.02f;
            if (colorTransition >= 1) {
                colorTransition = 1;
                increasing = false;
            }
        } else {
            colorTransition -= 0.02f;
            if (colorTransition <= 0) {
                colorTransition = 0;
                increasing = true;
            }
        }

        Color flashingColor = new Color(
            255,
            (int) (255 * (1 - colorTransition)),
            (int) (255 * (1 - colorTransition))
        );

        if (backSelected) {
            backLabel.setColor(flashingColor);
        } else {
            backLabel.setColor(Color.white);
        }

        if (!isFadingIn && !isFadingOut) {
            if (Keyboard.isKeyUp(Key.SPACE)) {
                keyLocker.unlockKey(Key.SPACE);
            }

            if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE) && backSelected) {
                isFadingOut = true;
            }
        }

        if (isFadingOut) {
            fadeValue += 0.02f;
            if (fadeValue >= 1) {
                fadeValue = 1;
                screenCoordinator.setGameState(GameState.MENU);
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        titleScreen.draw(graphicsHandler);

        int centerX = ScreenManager.getScreenWidth();

        creditsLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        brooksLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        callandraLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        ericLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        ryanLabel.centerTextX(centerX, graphicsHandler.getGraphics());

        creditsLabel.draw(graphicsHandler);
        brooksLabel.draw(graphicsHandler);
        callandraLabel.draw(graphicsHandler);
        ericLabel.draw(graphicsHandler);
        ryanLabel.draw(graphicsHandler);
        backLabel.draw(graphicsHandler);

        // Draw the fade effect overlay
        if (fadeValue > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) (fadeValue * 255));
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
        }
    }
}
