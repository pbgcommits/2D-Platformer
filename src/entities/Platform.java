package entities;

import bagel.*;
/**
 * Class for representing platform in game.
 */
public class Platform extends Entity {
    /**
     * The x coordinate at which the platform is no longer allowed to move right.
     */
    public static final int MAX_X = 3000;

    /**
     * Default constructor for the platform.
     * @param x The initial x coordinate of the platform.
     * @param y The initial y coordinate of the platform.
     */
    public Platform(int x, int y) {
        super(x, y,
            new Image(APP_PROPERTIES.getProperty("gameObjects.platform.image")),
            0,
            Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.platform.speed")),
            0);
    }
}
