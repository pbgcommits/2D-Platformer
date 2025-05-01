package entities;

import bagel.*;

/**
 * Class to represent in game enemies.
 * Enemies are entities which can deal damage to the player.
 * Each enemy is allowed to damage the player only once.
 */
public class Enemy extends RandomMovementEntity {

    private final double DAMAGE = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.enemy.damageSize"));
    private final static double RADIUS = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.enemy.radius"));
    private boolean hasDamagedPlayer = false;

    /**
     * Get whether the enemy has damaged the player.
     * @return Whether the enemy has damaged the player.
     */
    public boolean hasDamagedPlayer() {
        return hasDamagedPlayer;
    }

    /**
     * Set whether the enemy has damaged the player.
     * @param hasDamagedPlayer Whether the enemy has damaged the player.
     */
    public void setHasDamagedPlayer(boolean hasDamagedPlayer) {
        this.hasDamagedPlayer = hasDamagedPlayer;
    }

    /**
     * Get how much damage the enemy deals to the player.
     * @return How much damage the enemy deals to the player.
     */
    public double getDamage() {
        return DAMAGE;
    }

    /**
     * Default constructor for enemy.
     * @param x The initial x coordinate for the enemy.
     * @param y The initial x coordinate for the enemy.
     */
    public Enemy(int x, int y) {
        super(x, y,
            new Image(APP_PROPERTIES.getProperty("gameObjects.enemy.image")),
            RADIUS,
            Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.enemy.speed")),
            0, Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.enemy.randomSpeed")),
                Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.enemy.maxRandomDisplacementX"))
        );
    }
}
