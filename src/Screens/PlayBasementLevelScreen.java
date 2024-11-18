package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import Engine.Sound;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.Basement;
import Players.Spider;
import Players.WalrusPlayer;

import java.awt.Color;

public class PlayBasementLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected BasementLevelClearedScreen basementLevelClearedScreen;
    protected BasementLevelLoseScreen basementLevelLoseScreen;
    protected boolean levelCompletedStateChangeStart;

    // Fade effect variables
    protected float fadeValue = 1;
    protected boolean isFadingIn = true;
    protected boolean isFadingOut = false;
    protected boolean isFadeComplete = false;

    public PlayBasementLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        Sound.stopMusic();
        this.map = new Basement();
        if (!screenCoordinator.getArachnophobiaEnabled()) {
            this.player = new Spider(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        } else {
            this.player = new WalrusPlayer(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }

        this.player.setMap(map);
        this.player.addListener(this);

        basementLevelClearedScreen = new BasementLevelClearedScreen(screenCoordinator);
        basementLevelLoseScreen = new BasementLevelLoseScreen(this);

        Sound.playMusic(Sound.Level.BASEMENT);

        this.playLevelScreenState = PlayLevelScreenState.RUNNING;

        // Initialize fade values
        fadeValue = 1;
        isFadingIn = true;
        isFadingOut = false;
        isFadeComplete = false;
    }

    public void update() {
        // Handle fade-in and fade-out logic
        if (isFadingIn) {
            fadeValue -= 0.02f;
            if (fadeValue <= 0) {
                fadeValue = 0;
                isFadingIn = false;
                isFadeComplete = true;
            }
        }

        if (isFadingOut) {
            fadeValue += 0.02f;
            if (fadeValue >= 1) {
                fadeValue = 1;
                isFadingOut = false;
                screenCoordinator.setGameState(GameState.OUTSIDE_LEVEL);
            }
        }

        if (!isFadeComplete) {
            return; // Skip further updates until fade-in is complete
        }

        switch (playLevelScreenState) {
            case RUNNING:
                player.update();
                map.update(player);
                break;
            case LEVEL_COMPLETED:
                if (levelCompletedStateChangeStart) {
                    screenTimer = 130;
                    levelCompletedStateChangeStart = false;
                } else {
                    basementLevelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        isFadingOut = true; // Trigger fade-out when transitioning
                    }
                }
                break;
            case LEVEL_LOSE:
                basementLevelLoseScreen.update();
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        switch (playLevelScreenState) {
            case RUNNING:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                basementLevelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                basementLevelLoseScreen.draw(graphicsHandler);
                break;
        }

        // Draw fade effect overlay
        if (fadeValue > 0) {
            Color fadeColor = new Color(0, 0, 0, (int) (fadeValue * 255));
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), fadeColor);
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    @Override
    public void onLevelCompleted() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_COMPLETED) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
            levelCompletedStateChangeStart = true;
        }
    }

    @Override
    public void onDeath() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_LOSE) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE;
        }
    }

    public void resetLevel() {
        initialize();
    }

    public void goToOutsideLevelScreen() {
        screenCoordinator.setGameState(GameState.OUTSIDE_LEVEL);
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    public ScreenCoordinator getScreenCoordinator() {
        return this.screenCoordinator;
    }

    public Player getPlayer() {
        return this.player;
    }

    public enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
