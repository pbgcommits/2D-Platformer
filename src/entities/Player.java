package entities;

import bagel.*;
import entities.collectibles.Collectible;

import java.util.ArrayList;

/**
 * Class to represent player entity in game.
 * Unlike other entities, the player can have two possible images: a right-facing image, and a left-facing image.
 * The player also has health and the ability to jump.
 */
public class Player extends Entity implements Damageable {
    private static final double RADIUS = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.player.radius"));
    private final Image RIGHT_IMAGE = new Image(APP_PROPERTIES.getProperty("gameObjects.player.imageRight"));
    private final Image LEFT_IMAGE = new Image(APP_PROPERTIES.getProperty("gameObjects.player.imageLeft"));
    private final double INITIAL_HEALTH = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.player.health"));
    private final int JUMP_SPEED_CAP = 20;
    private double health = INITIAL_HEALTH;
    private boolean facingRight = true;
    private boolean isJumping = false;
    private boolean isInvincible = false;
    private int score = 0;
    private int scoreMultiplier = 1;
    private int distanceFromFloor = 0;

    /**
     * Get the x direction the player is facing.
     * @return main.Direction player is facing.
     */
    public boolean facingRight() {
        return facingRight;
    }

    /**
     * Get whether the player is currently invincible.
     * @return Whether the player is currently invincible.
     */
    public boolean isInvincible() {
        return isInvincible;
    }

    /**
     * Update whether the player is invincible.
     * @param invincible The desired invincibility status of the player.
     */
    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    /**
     * Get the player's current score multiplier (default 1).
     * @return The player's current score multiplier (default 1).
     */
    public int getScoreMultiplier() {
        return scoreMultiplier;
    }

    /**
     * Update the player's score multiplier.
     * @param scoreMultiplier The desired score multiplier for the player.
     */
    public void setScoreMultiplier(int scoreMultiplier) {
        this.scoreMultiplier = scoreMultiplier;
    }

    /**
     * Get the player's current score.
     * @return The player's current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Update the player's score.
     * @param score The desired new score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Increase the player's score by a certain amount.
     * @param increment The amount by which the player's score should increase.
     */
    public void incrementScore(int increment) {
        this.score += increment;
    }

    /**
     * Get the player's current health (out of 1).
     * @return The player's current health (out of 1).
     */
    public double getHealth() {
        return health;
    }

    /**
     * Update the player's health.
     * @param health The desired new health.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Get the image suitable for the direction that the player is facing.
     * @return The player's image, facing either left or right dependent on their current direction.
     */
    @Override
    protected Image getIMAGE() {
        if (facingRight) {
            return RIGHT_IMAGE;
        }
        else {
            return LEFT_IMAGE;
        }
    }

    /**
     * Draw the player facing the correct direction.
     */
    @Override
    public void draw() {
        if (facingRight) {
            RIGHT_IMAGE.draw(x, y);
        }
        else {
            LEFT_IMAGE.draw(x, y);
        }
    }

    /**
     * Update the player's direction.
     * @param right Whether the player should face right (true) or left (false).
     */
    public void setFacingRight(boolean right) {
        this.facingRight = right;
    }

    /**
     * Get whether the player is jumping.
     * @return Whether the player is jumping.
     */
    public boolean isJumping() {
        return isJumping;
    }

    /**
     * Commence player's jumping motion.
     *
     */
    public void startJumping() {
        isJumping = true;
        speedY = -JUMP_SPEED_CAP;
    }

    /**
     * Update the player's y-level, depending on the stage of their jump.
     */
    public void jump() {
        // Incrementing the player's y value by one ensures you don't miss any platform collisions
        for (int i = 0; i < Math.abs(speedY); i++) {
            if (speedY < 0) {
                moveY(true, 1);
                distanceFromFloor--;
            }
            else {
                moveY(false, 1);
                distanceFromFloor++;
            }
            // Check if player has landed on the ground
            if (distanceFromFloor >= 0) {
                speedY = -JUMP_SPEED_CAP;
                stopJumping();
                return;
            }
        }
        speedY++;
    }

    /**
     * Update the player's y-level, depending on the stage of their jump.
     * @param flyingPlatforms All flying platforms in the level.
     */
    public void jump(ArrayList<FlyingPlatform> flyingPlatforms) {
        // Incrementing the player's y value by one ensures you don't miss any platform collisions
        for (int i = 0; i < Math.abs(speedY); i++) {
            if (speedY < 0) {
                moveY(true, 1);
                distanceFromFloor--;
            }
            else {
                moveY(false, 1);
                distanceFromFloor++;
            }
            // Check if player has landed on the ground
            if (distanceFromFloor >= 0) {
                speedY = -JUMP_SPEED_CAP;
                stopJumping();
                return;
            }
            // Check if player has landed on a flying platform
            if (speedY > 1 && landedOnFlyingPlatform(flyingPlatforms)) {
                stopJumping();
                speedY = 0;
                return;
            };
        }
        speedY++;
    }

    /**
     * Complete the player's jump.
     */
    public void stopJumping() {
        isJumping = false;
    }

    /**
     * Checks against each flying platform to see if the player has just walked off it.
     * @param flyingPlatforms All flying platforms in the level.
     * @return Whether the player has just walked off the edge of a flying platform.
     */
    public boolean walkedOffEdge(ArrayList<FlyingPlatform> flyingPlatforms) {
        return distanceFromFloor < 0 && !landedOnFlyingPlatform(flyingPlatforms) && !isJumping;
    }

    /**
     * Start the second half of a jump (i.e. the falling half).
     * Should only be called when the player walks off a flying platform.
     */
    public void fall() {
        isJumping = true;
    }

    /**
     * Calculate whether a player has landed on a flying platform.
     * @param flyingPlatforms All flying platforms in the player's current level.
     * @return Whether the player has landed on any flying platform.
     */
    public boolean landedOnFlyingPlatform(ArrayList<FlyingPlatform> flyingPlatforms) {
        for (FlyingPlatform f: flyingPlatforms) {
            if (Math.abs(x - f.getX()) < f.HALF_LENGTH
                && f.getY() - y <= f.HALF_HEIGHT
                && f.getY() - y  >= f.HALF_HEIGHT - 1
            ) {
                return true;
            }
        }
        return false;
    }

    /**
     * Decrease the player's health.
     * @param damage The amount by which to decrease the player's health.
     */
    public void takeDamage(double damage) {
        if (isInvincible) {
            return;
        }
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Play the player's death animation.
     */
    public void die() {
        moveY(false, 2);
    }

    /**
     * Check for collision with a collectible item.
     * @param c The item to check for collision with.
     * @return True if player is within collision range and if the player has not already collected this item.
     */
    public boolean collidedWith(Collectible c) {
        return euclideanDistance(c) < collisionRange(c) && !c.isCollected();
    }

    /**
     * Default constructor for a player.
     * @param x The player's initial x coordinate.
     * @param y The player's initial y coordinate.
     */
    public Player(int x, int y) {
        super(x, y, new Image(APP_PROPERTIES.getProperty("gameObjects.player.imageRight")), RADIUS, 0, -20);
    }
}
