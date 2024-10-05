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
    protected Sprite titleScreen = new Sprite(ImageLoader.load("creditsScreen.png"), 0, 0);
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont creditsLabel;
    protected SpriteFont brooksLabel;
    protected SpriteFont callandraLabel;
    protected SpriteFont ericLabel;
    protected SpriteFont ryanLabel;
    protected SpriteFont backLabel;
    protected boolean backSelected = true;

    public CreditsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // Setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);

        // Initialize SpriteFont objects with default x position
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

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE) && backSelected) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        titleScreen.draw(graphicsHandler);
        
        // Center the labels horizontally
        int centerX = ScreenManager.getScreenWidth();

        creditsLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        brooksLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        callandraLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        ericLabel.centerTextX(centerX, graphicsHandler.getGraphics());
        ryanLabel.centerTextX(centerX, graphicsHandler.getGraphics());

        // Change the color of the back label based on selection
        if (backSelected) {
            backLabel.setColor(Color.red);
        } else {
            backLabel.setColor(Color.white);
        }

        // Draw the labels
        creditsLabel.draw(graphicsHandler);
        brooksLabel.draw(graphicsHandler);
        callandraLabel.draw(graphicsHandler);
        ericLabel.draw(graphicsHandler);
        ryanLabel.draw(graphicsHandler);
        backLabel.draw(graphicsHandler);
    }
}
