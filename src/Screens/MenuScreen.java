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
    protected Sprite titleScreen = new Sprite(ImageLoader.load("titleScreen.png"), -10, 0);
    protected int keyPressTimer;
    protected KeyLocker keyLocker = new KeyLocker();

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        playGame = new SpriteFont("START", 200, 450, "Times New Roman", 35, new Color(0, 0, 0));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        credits = new SpriteFont("CREDITS", 460, 450, "Times New Roman", 35, new Color(0, 0, 0));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        // update background map (to play tile animations)
        background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if ((Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) &&  keyPressTimer == 0 && currentMenuItemHovered == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered++;
        } else if ((Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) &&  keyPressTimer == 0 && currentMenuItemHovered == 1) {
            keyPressTimer = 14;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 1) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 1;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
            playGame.setColor(Color.red);
            credits.setColor(Color.white);
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(Color.white);
            credits.setColor(Color.red);
        }

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
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
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
    }
}
