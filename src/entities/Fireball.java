package entities;

import bagel.Image;

/**
 * An entity which can be shot by either the player or the boss.
 */
public class Fireball extends Entity {
    private final double DAMAGE = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.fireball.damageSize"));
    private static final double RADIUS = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.fireball.radius"));
    private boolean goingRight;
    private boolean thrownByPlayer;
    private static int shootingSpeed = Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.fireball.speed"));

    /**
     * Get how much damage the fireball deals.
     * @return How much damage the fireball deals.
     */
    public double getDamage() {
        return DAMAGE;
    }

    /**
     * Get whether the fireball is travelling to the right or left.
     * @return True if the fireball is travelling right; false if it is travelling left.
     */
    public boolean isGoingRight() {
        return goingRight;
    }

    /**
     * Get how fast the fireball is thrown.
     * @return How fast the fireball is thrown.
     */
    public static int getShootingSpeed() {
        return shootingSpeed;
    }

    /**
     * Get whether the fireball is thrown by the player.
     * @return Whether the fireball is thrown by the player.
     */
    public boolean thrownByPlayer() {
        return thrownByPlayer;
    }

    /**
     * Default constructor for fireball.
     * @param x The initial x coordinate of the fireball.
     * @param y The initial y coordinate of the fireball.
     * @param goingRight The initial direction of the fireball along the x-axis.
     * @param thrownByPlayer Whether the fireball was thrown by the player.
     */
    public Fireball(int x, int y, boolean goingRight, boolean thrownByPlayer) {
        super(x, y, new Image(APP_PROPERTIES.getProperty("gameObjects.fireball.image")), RADIUS,
                Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.coin.speed")), 0);
        this.goingRight = goingRight;
        this.thrownByPlayer = thrownByPlayer;
    }
}
