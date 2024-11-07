package Screens;

import Engine.*;
import GameObject.Sprite;
import SpriteFont.SpriteFont;

import java.awt.*;

public class LivingRoomLevelLoseScreen extends Screen {
    protected PlayLivingRoomLevelScreen playLivingRoomLevelScreen;
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

    public LivingRoomLevelLoseScreen(PlayLivingRoomLevelScreen playLevelScreen) {
        this.playLivingRoomLevelScreen = playLevelScreen;
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

        deathMessage = new SpriteFont("You Died", 0, 200, "Times New Roman", 50, Color.white);
        deathMessage.setOutlineColor(Color.black);
        deathMessage.setOutlineThickness(3);

        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
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
            if (menuItemSelected == 0) {
                playLivingRoomLevelScreen.resetLevel();
            } else if (menuItemSelected == 1) {
                playLivingRoomLevelScreen.goBackToMenu();
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        background.draw(graphicsHandler);

        retryOption.centerTextX(ScreenManager.getScreenWidth() - 250, graphicsHandler.getGraphics());
        quitOption.centerTextX(ScreenManager.getScreenWidth() + 250, graphicsHandler.getGraphics());

        deathMessage.centerTextX(ScreenManager.getScreenWidth(), graphicsHandler.getGraphics());
        deathMessage.draw(graphicsHandler);

        retryOption.draw(graphicsHandler);
        quitOption.draw(graphicsHandler);
    }
}
