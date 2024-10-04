package Screens;

import Engine.*;
import GameObject.Sprite;
import SpriteFont.SpriteFont;

import java.awt.*;

public class LevelLoseScreen extends Screen {
    protected PlayLevelScreen playLevelScreen;
    protected int currentMenuItemHovered = 0; // Current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont retryOption;
    protected SpriteFont quitOption;
    protected SpriteFont deathMessage; // New SpriteFont for "You Died"
    protected Sprite background = new Sprite(ImageLoader.load("creditsScreenBlank.png"), 0, 0);
    protected int keyPressTimer;
    protected KeyLocker keyLocker = new KeyLocker();

    public LevelLoseScreen(PlayLevelScreen playLevelScreen) {
        this.playLevelScreen = playLevelScreen;
        initialize();
    }

    @Override
    public void initialize() {
        // Set the x positions to align with the MenuScreen
        retryOption = new SpriteFont("RETRY", 0, 325, "Times New Roman", 35, Color.white);
        retryOption.setOutlineColor(Color.black);
        retryOption.setOutlineThickness(3);

        quitOption = new SpriteFont("QUIT", 0, 325, "Times New Roman", 35, Color.white);
        quitOption.setOutlineColor(Color.black);
        quitOption.setOutlineThickness(3);

        // Initialize the "You Died" message
        deathMessage = new SpriteFont("You Died", 0, 200, "Times New Roman", 50, Color.white);
        deathMessage.setOutlineColor(Color.black);
        deathMessage.setOutlineThickness(3);

        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    @Override
    public void update() {
        // Navigate through the menu
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

        // Loop the selection back around when reaching top or bottom
        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        // Update the menu item colors based on hover
        if (currentMenuItemHovered == 0) {
            retryOption.setColor(Color.red);
            quitOption.setColor(Color.white);
        } else if (currentMenuItemHovered == 1) {
            retryOption.setColor(Color.white);
            quitOption.setColor(Color.red);
        }

        // Unlock space key and confirm selection
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // Execute action based on the selected option
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                playLevelScreen.resetLevel(); // Retry the level
            } else if (menuItemSelected == 1) {
                playLevelScreen.goBackToMenu(); // Go back to the main menu
            }
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        background.draw(graphicsHandler);
        
        // Set the x positions to align with the MenuScreen
        retryOption.centerTextX(ScreenManager.getScreenWidth() - 250, graphicsHandler.getGraphics());
        quitOption.centerTextX(ScreenManager.getScreenWidth() + 250, graphicsHandler.getGraphics());

        // Center the death message on the screen
        deathMessage.centerTextX(ScreenManager.getScreenWidth(), graphicsHandler.getGraphics());
        deathMessage.draw(graphicsHandler); // Draw the "You Died" message

        retryOption.draw(graphicsHandler);
        quitOption.draw(graphicsHandler);
    }
}
