package entities.collectibles;

import bagel.Image;

/**
 * A Collectible PowerUp which makes the player invincible for a certain amount of time.
 */
public class InvinciblePower extends PowerUp {
    private static double RADIUS = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.invinciblePower.radius"));

    /**
     * Default constructor for InvinciblePower.
     * @param x The initial coordinate for the InvinciblePower.
     * @param y The initial coordinate for the InvinciblePower.
     */
    public InvinciblePower(int x, int y) {
        super(Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.invinciblePower.maxFrames")), x, y,
                new Image(APP_PROPERTIES.getProperty("gameObjects.invinciblePower.image")),
                RADIUS, Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.invinciblePower.speed")));
    }
}
