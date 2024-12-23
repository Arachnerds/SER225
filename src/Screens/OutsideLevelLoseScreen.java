package Screens;

import Engine.*;
import GameObject.Sprite;
import SpriteFont.SpriteFont;

import java.awt.*;

public class OutsideLevelLoseScreen extends Screen {
    protected PlayOutsideLevelScreen playOutsideLevelScreen;
    protected int currentMenuItemHovered = 0;
    protected int menuItemSelected = -1;
    protected SpriteFont retryOption;
    protected SpriteFont quitOption;
    protected SpriteFont deathMessage;
    protected Sprite background = new Sprite(ImageLoader.load("creditsScreenBlank.png"), 0, 0);
    protected int keyPressTimer;
    protected KeyLocker keyLocker = new KeyLocker();
    protected float colorTransition = 0;
    protected boolean increasing = true;

    // Fade effect variables
    protected float fadeValue = 1; // Fully black at start
    protected boolean isFadingIn = true;
    protected boolean isFadingOut = false;

    public OutsideLevelLoseScreen(PlayOutsideLevelScreen playLevelScreen) {
        this.playOutsideLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        retryOption = new SpriteFont("RETRY", 0, 325, "Times New Roman", 35, Color.white);
        retryOption.setOutlineColor(Color.black);
        retryOption.setOutlineThickness(3);

        quitOption = new SpriteFont("QUIT", 0, 325, "Times New Roman", 35, Color.white);
        quitOption.setOutlineColor(Color.black);
        quitOption.setOutlineThickness(3);

        deathMessage = new SpriteFont("You Died", 0, 200, "Times New Roman", 50, Color.RED);
        deathMessage.setOutlineColor(Color.black);
        deathMessage.setOutlineThickness(3);

        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);

        fadeValue = 1; // Start fully black
        isFadingIn = true;
        isFadingOut = false;
    }

    @Override
    public void update() {
        // Handle fade-in
        if (isFadingIn) {
            fadeValue -= 0.02f; // Gradually reduce opacity
            if (fadeValue <= 0) {
                fadeValue = 0;
                isFadingIn = false;
            }
        }

        // Update logic for menu navigation (only active after fade-in is complete)
        if (!isFadingIn && !isFadingOut) {
            if ((Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) && keyPressTimer == 0 && currentMenuItemHovered == 0) {
                keyPressTimer = 14;
                currentMenuItemHovered++;
            } else if ((Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) && keyPressTimer == 0 && currentMenuItemHovered == 1) {
                keyPressTimer = 14;
                currentMenuItemHovered--;
            } else {
                if (keyPressTimer > 0) {
                    keyPressTimer--;
                }
            }

            if (currentMenuItemHovered > 1) {
                currentMenuItemHovered = 0;
            } else if (currentMenuItemHovered < 0) {
                currentMenuItemHovered = 1;
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

            if (currentMenuItemHovered == 0) {
                retryOption.setColor(flashingColor);
                quitOption.setColor(Color.white);
            } else if (currentMenuItemHovered == 1) {
                retryOption.setColor(Color.white);
                quitOption.setColor(flashingColor);
            }

            if (Keyboard.isKeyUp(Key.SPACE)) {
                keyLocker.unlockKey(Key.SPACE);
            }

            if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
                menuItemSelected = currentMenuItemHovered;
                isFadingOut = true; // Start fade-out before transitioning
            }
        }

        // Handle fade-out
        if (isFadingOut) {
            fadeValue += 0.02f; // Gradually increase opacity
            if (fadeValue >= 1) {
                fadeValue = 1;
                if (menuItemSelected == 0) {
                    playOutsideLevelScreen.resetLevel();
                } else if (menuItemSelected == 1) {
                    playOutsideLevelScreen.goBackToMenu();
                }
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        // Draw the background
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        background.draw(graphicsHandler);

        // Draw the "You Died" message
        deathMessage.centerTextX(ScreenManager.getScreenWidth(), graphicsHandler.getGraphics());
        deathMessage.draw(graphicsHandler);

        // Draw the retry and quit options
        retryOption.centerTextX(ScreenManager.getScreenWidth() - 250, graphicsHandler.getGraphics());
        quitOption.centerTextX(ScreenManager.getScreenWidth() + 250, graphicsHandler.getGraphics());
        retryOption.draw(graphicsHandler);
        quitOption.draw(graphicsHandler);

        // Draw the fade effect
        if (fadeValue > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) (fadeValue * 255));
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
        }
    }
}
