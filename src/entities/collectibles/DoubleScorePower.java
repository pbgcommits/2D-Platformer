package entities.collectibles;

import bagel.Image;

/**
 * A Collectible PowerUp which doubles the player's score by 2 for a certain amount of time.
 */
public class DoubleScorePower extends PowerUp {
    private static double RADIUS = Double.parseDouble(APP_PROPERTIES.getProperty("gameObjects.doubleScore.radius"));
    private static int SCORE_MULTIPLIER = 2;

    /**
     * Get the amount to multiply the score by (2).
     * @return The amount to multiply the score by.
     */
    public static int getScoreMultiplier() {
        return SCORE_MULTIPLIER;
    }

    /**
     * Default constructor for DoubleScorePower.
     * @param x The initial coordinate for the DoubleScorePower.
     * @param y The initial coordinate for the DoubleScorePower.
     */
    public DoubleScorePower(int x, int y) {
        super(Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.doubleScore.maxFrames")), x, y,
                new Image(APP_PROPERTIES.getProperty("gameObjects.doubleScore.image")),
                RADIUS, Integer.parseInt(APP_PROPERTIES.getProperty("gameObjects.doubleScore.speed")));
    }
}
