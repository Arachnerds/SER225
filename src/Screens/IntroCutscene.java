package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.Sprite;

import java.awt.*;

public class IntroCutscene extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected KeyLocker keyLocker = new KeyLocker();
    protected Sprite[] cutsceneFrames = new Sprite[16]; // Holds 16 frames
    protected int animationFrame = 0;
    protected int cutsceneTimer;
    protected int cutsceneDuration = 3700; // Total cutscene duration
    protected boolean musicStarted = false;
    protected boolean narrationStarted = false; // Flag to track when narration starts
    protected int blackScreenDuration = 330; // Black screen duration
    protected int musicDelayTimer = 0;
    protected final int musicDelayDuration = 30;

    // Define frame switch times for each transition
    protected final int[] frameSwitchTimes = {
        300, // Duration for transition from frame 0 to 1
        250, // Duration for transition from frame 1 to 2
        230, // Duration for transition from frame 2 to 3
        212, // Duration for transition from frame 3 to 4
        215, // Duration for transition from frame 4 to 5
        205, // Duration for transition from frame 5 to 6
        205, // Duration for transition from frame 6 to 7
        205, // Duration for transition from frame 7 to 8
        200, // Duration for transition from frame 8 to 9
        205, // Duration for transition from frame 9 to 10
        205, // Duration for transition from frame 10 to 11
        205, // Duration for transition from frame 11 to 12
        205, // Duration for transition from frame 12 to 13
        205, // Duration for transition from frame 13 to 14
        205, // Duration for transition from frame 14 to 15
        310  // Duration for transition from frame 15 to titleScreen (added duration)
    };

    public IntroCutscene(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        Sound.stopMusic();

        // Load 16 frames
        for (int i = 0; i < cutsceneFrames.length; i++) {
            if (i < 15) {
                cutsceneFrames[i] = new Sprite(ImageLoader.load("line" + (i + 1) + ".png"), 0, 0);
            } else {
                cutsceneFrames[i] = new Sprite(ImageLoader.load("titleScreen.png"), 0, 0); // Load the title screen frame
            }
        }

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
            Sound.playMusic(Sound.Level.WALTZ);
            Sound.setMusicVolume(-15.0f);
            musicStarted = true;
        }

        // Frame transitions based on timer
        if (cutsceneTimer >= blackScreenDuration) {
            // Determine the duration based on the current animation frame
            int switchTime = 0;

            if (animationFrame < frameSwitchTimes.length) {
                switchTime = frameSwitchTimes[animationFrame];
            }

            // Check if it's time to switch to the next frame
            if (cutsceneTimer >= blackScreenDuration + (animationFrame + 1) * switchTime) {
                if (animationFrame < cutsceneFrames.length - 1) {
                    animationFrame++;
                }

                // Start narration if it hasn't started yet and we're past the first frame
                if (animationFrame == 1 && !narrationStarted) {
                    Sound.playNarration(Sound.Narration.INTRO);
                    narrationStarted = true;
                }
            }
        }

        // Check if the cutscene duration is over
        if (cutsceneTimer >= cutsceneDuration) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        if (cutsceneTimer < blackScreenDuration) {
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        } else {
            // Display the frame based on animationFrame
            cutsceneFrames[animationFrame].draw(graphicsHandler);
        }
    }
}
