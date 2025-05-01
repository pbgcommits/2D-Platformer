package entities.collectibles;

import bagel.Image;
import entities.Entity;

/**
 * A class for any object which can be collected by the player.
 */
public abstract class Collectible extends Entity {
    private boolean collected = false;

    /**
     * Get the collected status of the collectible.
     * @return Whether the collectible has been collected.
     */
    public boolean isCollected() {
        return collected;
    }

    /**
     * Update whether the collectible has been collected.
     * @param collected Whether the collectible has been collected.
     */
    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    /**
     * Play the collectible's collected animation.
     */
    public void playCollectAnimation() {
        moveY(true);
    }

    /**
     * Get whether the collectible has been moved off-screen (by the collection animation).
     * @return Whether the collectible has been moved off-screen.
     */
    public boolean isOffScreen() {
        return getY() < -getRadius();
    }

    /**
     * Default constructor for collectible entity.
     * @param x The initial x coordinate for the collectible.
     * @param y The initial y coordinate for the collectible.
     * @param image The default image the collectible uses.
     * @param radius The collision radius of the collectible.
     * @param speedX The speed the collectible travels along the x coordinate.
     */
    public Collectible(int x, int y, Image image, double radius, int speedX) {
        // When collected, all collectibles move off the screen at a speed of 10 pixels per second
        super(x, y, image, radius, speedX, 10);
    }
}
