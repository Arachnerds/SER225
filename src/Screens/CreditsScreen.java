package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the credits screen
public class CreditsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected Sprite titleScreen = new Sprite(ImageLoader.load("creditsScreen.png"), -10, 0);
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont brooksLabel;
    protected SpriteFont callandraLabel;
    protected SpriteFont ericLabel;
    protected SpriteFont ryanLabel;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        creditsLabel = new SpriteFont("Arachnerds Inc.", 255, 150, "Times New Roman", 45, Color.white);
        creditsLabel.setOutlineColor(Color.black);
        creditsLabel.setOutlineThickness(3);
        brooksLabel = new SpriteFont("Brooks Jackson - Full Stack", 200, 250, "Times New Roman", 35, Color.white);
        brooksLabel.setOutlineColor(Color.black);
        brooksLabel.setOutlineThickness(3);
        callandraLabel = new SpriteFont("Callandra Ruiter - Full Stack", 193, 300, "Times New Roman", 35, Color.white);
        callandraLabel.setOutlineColor(Color.black);
        callandraLabel.setOutlineThickness(3);
        ericLabel = new SpriteFont("Eric May - Full Stack", 245, 350, "Times New Roman", 35, Color.white);
        ericLabel.setOutlineColor(Color.black);
        ericLabel.setOutlineThickness(3);
        ryanLabel = new SpriteFont("Ryan Slattery - Full Stack", 215, 400, "Times New Roman", 35, Color.white);
        ryanLabel.setOutlineColor(Color.black);
        ryanLabel.setOutlineThickness(3);
        keyLocker.lockKey(Key.ESC);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        titleScreen.draw(graphicsHandler);
        creditsLabel.draw(graphicsHandler);
        brooksLabel.draw(graphicsHandler);
        callandraLabel.draw(graphicsHandler);
        ericLabel.draw(graphicsHandler);
        ryanLabel.draw(graphicsHandler);
    }
}
