package entities;

import bagel.Image;

/**
 * Flying platforms appear in levels 2 and 3. The player can land on them.
 */
public class FlyingPlatform extends RandomMovementEntity {
    /**
     * A flying platform's half length.
     */
    protected static final int HALF_LENGTH = Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.flyingPlatform.halfLength"));
    /**
     * A flying platform's half height.
     */
    protected static final int HALF_HEIGHT = Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.flyingPlatform.halfHeight"));

    /**
     * Default constructor for flying platform.
     * @param x The initial x coordinate of the flying platform.
     * @param y The initial y coordinate of the flying platform.
     */
    public FlyingPlatform(int x, int y) {
        super(x, y, new Image(APP_PROPERTIES.getProperty("gameObjects.flyingPlatform.image")), 0,
                Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.flyingPlatform.speed")), 0,
                Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.flyingPlatform.randomSpeed")),
                Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.flyingPlatform.maxRandomDisplacementX")));
    }
}
