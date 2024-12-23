package Screens;

import Enemies.BossMainEnemy;
import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.Sound;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.Final;
import Players.Spider;
import Players.WalrusPlayer;

public class PlayFinalLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected FinalLevelLoseScreen finalLevelClearedScreen;
    protected FinalLevelLoseScreen finalLevelLoseScreen;
    protected boolean levelCompletedStateChangeStart;
    private BossMainEnemy boss;

    public PlayFinalLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        Sound.stopMusic();
        this.map = new Final();
        if (!screenCoordinator.getArachnophobiaEnabled()){
            this.player = new Spider(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        else {
            this.player = new WalrusPlayer(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        }
        this.player.setMap(map);
        this.player.addListener(this);

        this.boss = ((Final) map).getBoss();

        finalLevelLoseScreen = new FinalLevelLoseScreen(this);
        
        Sound.playMusic(Sound.Level.BOSS);

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
                    finalLevelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        goBackToMenu();
                    }
                }
                break;
            case LEVEL_LOSE:
                finalLevelLoseScreen.update();
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
                goToEnd();
                break;
            case LEVEL_LOSE:
                finalLevelLoseScreen.draw(graphicsHandler);
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

    public void goToEnd() {
        screenCoordinator.setGameState(GameState.END_CUTSCENE);
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

    public BossMainEnemy getBoss() {
        return boss;
    }

    public enum PlayLevelScreenState {
        RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
