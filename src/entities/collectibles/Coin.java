package entities.collectibles;

import bagel.*;
import entities.Entity;

/**
 * Class to represent coins in game.
 * Coins can be collected by the player. They have an assigned value.
 */
public class Coin extends Collectible {
    private final int VALUE = Integer.parseInt(Entity.APP_PROPERTIES.getProperty("gameObjects.coin.value"));

    private final static double RADIUS = Double.parseDouble(Entity.APP_PROPERTIES.getProperty("gameObjects.coin.radius"));

    /**
     * Get the coin's value.
     * @return The coin's value.
     */
    public int getValue() {
        return VALUE;
    }

    /**
     * Default constructor for coin.
     * @param x The coin's initial x coordinate.
     * @param y The coin's initial y coordinate.
     */
    public Coin(int x, int y) {
        super(x, y, new Image(Entity.APP_PROPERTIES.getProperty("gameObjects.coin.image")), RADIUS,
                Integer.parseInt(Entity.APP_PROPERTIES.getProperty("gameObjects.coin.speed")));
    }
}
