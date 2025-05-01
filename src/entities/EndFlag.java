package entities;

import bagel.Image;

/**
 * Class for representing end flag in game.
 */
public class EndFlag extends Entity {
    private static final double RADIUS = Double.parseDouble(Entity.APP_PROPERTIES.getProperty("gameObjects.endFlag.radius"));

    /**
     * Default constructor for EndFlag entity.
     * @param x The end flag's initial x coordinate.
     * @param y The end flag's inital y coordinate.
     */
    public EndFlag(int x, int y) {
        super(x, y,
            new Image(Entity.APP_PROPERTIES.getProperty("gameObjects.endFlag.image")),
            RADIUS,
            Integer.parseInt(Entity.APP_PROPERTIES.getProperty("gameObjects.endFlag.speed")),
            0);
    }
}
