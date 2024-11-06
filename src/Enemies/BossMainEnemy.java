package Enemies;

import Level.Map;
import Level.MapEntityStatus;
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

    private int idleCooldownFrames = 120;
    private int idleCooldownCounter = 0;

    public BossMainEnemy(Map map, int health) {
        this.map = map;
        this.health = health;
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
        attackTypes.add("SLAM");
    }

    public void respawnHands(){ 

        if (!isAlive) return;

        Point lhandArea = new Point(map.getMapTile(5, 10).getX(), map.getMapTile(5, 10).getY()-20); // DETERMINE THESE LATER BASED ON MAP TILES LOCATION
        Point rhandArea = new Point(map.getMapTile(12, 10).getX(), map.getMapTile(12, 10).getY()-20);

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
    public void coordinateAttack() {

        if (!isAlive) return;

        Random random = new Random();
        int attackIndex = random.nextInt(attackTypes.size());
        String selectedAttack = attackTypes.get(attackIndex);

        switch (selectedAttack) {
            case "SLAM":
                leftHand.slamHand();
                rightHand.slamHand();
                break;
            case "SWEEP_RIGHT":
                leftHand.sweepHand();
                break;
            case "SWEEP_LEFT":
                rightHand.sweepHand();
                break;
        }
    }

    // This is the method used to communicate with the boss from the hands
    // At the end of every update this method is called
    // The method will check if both hands are in the idle position, if so, then will coordinate the new attack
    public void handleHandsIdleState() {

        if (!isAlive) return;

        // Might need to check if the hands are null but unsure right now
        // Should never be null after initial creation because when a hand is kills, will be respawned
        if (leftHand.handState == HandState.IDLE && rightHand.handState == HandState.IDLE) {
            coordinateAttack(); 
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
