package entities.collectibles;

import bagel.Image;

/** Abstract implementation of a powerup which can be collected by the player.
 *
 */
public abstract class PowerUp extends Collectible {
    private int duration;

    /**
     * Get the powerup's duration.
     * @return The powerup's duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Change the powerup's duration.
     * @param duration The powerup's new duration.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Default constructor for a powerup.
     * @param duration How long the powerup should last.
     * @param x The initial x coordinate of the powerup.
     * @param y The initial y coordinate of the powerup.
     * @param image The default image of the powerup.
     * @param radius The collision radius of the powerup.
     * @param speedX How quickly the powerup moves along the x-axis.
     */
    public PowerUp(int duration, int x, int y, Image image, double radius, int speedX) {
        super(x, y, image, radius, speedX);
        this.duration = duration;
    }

}
