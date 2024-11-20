package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.Sound;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.LivingRoom;
import Players.Spider;
import Players.WalrusPlayer;

public class PlayLivingRoomLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected LivingRoomLevelClearedScreen livingRoomLevelClearedScreen;
    protected LivingRoomLevelLoseScreen livingRoomLevelLoseScreen;
    protected boolean levelCompletedStateChangeStart;

    public PlayLivingRoomLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        Sound.stopMusic();
        this.map = new LivingRoom();

        if(!screenCoordinator.getArachnophobiaEnabled()){
            this.player = new Spider(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        else{
            this.player = new WalrusPlayer(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        this.player.setMap(map);
        this.player.addListener(this);

        livingRoomLevelClearedScreen = new LivingRoomLevelClearedScreen();
        livingRoomLevelLoseScreen = new LivingRoomLevelLoseScreen(this);
        Sound.playMusic(Sound.Level.LIVING_ROOM);

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
                    livingRoomLevelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        goToKitchenLevelScreen();
                    }
                }
                break;
            case LEVEL_LOSE:
                livingRoomLevelLoseScreen.update();
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
                livingRoomLevelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                livingRoomLevelLoseScreen.draw(graphicsHandler);
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

    public void goToKitchenLevelScreen() {
        screenCoordinator.setGameState(GameState.KITCHEN_LEVEL);
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
