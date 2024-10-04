package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected Sprite titleScreen = new Sprite(ImageLoader.load("titleScreen.png"), 0, 0);
    protected int keyPressTimer;
    protected KeyLocker keyLocker = new KeyLocker();

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // Initialize the SpriteFont objects
        playGame = new SpriteFont("START", 0, 475, "Times New Roman", 35, new Color(0, 0, 0));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        
        credits = new SpriteFont("CREDITS", 0, 475, "Times New Roman", 35, new Color(0, 0, 0));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        
        // Initialize the background map
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        // Update the background map (to play tile animations)
        background.update(null);

        // Handle menu navigation
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

        // Loop the selection back around
        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        // Update the colors based on the hovered item
        if (currentMenuItemHovered == 0) {
            playGame.setColor(Color.red);
            credits.setColor(Color.white);
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(Color.white);
            credits.setColor(Color.red);
        }

        // Check for selection confirmation
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        titleScreen.draw(graphicsHandler);

        // Set the x positions
        playGame.centerTextX(ScreenManager.getScreenWidth() - 250, graphicsHandler.getGraphics());
        credits.centerTextX(ScreenManager.getScreenWidth() + 250, graphicsHandler.getGraphics());

        // Draw the menu items
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
    }
}
