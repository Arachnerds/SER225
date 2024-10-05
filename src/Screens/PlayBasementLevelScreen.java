package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.Basement;
import Players.Spider;

// This class is for when the platformer game is actually being played
public class PlayBasementLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected BasementLevelClearedScreen basementLevelClearedScreen;
    protected BasementLevelLoseScreen basementLevelLoseScreen;
    protected boolean levelCompletedStateChangeStart;

    public PlayBasementLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
        initialize(); // Call initialize in constructor for setup
    }

    public void initialize() {
        // Define/setup map
        this.map = new Basement();

        // Setup player
        this.player = new Spider(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.player.setMap(map);
        this.player.addListener(this);

        basementLevelClearedScreen = new BasementLevelClearedScreen();
        basementLevelLoseScreen = new BasementLevelLoseScreen(this);

        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
    }

    public void update() {
        // Based on screen state, perform specific actions
        switch (playLevelScreenState) {
            // If level is "running," update player and map to keep game logic for the platformer level going
            case RUNNING:
                player.update();
                map.update(player);
                break;
            // If level has been completed, bring up level cleared screen
            case LEVEL_COMPLETED:
                if (levelCompletedStateChangeStart) {
                    screenTimer = 130;
                    levelCompletedStateChangeStart = false;
                } else {
                    basementLevelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        goToOutsideLevelScreen(); // Transition to PlayOutsideLevelScreen
                    }
                }
                break;
            // Wait on level lose screen to make a decision (reset level or send player back to main menu)
            case LEVEL_LOSE:
                basementLevelLoseScreen.update();
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // Based on screen state, draw appropriate graphics
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
        initialize(); // Re-initialize the level
    }

    public void goToOutsideLevelScreen() {
        // Transition to PlayOutsideLevelScreen
        screenCoordinator.setGameState(GameState.OUTSIDE_LEVEL);
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
