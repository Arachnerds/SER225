package Enemies;

import Level.Map;
import Level.MapEntityStatus;
import Level.Player;
import Utils.Direction;
import Utils.Point;


import java.util.ArrayList;
import java.util.Random;

import Enemies.BossHandEnemy.HandState;

public class BossMainEnemy  {

    private Map map;
    private BossHandEnemy leftHand;
    private BossHandEnemy rightHand;
    private ArrayList<String> attackTypes;
    private int health;
    private boolean isAlive = true;

    private int idleCooldownFrames = 180;
    private int idleCooldownCounter = 0;
    private int maxHealth;

    public BossMainEnemy(Map map, int health) {
        this.map = map;
        this.health = health;
        this.maxHealth = health;
        attackTypes = new ArrayList<>();
        this.initializeAttackTypes();
        // this.initialize();
    }

    /*
    public void initialize() {
        spawnNewHands(); //spawn hands for the boss enemy
    } */

    // Just adding the attacks to the attack list
    // Slam is added multiple times to increase probability during random selection
    private void initializeAttackTypes() {
        attackTypes.add("SLAM");
        attackTypes.add("SWEEP_RIGHT");
        attackTypes.add("SLAM");
        attackTypes.add("SWEEP_LEFT");
        attackTypes.add("CLAP");
    }

    public void respawnHands(){ 

        if (!isAlive) return;

        Point lhandArea = new Point(map.getMapTile(3, 11).getX()+5, map.getMapTile(3, 10).getY()+60);
        Point rhandArea = new Point(map.getMapTile(14, 11).getX()-20, map.getMapTile(14, 10).getY()+60); // DETERMINE THESE LATER BASED ON MAP TILES LOCATION

        if(leftHand.handState == HandState.DEAD){
            BossHandEnemy lHand = new BossHandEnemy(lhandArea, Direction.RIGHT, this, map);
            map.addEnemy(lHand);
            leftHand = lHand;
        }
        if(rightHand.handState == HandState.DEAD){
            BossHandEnemy rHand = new BossHandEnemy(rhandArea, Direction.LEFT, this, map);
            map.addEnemy(rHand);
            rightHand = rHand;
        }
    }

    // This will choose a random index from the array of attacks
    // Based on the chosen attack, the corresponding attack methods will be called
    public void coordinateAttack(Player player) {

        if (!isAlive) return;

        Random random = new Random();
        int attackIndex = random.nextInt(attackTypes.size());
        String selectedAttack = attackTypes.get(attackIndex);

        switch (selectedAttack) {
            case "SLAM":
                leftHand.slamHand(player, Direction.RIGHT);
                rightHand.slamHand(player, Direction.LEFT);
                break;
            case "SWEEP_RIGHT":
                leftHand.sweepHand();
                break;
            case "SWEEP_LEFT":
                rightHand.sweepHand();
                break;
            case "CLAP":
                rightHand.clapHand(leftHand); // Might need to pass the other hand to check for collison
                leftHand.clapHand(rightHand); // Same
                break;
        }
    }

    // This is the method used to communicate with the boss from the hands
    // At the end of every update this method is called
    // The method will check if both hands are in the idle position, if so, then will coordinate the new attack
    public void handleHandsIdleState(Player player) {

        if (!isAlive) return;

        // Might need to check if the hands are null but unsure right now
        // Should never be null after initial creation because when a hand is kills, will be respawned
        if (leftHand.handState == HandState.IDLE && rightHand.handState == HandState.IDLE) {
            coordinateAttack(player); 
        }
    }

    public void bossTakeDamage(int amount) {
        if (!isAlive) return;

        health -= amount;
        if (health <= 0) {
            health = 0;
            die();
        }
    }

    public int getCurrentHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isDead() {
        return !isAlive;
    }

    private void die() {
        isAlive = false;
        System.out.println("Boss fight over");
        leftHand.setMapEntityStatus(MapEntityStatus.REMOVED);
        rightHand.setMapEntityStatus(MapEntityStatus.REMOVED);
    }

    public void setInitialHands(BossHandEnemy leftHand, BossHandEnemy rightHand) {
        this.leftHand = leftHand;
        this.rightHand = rightHand;
    }

    public boolean checkIfHandsIdle() {
        if (leftHand.handState == HandState.IDLE && rightHand.handState == HandState.IDLE) {
            // Only reset the cooldown when both hands are idle
            if (idleCooldownCounter < idleCooldownFrames) {
                idleCooldownCounter++;
                return false; // Hands are idle, but cooldown is still active
            } else {
                idleCooldownCounter = 0; // Reset counter to allow next attack after cooldown
                return true; // Hands are idle and cooldown is complete
            }
        } else {
            // If any hand is not idle, reset the cooldown counter
            idleCooldownCounter = 0;
            return false;
        }
    }
}
