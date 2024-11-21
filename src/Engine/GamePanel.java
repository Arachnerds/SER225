package Engine;

import Enemies.BossMainEnemy;
import Game.ScreenCoordinator;
import GameObject.Rectangle;
import GameObject.Sprite;
import Screens.PlayBasementLevelScreen;
import Screens.PlayBedroomLevelScreen;
import Screens.PlayFinalLevelScreen;
import Screens.PlayKitchenLevelScreen;
import Screens.PlayLivingRoomLevelScreen;
import Screens.PlayOutsideLevelScreen;
import SpriteFont.SpriteFont;
import Utils.Colors;
import java.awt.*;
import javax.swing.*;

public class GamePanel extends JPanel {
    private ScreenManager screenManager;
    private GraphicsHandler graphicsHandler;

    private boolean isGamePaused = false;
    private boolean isOnCooldown = false;
    private boolean hasKey = false;

    private SpriteFont pauseLabel;
    private SpriteFont controlsLabel;
    private SpriteFont quitLabel;

    private SpriteFont controlsTitleLabel;
    private SpriteFont quitConfirmationLabel;
    private SpriteFont yesLabel;
    private SpriteFont noLabel;
    private SpriteFont quitWarningLabel;

    private KeyLocker keyLocker = new KeyLocker();
    private final Key pauseKey = Key.P;
    private final Key pauseKeyAlt = Key.ESC;
    private Thread gameLoopProcess;
    private Key showFPSKey = Key.G;
    private SpriteFont fpsDisplayLabel;
    private boolean showFPS = false;
    private int currentFPS;
    private boolean doPaint;
    protected Sprite pauseScreen = new Sprite(ImageLoader.load("creditsScreenBlank.png"), 0, 0);
    protected Sprite doorKey = new Sprite(ImageLoader.load("DoorKey.png"));

    private int currentMenuItemHovered = 0;
    private int currentQuitOptionHovered = 1;
    private boolean isQuitConfirmationVisible = false;
    private boolean isControlsVisible = false;

    private int cooldownBarWidth = 160;
    private int cooldownBarWidthMax = 160;
    private int cooldownBarHeight = 13;
    private int cooldownBarXPosition = 20;
    private int cooldownBarYPosition = this.getHeight() - 100;

    protected Sprite playerHeart = new Sprite(ImageLoader.load("player_heart_web2.png"));
    protected Sprite playerHeartdead = new Sprite(ImageLoader.load("player_heart_dead.png"));
    private int playerHeartYPosition = this.getHeight() - 100;
    private int playerHeartXPosition = 50;
    private Sprite[] hearts = new Sprite[3];

    private int keyLocationX;
    private int keyLocationY;

    public GamePanel() {
        super();
        this.setDoubleBuffered(true);
        this.addKeyListener(Keyboard.getKeyListener());
        graphicsHandler = new GraphicsHandler();
        screenManager = new ScreenManager();
        pauseLabel = new SpriteFont("Paused", 0, 100, "Times New Roman", 45, Color.white);
        pauseLabel.setOutlineColor(Color.black);
        pauseLabel.setOutlineThickness(3);
        controlsLabel = new SpriteFont("Controls", 0, 250, "Times New Roman", 35, Color.white);
        controlsLabel.setOutlineColor(Color.black);
        controlsLabel.setOutlineThickness(3);
        quitLabel = new SpriteFont("Quit", 0, 300, "Times New Roman", 35, Color.white);
        quitLabel.setOutlineColor(Color.black);
        quitLabel.setOutlineThickness(3);
        quitConfirmationLabel = new SpriteFont("Are you sure you want to quit?", 0, 225, "Times New Roman", 35, Color.white);
        quitConfirmationLabel.setOutlineColor(Color.black);
        quitConfirmationLabel.setOutlineThickness(3);
        yesLabel = new SpriteFont("Yes", 0, 325, "Times New Roman", 35, Color.white);
        yesLabel.setOutlineColor(Color.black);
        yesLabel.setOutlineThickness(3);
        noLabel = new SpriteFont("No", 0, 325, "Times New Roman", 35, Color.white);
        noLabel.setOutlineColor(Color.black);
        noLabel.setOutlineThickness(3);
        quitWarningLabel = new SpriteFont("(Progress will not be saved)", 0, 275, "Times New Roman", 20, Color.white);
        quitWarningLabel.setOutlineColor(Color.black);
        quitWarningLabel.setOutlineThickness(2);
        fpsDisplayLabel = new SpriteFont("FPS", 4, 3, "Arial", 12, Color.black);
        currentFPS = Config.TARGET_FPS;
        GameLoop gameLoop = new GameLoop(this);
        gameLoopProcess = new Thread(gameLoop.getGameLoopProcess());


    }

    public void setupGame() {
        setBackground(Colors.CORNFLOWER_BLUE);
        screenManager.initialize(new Rectangle(getX(), getY(), getWidth(), getHeight()));
    }

    public void startGame() {
        gameLoopProcess.start();
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public void setCurrentFPS(int currentFPS) {
        this.currentFPS = currentFPS;
    }

    public void setDoPaint(boolean doPaint) {
        this.doPaint = doPaint;
    }

    public void update() {

        updatePauseState();
        updateShowFPSState();
        updateCooldownBar();
        //updatePlayerHealth();

        if (!isGamePaused) {
            screenManager.update();
        } else {
            updateMenuNavigation();
        }
    }

    private void updateCooldownBar() {
        if (isOnCooldown && cooldownBarWidth == cooldownBarWidthMax) {
            cooldownBarWidth = 0;
        }

        if (cooldownBarWidth >= cooldownBarWidthMax) {
            isOnCooldown = false;
        }
    }

    private void updatePauseState() {
        if ((Keyboard.isKeyDown(pauseKey) || Keyboard.isKeyDown(pauseKeyAlt)) && !keyLocker.isKeyLocked(pauseKey)) {
            isGamePaused = !isGamePaused;
            if (isGamePaused) {
                currentMenuItemHovered = 0;
                currentQuitOptionHovered = 1;
                isQuitConfirmationVisible = false;
                isControlsVisible = false;
            }
            keyLocker.lockKey(pauseKey);
        }
        if (Keyboard.isKeyUp(pauseKey) && Keyboard.isKeyUp(pauseKeyAlt)) {
            keyLocker.unlockKey(pauseKey);
        }
    }

    private void updateShowFPSState() {
        if (Keyboard.isKeyDown(showFPSKey) && !keyLocker.isKeyLocked(showFPSKey)) {
            showFPS = !showFPS;
            keyLocker.lockKey(showFPSKey);
        }
        if (Keyboard.isKeyUp(showFPSKey)) {
            keyLocker.unlockKey(showFPSKey);
        }
        fpsDisplayLabel.setText("FPS: " + currentFPS);
    }

    private void updateMenuNavigation() {
        if (!isQuitConfirmationVisible) {
            if ((Keyboard.isKeyDown(Key.DOWN) || Keyboard.isKeyDown(Key.S)) && currentMenuItemHovered < 1) {
                currentMenuItemHovered++;
            } else if ((Keyboard.isKeyDown(Key.UP) || Keyboard.isKeyDown(Key.W)) && currentMenuItemHovered > 0) {
                currentMenuItemHovered--;
            }
            controlsLabel.setColor(currentMenuItemHovered == 0 ? Color.red : Color.white);
            quitLabel.setColor(currentMenuItemHovered == 1 ? Color.red : Color.white);
            if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
                if (currentMenuItemHovered == 0) {
                    isControlsVisible = !isControlsVisible;
                } else if (currentMenuItemHovered == 1) {
                    isQuitConfirmationVisible = true;
                }
                keyLocker.lockKey(Key.SPACE);
            }
        } else {
            if ((Keyboard.isKeyDown(Key.RIGHT) || Keyboard.isKeyDown(Key.D)) && currentQuitOptionHovered < 1) {
                currentQuitOptionHovered++;
            } else if ((Keyboard.isKeyDown(Key.LEFT) || Keyboard.isKeyDown(Key.A)) && currentQuitOptionHovered > 0) {
                currentQuitOptionHovered--;
            }
            yesLabel.setColor(currentQuitOptionHovered == 0 ? Color.red : Color.white);
            noLabel.setColor(currentQuitOptionHovered == 1 ? Color.red : Color.white);
            if (Keyboard.isKeyDown(Key.SPACE) && !keyLocker.isKeyLocked(Key.SPACE)) {
                if (currentQuitOptionHovered == 0) {
                    System.exit(0);
                } else if (currentQuitOptionHovered == 1) {
                    isQuitConfirmationVisible = false;
                }
                keyLocker.lockKey(Key.SPACE);
            }
        }
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        //This makes sure you can't scroll off of the "back" button when you are on the controls screen
        if(isControlsVisible){ 
            currentMenuItemHovered = 0;
        }
    }

    public void draw() {
        screenManager.draw(graphicsHandler);
        Graphics2D g2d = graphicsHandler.getGraphics();
        Screen currentScreenState = screenManager.getCurrentScreen();
        pauseLabel.centerTextX(getWidth(), g2d);
        controlsLabel.centerTextX(getWidth(), g2d);
        quitLabel.centerTextX(getWidth(), g2d); 
        quitConfirmationLabel.centerTextX(getWidth(), g2d);
        yesLabel.centerTextX(getWidth(), g2d);
        noLabel.centerTextX(getWidth(), g2d);
        quitWarningLabel.centerTextX(getWidth(), g2d);

        PlayLevelCurrentScreenState currentPlayState = PlayLevelCurrentScreenState.OTHER;

        if (currentScreenState instanceof ScreenCoordinator) {
            ScreenCoordinator screenCoordinator = (ScreenCoordinator) currentScreenState;
            Screen currentScreen = screenCoordinator.getCurrentScreen();

            if (currentScreen instanceof PlayBasementLevelScreen) {
                PlayBasementLevelScreen playScreen = (PlayBasementLevelScreen) currentScreen;
                
                if (playScreen.getPlayLevelScreenState() == PlayBasementLevelScreen.PlayLevelScreenState.RUNNING) {
                    currentPlayState = PlayLevelCurrentScreenState.RUNNING;
                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }

                    int health = playScreen.getPlayer().getPlayerLives(); //retrieve player health

                    for(int i = 0; i < health; i++){ //how many hearts left
                        this.keyLocationX = this.getWidth() - ((50 * (3-i)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeart.setLocation(keyLocationX, keyLocationY);
                        this.playerHeart.setScale(1.5f);
                        playerHeart.draw(graphicsHandler);
                    }

                    //how many hearts taken -> replace with black hearts
                    for (int b = health; b<3;b++){
                        this.keyLocationX = this.getWidth() - ((50 * (3-b)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeartdead.setLocation(keyLocationX, keyLocationY);
                        this.playerHeartdead.setScale(1.5f);
                        playerHeartdead.draw(graphicsHandler);
                    }

                    this.hasKey = playScreen.getPlayer().hasKey();
                }

            } else if (currentScreen instanceof PlayOutsideLevelScreen) {
                PlayOutsideLevelScreen playScreen = (PlayOutsideLevelScreen) currentScreen;
                
                if (playScreen.getPlayLevelScreenState() == PlayOutsideLevelScreen.PlayLevelScreenState.RUNNING) {
                    currentPlayState = PlayLevelCurrentScreenState.RUNNING;
                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }


                    int health = playScreen.getPlayer().getPlayerLives(); //retrieve player health

                    for(int i = 0; i < health; i++){ //how many hearts left
                        this.keyLocationX = this.getWidth() - ((50 * (3-i)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeart.setLocation(keyLocationX, keyLocationY);
                        this.playerHeart.setScale(1.5f);
                        playerHeart.draw(graphicsHandler);
                    }

                    //how many hearts taken -> replace with black hearts
                    for (int b = health; b<3;b++){
                        this.keyLocationX = this.getWidth() - ((50 * (3-b)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeartdead.setLocation(keyLocationX, keyLocationY);
                        this.playerHeartdead.setScale(1.5f);
                        playerHeartdead.draw(graphicsHandler);
                    }

                    this.hasKey = playScreen.getPlayer().hasKey();
                }
            } else if (currentScreen instanceof PlayLivingRoomLevelScreen) {
                PlayLivingRoomLevelScreen playScreen = (PlayLivingRoomLevelScreen) currentScreen;
                
                if (playScreen.getPlayLevelScreenState() == PlayLivingRoomLevelScreen.PlayLevelScreenState.RUNNING) {
                    currentPlayState = PlayLevelCurrentScreenState.RUNNING;
                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }


                    int health = playScreen.getPlayer().getPlayerLives(); //retrieve player health

                    for(int i = 0; i < health; i++){ //how many hearts left
                        this.keyLocationX = this.getWidth() - ((50 * (3-i)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeart.setLocation(keyLocationX, keyLocationY);
                        this.playerHeart.setScale(1.5f);
                        playerHeart.draw(graphicsHandler);
                    }

                    //how many hearts taken -> replace with black hearts
                    for (int b = health; b<3;b++){
                        this.keyLocationX = this.getWidth() - ((50 * (3-b)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeartdead.setLocation(keyLocationX, keyLocationY);
                        this.playerHeartdead.setScale(1.5f);
                        playerHeartdead.draw(graphicsHandler);
                    }

                    this.hasKey = playScreen.getPlayer().hasKey();
                }
            } else if (currentScreen instanceof PlayBedroomLevelScreen) {
                PlayBedroomLevelScreen playScreen = (PlayBedroomLevelScreen) currentScreen;
                
                if (playScreen.getPlayLevelScreenState() == PlayBedroomLevelScreen.PlayLevelScreenState.RUNNING) {
                    currentPlayState = PlayLevelCurrentScreenState.RUNNING;
                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }


                    int health = playScreen.getPlayer().getPlayerLives(); //retrieve player health

                    for(int i = 0; i < health; i++){ //how many hearts left
                        this.keyLocationX = this.getWidth() - ((50 * (3-i)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeart.setLocation(keyLocationX, keyLocationY);
                        this.playerHeart.setScale(1.5f);
                        playerHeart.draw(graphicsHandler);
                    }

                    //how many hearts taken -> replace with black hearts
                    for (int b = health; b<3;b++){
                        this.keyLocationX = this.getWidth() - ((50 * (3-b)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeartdead.setLocation(keyLocationX, keyLocationY);
                        this.playerHeartdead.setScale(1.5f);
                        playerHeartdead.draw(graphicsHandler);
                    }

                    this.hasKey = playScreen.getPlayer().hasKey();
                }
            } else if (currentScreen instanceof PlayKitchenLevelScreen) {
                PlayKitchenLevelScreen playScreen = (PlayKitchenLevelScreen) currentScreen;
                
                if (playScreen.getPlayLevelScreenState() == PlayKitchenLevelScreen.PlayLevelScreenState.RUNNING) {
                    currentPlayState = PlayLevelCurrentScreenState.RUNNING;
                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }

                    int health = playScreen.getPlayer().getPlayerLives(); //retrieve player health

                    for(int i = 0; i < health; i++){ //how many hearts left
                        this.keyLocationX = this.getWidth() - ((50 * (3-i)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeart.setLocation(keyLocationX, keyLocationY);
                        this.playerHeart.setScale(1.5f);
                        playerHeart.draw(graphicsHandler);
                    }

                    //how many hearts taken -> replace with black hearts
                    for (int b = health; b<3;b++){
                        this.keyLocationX = this.getWidth() - ((50 * (3-b)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeartdead.setLocation(keyLocationX, keyLocationY);
                        this.playerHeartdead.setScale(1.5f);
                        playerHeartdead.draw(graphicsHandler);
                    }

                    this.hasKey = playScreen.getPlayer().hasKey();
                } 
            } else if (currentScreen instanceof PlayFinalLevelScreen) {
                PlayFinalLevelScreen playScreen = (PlayFinalLevelScreen) currentScreen;
                if (playScreen.getPlayLevelScreenState() == PlayFinalLevelScreen.PlayLevelScreenState.RUNNING) {
                    currentPlayState = PlayLevelCurrentScreenState.RUNNING;
                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }

                    BossMainEnemy boss = playScreen.getBoss();
                    if (!boss.isDead()) {
                        int currentHealth = boss.getCurrentHealth();
                        int maxHealth = boss.getMaxHealth();

                        int fixedWidth = 500;

                        double healthPercentage = (double) currentHealth / maxHealth;
                        int currentHealthBarWidth = (int) (fixedWidth * healthPercentage);

                        // Set the position for the health bar
                        int healthBarXPosition = this.getWidth() / 2 - fixedWidth / 2;
                        int healthBarYPosition = 30;
                        
                        graphicsHandler.drawFilledRectangle(healthBarXPosition, healthBarYPosition - 3, fixedWidth, 14, java.awt.Color.BLACK);
                        graphicsHandler.drawFilledRectangle(healthBarXPosition + 3, healthBarYPosition, currentHealthBarWidth - 6, 8, java.awt.Color.RED);
                    }

                    if (playScreen.getPlayer().hasShotWeb()) {
                        cooldownBarWidth = playScreen.getPlayer().getShootCooldownFrames()*2;
                        isOnCooldown = true;
                    } else {
                        cooldownBarWidth = cooldownBarWidthMax;
                        isOnCooldown = false;
                    }


                    int health = playScreen.getPlayer().getPlayerLives(); //retrieve player health

                    for(int i = 0; i < health; i++){ //how many hearts left
                        this.keyLocationX = this.getWidth() - ((50 * (3-i)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeart.setLocation(keyLocationX, keyLocationY);
                        this.playerHeart.setScale(1.5f);
                        playerHeart.draw(graphicsHandler);
                    }

                    //how many hearts taken -> replace with black hearts
                    for (int b = health; b<3;b++){
                        this.keyLocationX = this.getWidth() - ((50 * (3-b)) + 10) ;
                        this.keyLocationY = this.getHeight() - 40;
                        this.playerHeartdead.setLocation(keyLocationX, keyLocationY);
                        this.playerHeartdead.setScale(1.5f);
                        playerHeartdead.draw(graphicsHandler);
                    }

                    this.hasKey = playScreen.getPlayer().hasKey();
                }
            }
        }

        if (currentPlayState == PlayLevelCurrentScreenState.RUNNING) {
            cooldownBarYPosition = getHeight() - cooldownBarHeight - 15;

            graphicsHandler.drawFilledRectangle(cooldownBarXPosition - 4, cooldownBarYPosition - 4, cooldownBarWidthMax + 8, cooldownBarHeight + 8, java.awt.Color.BLACK);
            
            if (!isOnCooldown) {
                graphicsHandler.drawFilledRectangle(cooldownBarXPosition, cooldownBarYPosition, cooldownBarWidth, cooldownBarHeight, java.awt.Color.WHITE);
                isOnCooldown = false;
            } else {
                graphicsHandler.drawFilledRectangle(cooldownBarXPosition, cooldownBarYPosition, cooldownBarWidth, cooldownBarHeight, java.awt.Color.RED);
            }
        }

        if (currentPlayState == PlayLevelCurrentScreenState.RUNNING && hasKey) {
            this.keyLocationX = this.getWidth() - 40;
            this.keyLocationY = this.getHeight() - 560;
            this.doorKey.setLocation(keyLocationX, keyLocationY);
            this.doorKey.setScale(2f);
            doorKey.draw(graphicsHandler);
        }
        
        if (isGamePaused) {
            pauseScreen.draw(graphicsHandler);
            if (!isQuitConfirmationVisible && !isControlsVisible) {
                pauseLabel.draw(graphicsHandler);
                controlsLabel.draw(graphicsHandler);
                quitLabel.draw(graphicsHandler);
            } else if (isControlsVisible) {
                SpriteFont backLabel = new SpriteFont("Back", 25, 25, "Times New Roman", 30, Color.red);
                backLabel.setOutlineColor(Color.black);
                backLabel.setOutlineThickness(2);
                backLabel.draw(graphicsHandler);
                controlsTitleLabel = new SpriteFont("Controls", 0, 100, "Times New Roman", 45, Color.white);
                controlsTitleLabel.setOutlineColor(Color.black);
                controlsTitleLabel.setOutlineThickness(3);
                controlsTitleLabel.centerTextX(getWidth(), g2d);
                controlsTitleLabel.draw(graphicsHandler);
                String[] controlInstructions = {
                    "A/Left Arrow - Move Left",
                    "D/Right Arrow - Move Right",
                    "S/Down Arrow - Crouch",
                    "W/Up Arrow - Climb",
                    "Space - Jump",
                    "C - Activate Climbing Mode",
                    "Q - Shoot Projectile",
                    "E - Interact With Jumping/Swinging Points",
                    "Y - Pull a draggable object",
                    "T - Talk to NPCs"
                };
                for (int i = 0; i < controlInstructions.length; i++) {
                    SpriteFont instructionsLabel = new SpriteFont(controlInstructions[i], 0, 160 + (i * 40), "Times New Roman", 30, Color.white);
                    instructionsLabel.setOutlineColor(Color.black);
                    instructionsLabel.setOutlineThickness(2);
                    instructionsLabel.centerTextX(getWidth(), g2d);
                    instructionsLabel.draw(graphicsHandler);
                }
            } else {
                quitConfirmationLabel.draw(graphicsHandler);
                quitWarningLabel.draw(graphicsHandler);
                yesLabel.centerTextX(ScreenManager.getScreenWidth() - 100, graphicsHandler.getGraphics());
                noLabel.centerTextX(ScreenManager.getScreenWidth() + 100, graphicsHandler.getGraphics());
                yesLabel.draw(graphicsHandler);
                noLabel.draw(graphicsHandler);
            }
        }
        if (showFPS) {
            fpsDisplayLabel.draw(graphicsHandler);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (doPaint) {
            graphicsHandler.setGraphics((Graphics2D) g);
            draw();
        }
    }

    private enum PlayLevelCurrentScreenState {
        RUNNING, OTHER
    }
}
