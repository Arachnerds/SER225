package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0;
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected Sprite titleScreen = new Sprite(ImageLoader.load("titleScreen.png"), 0, 0);
    protected int keyPressTimer;
    protected KeyLocker keyLocker = new KeyLocker();
    protected float colorTransition = 0;
    protected boolean increasing = true;
    protected int alpha = 0;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        Sound.stopAmbience();
        Sound.stopNarration();

        playGame = new SpriteFont("START", 0, 475, "Times New Roman", 35, new Color(255, 255, 255, alpha));
        playGame.setOutlineColor(new Color(0, 0, 0, alpha));
        playGame.setOutlineThickness(3);

        credits = new SpriteFont("CREDITS", 0, 475, "Times New Roman", 35, new Color(255, 255, 255, alpha));
        credits.setOutlineColor(new Color(0, 0, 0, alpha));
        credits.setOutlineThickness(3);

        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        Sound.playMusic(Sound.Level.TITLE);

        keyPressTimer = 0;
        menuItemSelected = -1;
        colorTransition = 0;
        increasing = true;
        alpha = 0;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        background.update(null);

        if (alpha < 255) {
            alpha += 5;
            if (alpha > 255) {
                alpha = 255;
            }
        }

        Color flashingColor = new Color(
            255,
            (int) (255 * (1 - colorTransition)),
            (int) (255 * (1 - colorTransition)),
            alpha
        );

        playGame.setColor(flashingColor);
        playGame.setOutlineColor(new Color(0, 0, 0, alpha));

        credits.setColor(new Color(255, 255, 255, alpha));
        credits.setOutlineColor(new Color(0, 0, 0, alpha));

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

        if (currentMenuItemHovered == 0) {
            playGame.setColor(flashingColor);
            credits.setColor(new Color(255, 255, 255, alpha));
        } else if (currentMenuItemHovered == 1) {
            credits.setColor(flashingColor);
            playGame.setColor(new Color(255, 255, 255, alpha));
        }

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.BASEMENT_LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.FINAL);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        titleScreen.draw(graphicsHandler);

        playGame.centerTextX(ScreenManager.getScreenWidth() - 250, graphicsHandler.getGraphics());
        credits.centerTextX(ScreenManager.getScreenWidth() + 250, graphicsHandler.getGraphics());

        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
    }
}
