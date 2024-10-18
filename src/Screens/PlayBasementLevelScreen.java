package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.Sound;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.Basement;
import Players.Spider;

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
    }

    public void initialize() {
        Sound.stopMusic();
        this.map = new Basement();

        this.player = new Spider(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        this.player.setMap(map);
        this.player.addListener(this);

        basementLevelClearedScreen = new BasementLevelClearedScreen();
        basementLevelLoseScreen = new BasementLevelLoseScreen(this);

        this.playLevelScreenState = PlayLevelScreenState.RUNNING;
    }

    public void update() {
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
                        goToOutsideLevelScreen();
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

    public Spider getPlayer() {
        return (Spider) this.player;
    }

    public enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
