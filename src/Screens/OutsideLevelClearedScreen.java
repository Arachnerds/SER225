package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the level cleared screen for outside levels
public class OutsideLevelClearedScreen extends Screen {
    protected SpriteFont winMessage;

    public OutsideLevelClearedScreen() {
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Level Complete!", 0, 0, "Times New Roman", 35, Color.white);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.centerTextX(ScreenManager.getScreenWidth(), graphicsHandler.getGraphics());
        winMessage.centerTextY(ScreenManager.getScreenHeight(), graphicsHandler.getGraphics());
        winMessage.draw(graphicsHandler);
    }
}
