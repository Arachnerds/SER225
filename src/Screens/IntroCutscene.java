package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;

import java.awt.*;

public class IntroCutscene extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected KeyLocker keyLocker = new KeyLocker();
    protected Sprite[] cutsceneFrames = new Sprite[2];
    protected int animationFrame = 0;
    protected int animationSpeed = 20;
    protected int animationTimer = 0;
    protected int cutsceneTimer;
    protected int cutsceneDuration = 3780;
    protected boolean musicStarted = false;
    protected int blackScreenDuration = 330;
    protected int musicDelayTimer = 0;
    protected final int musicDelayDuration = 30;
    protected final int frameSwitchTime = 3480;

    public IntroCutscene(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        Sound.stopMusic();
        cutsceneFrames[0] = new Sprite(ImageLoader.load("creditsScreen.png"), 0, 0);
        cutsceneFrames[1] = new Sprite(ImageLoader.load("titleScreen.png"), 0, 0);

        cutsceneTimer = 0;
        
        musicDelayTimer = 0;
    }

    @Override
    public void update() {
        cutsceneTimer++;

        musicDelayTimer++;

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        if (Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }

        if (musicDelayTimer >= musicDelayDuration && !musicStarted) {
            Sound.playOnce(Sound.Level.WALTZ);
            musicStarted = true;
        }

        // Only animate the frames after switching to the second frame.
        if (cutsceneTimer >= frameSwitchTime) {
            animationTimer++;
            if (animationTimer >= animationSpeed) {
                animationFrame = 1; // Stay on the second frame
                animationTimer = 0;
            }
        }

        if (cutsceneTimer >= cutsceneDuration) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (cutsceneTimer < blackScreenDuration) {
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        } else {
            // Display the first frame until the cutscene timer reaches the switch time
            if (cutsceneTimer < frameSwitchTime) {
                cutsceneFrames[0].draw(graphicsHandler);
            } else {
                cutsceneFrames[1].draw(graphicsHandler);
            }
        }
    }
}
