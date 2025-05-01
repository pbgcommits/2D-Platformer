package entities;

import bagel.Image;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The boss, which the player fights in level 3.
 */
public class Boss extends Entity implements Damageable {
    private static final double RADIUS = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.enemyBoss.radius"));
    private double health = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.enemyBoss.health"));

    /**
     * Get the boss's current health.
     * @return The boss's current health.
     */
    public double getHealth() {
        return health;
    }

    /**
     * Decrease the boss's health.
     * @param damage The amount by which to decrease the boss's health.
     */
    public void takeDamage(double damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Play the boss's death animation.
     */
    public void die() {
        moveY(false, 2);
    }
    private final int ACTIVATION_RANGE = Integer.parseInt(
            APP_PROPERTIES.getProperty("gameObjects.enemyBoss.activationRadius"));
    private final int FRAMES_BETWEEN_FIREBALL_ATTEMPT = 100;
    private int framesUntilFireballAttempt = 0;

    /**
     * Get the range at which the boss and the player may commence battle
     * @return The range at which the boss and the player may commence battle.
     */
    public int getACTIVATION_RANGE() {
        return ACTIVATION_RANGE;
    }

    /**
     * Get how many frames the boss should wait until it tries to throws another fireball.
     * @return How many frames the boss should wait until it tries to throws another fireball.
     */
    public int getFRAMES_BETWEEN_FIREBALL_ATTEMPT() {
        return FRAMES_BETWEEN_FIREBALL_ATTEMPT;
    }

    /**
     * Get the number of frames remaining before the boss should attempt a fireball throw.
     * @return The number of frames remaining before the boss should attempt a fireball throw.
     */
    public int getFramesUntilFireballAttempt() {
        return framesUntilFireballAttempt;
    }

    /**
     * Update how many frames it will be until the boss attempts a fireball throw.
     * @param frames The new number of frames.
     */
    public void setFramesUntilFireballAttempt(int frames) {
        this.framesUntilFireballAttempt = frames;
    }

    /**
     * Decrease the number of frames remaining until the boss attempts a fireball throw by 1.
     */
    public void decrementFramesUntilFireballAttempt() {
        framesUntilFireballAttempt--;
    }

    /**
     * Randomly decide if the boss's fireball throw is successful.
     * @return Whether the boss's fireball throw has succeeded.
     */
    public boolean successfulFireballThrow() {
        boolean b = ThreadLocalRandom.current().nextBoolean();
        return b;
    }

    /**
     * The boss's default constructor.
     * @param x The boss's initial x coordinate.
     * @param y The boss's initial y coordinate.
     */
    public Boss(int x, int y) {
        super(x, y, new Image(APP_PROPERTIES.getProperty("gameObjects.enemyBoss.image")), RADIUS,
                Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.enemyBoss.speed")), 0);
    }
}
