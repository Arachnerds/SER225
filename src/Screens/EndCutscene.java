package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;

import java.awt.*;

public class EndCutscene extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected KeyLocker keyLocker = new KeyLocker();
    protected Sprite[] cutsceneFrames = new Sprite[4];
    protected int animationFrame = 0;
    protected int cutsceneTimer;
    protected final int frameDuration = 300;
    protected int blackScreenDuration = 150;
    protected int cutsceneDuration = 4 * frameDuration + blackScreenDuration;
    protected boolean musicStarted = false;

    public EndCutscene(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        Sound.stopMusic();
        for (int i = 0; i < cutsceneFrames.length; i++) {
            cutsceneFrames[i] = new Sprite(ImageLoader.load("EndCutscene" + (i + 1) + ".png"), 0, 0);
        }
        cutsceneTimer = 0;
    }

    @Override
    public void update() {
        cutsceneTimer++;
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
        if (cutsceneTimer >= blackScreenDuration - 30 && !musicStarted) {
            Sound.playMusic(Sound.Level.END);
            musicStarted = true;
        }
        if (cutsceneTimer >= blackScreenDuration) {
            animationFrame = (cutsceneTimer - blackScreenDuration) / frameDuration;
            if (animationFrame >= cutsceneFrames.length) {
                animationFrame = cutsceneFrames.length - 1;
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
            cutsceneFrames[animationFrame].draw(graphicsHandler);
        }
    }
}
