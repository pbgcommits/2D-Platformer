package entities;

import bagel.Image;

import java.util.Properties;

/**
 * Abstract class for game entities.
 */
public abstract class Entity {
    protected static final Properties APP_PROPERTIES = main.IOUtils.readPropertiesFile("res/app.properties");
    protected int x;
    protected int y;
    protected final Image IMAGE;
    private double radius;
    protected int speedX;
    protected int speedY;

    /**
     * Get the collision radius of the entity.
     * @return The collision radius of the entity.
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Get the current x value of the entity.
     * @return The current x value of the entity.
     */
    public int getX() {
        return x;
    }

    /**
     * Set the current x value of the entity.
     * @param x The current x value of the entity.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the current y value of the entity.
     * @return The current y value of the entity.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the current y value of the entity.
     * @param y The current y value of the entity.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the entity's image.
     * @return An entity's default image.
     */
    protected Image getIMAGE() {
        return IMAGE;
    }

    /**
     * Draw the entity to the screen.
     */
    public void draw() {
        IMAGE.draw(x, y);
    }

    /**
     * Move the entity along the screen's x-axis.
     * @param right Whether the entity should move right (true) or left (false).
     */
    public void moveX(boolean right) {
        if (right) {
            x -= speedX;
        }
        else {
            x += speedX;
        }
    }

    /**
     * Move the entity along the screen's x-axis.
     * @param right Whether the entity should move right (true) or left (false).
     * @param speed How fast the entity should move in a frame.
     */
    public void moveX(boolean right, int speed) {
        if (right) {
            x -= speed;
        }
        else {
            x += speed;
        }
    }

    /**
     * Move the entity along the screen's y-axis.
     * @param up Whether the entity should move up (true) or down (false).
     */
    public void moveY(boolean up) {
        if (up) {
            y -= speedY;
        }
        else {
            y += speedY;
        }
    }

    /**
     * Move the entity along the screen's y-axis.
     * @param up Whether the entity should move up (true) or down (false).
     * @param speed How fast the entity should move in a frame.
     */
    public void moveY(boolean up, int speed) {
        if (up) {
            y -= speed;
        }
        else {
            y += speed;
        }
    }

    /**
     * Return the collision range between the caller and another entity.
     * @param entity the entity to check the collision range with.
     * @return the range at which the two entities can be considered to be colliding.
     */
    public double collisionRange(Entity entity) {
        return this.getRadius() + entity.getRadius();
    }

    /**
     * Calculate the Euclidean distance between the calling entity and another.
     * @param entity The entity to check the Euclidean distance from.
     * @return the Euclidean distance from another entity.
     */
    public double euclideanDistance(Entity entity) {
        return Math.sqrt(Math.pow(entity.getX()-this.getX(), 2) + Math.pow(entity.getY()-this.getY(), 2));
    }

    /**
     * Default constructor for an Entity.
     * @param x The entity's initial x coordinate.
     * @param y The entity's initial y coordinate.
     * @param image The entity's default image.
     * @param radius The entity's collision radius.
     * @param speedX The entity's default speed along the X axis.
     * @param speedY The entity's default speed along the Y axis.
     */
    public Entity(int x, int y, Image image, double radius, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.IMAGE = image;
        this.radius = radius;
        this.speedX = speedX;
        this.speedY = speedY;
    }
}
