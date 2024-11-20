package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.Sound;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.Kitchen;
import Players.Spider;
import Players.WalrusPlayer;

public class PlayKitchenLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected KitchenLevelClearedScreen kitchenLevelClearedScreen;
    protected KitchenLevelLoseScreen kitchenLevelLoseScreen;
    protected boolean levelCompletedStateChangeStart;

    public PlayKitchenLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        Sound.stopMusic();
        this.map = new Kitchen();

        if(!screenCoordinator.getArachnophobiaEnabled()){
            this.player = new Spider(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        else{
            this.player = new WalrusPlayer(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        this.player.setMap(map);
        this.player.addListener(this);

        kitchenLevelClearedScreen = new KitchenLevelClearedScreen();
        kitchenLevelLoseScreen = new KitchenLevelLoseScreen(this);
        Sound.playMusic(Sound.Level.KITCHEN);

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
                    kitchenLevelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        goToBossLevel();
                    }
                }
                break;
            case LEVEL_LOSE:
                kitchenLevelLoseScreen.update();
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
                kitchenLevelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                kitchenLevelLoseScreen.draw(graphicsHandler);
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
            screenCoordinator.setGameState(GameState.FINAL);
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

    public void goToLivingRoomLevelScreen() {
        screenCoordinator.setGameState(GameState.LIVING_ROOM_LEVEL);
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    public void goToBossLevel() {
        screenCoordinator.setGameState(GameState.FINAL);
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
