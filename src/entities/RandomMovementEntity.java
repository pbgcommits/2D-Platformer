package entities;

import bagel.Image;

import java.util.Random;

/**
 * An abstract class for any entity which moves randomly.
 * Used for enemies and flying platforms.
 */
public abstract class RandomMovementEntity extends Entity {
    private int randomSpeedX;
    private int maxRandomDisplacementX;
    private int framesMoved = 0;
    private boolean facingRight;

    /**
     * Randomly move the entity along the x-axis.
     */
    public void randomlyMoveX() {
        moveX(facingRight, randomSpeedX);
        if (framesMoved >= maxRandomDisplacementX) {
            framesMoved = 0;
            facingRight = !facingRight;
        }
        else {
            framesMoved += randomSpeedX;
        }
    }

    /**
     * Default constructor for RandomMovementEntity.
     * @param x Initial x coordinate for RandomMovementEntity.
     * @param y Initial y coordinate for RandomMovementEntity.
     * @param image Default image for RandomMovementEntity.
     * @param radius Collision radius for RandomMovementEntity.
     * @param speedX The speed along the x-axis of the RandomMovementEntity.
     * @param speedY The speed along the y-axis of the RandomMovementEntity.
     * @param randomSpeedX How fast the RandomMovementEntity randomly moves along the x-axis.
     * @param maxRandomDisplacementX The maximum distance a RandomMovementEntity can move from its starting position.
     */
    public RandomMovementEntity(int x, int y, Image image, double radius, int speedX, int speedY,
                                int randomSpeedX, int maxRandomDisplacementX) {
        super(x, y, image, radius, speedX, speedY);
        Random r = new Random();
        facingRight = r.nextBoolean();
        this.randomSpeedX = randomSpeedX;
        this.maxRandomDisplacementX = maxRandomDisplacementX;
    }
}
