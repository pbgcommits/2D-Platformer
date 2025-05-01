package levels;

import bagel.*;
import entities.*;
import entities.collectibles.*;
import main.Text;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Parent class for all levels in the game.
 */
public abstract class Level {
    /**
     * The player's score text.
     */
    protected final main.Text SCORE;
    /**
     * The player's health text.
     */
    protected final main.Text PLAYER_HEALTH;
    /**
     * The entity which the player controls.
     */
    protected Player player;

    /**
     * All enemies which appear in the level.
     */
    protected ArrayList<Enemy> enemies;
    /**
     * All coins which appear in the level.
     */
    protected ArrayList<Coin> coins;
    /**
     * All double score powers which appear in the level - only appear in levels 2 and 3.
     */

    /**
     * The flag at the end of the level, which the player must touch to win.
     */
    protected EndFlag endFlag;
    /**
     * The base platform, which the player walks on.
     */
    protected Platform platform;
    /**
     * The current state of the game.
     */
    protected main.GameState gameState = main.GameState.NOT_STARTED;

    /**
     * Check whether the player has died.
     * @return Whether the player has died.
     */
    public boolean checkPlayerDeath() {
        return player.getHealth() <= 0;
    }

    /**
     * Check whether the player has reached the end flag.
     * @return Whether the player has reached the end flag.
     */
    public boolean checkReachedFlag() {
        return player.euclideanDistance(endFlag) < player.collisionRange(endFlag);
    }

    /**
     * Check if the player has lost the level.
     * @return Whether the player has lost the level.
     */
    public boolean checkLossCondition() {
        return player.getY() > Window.getHeight() + player.getRadius();
    }

    /**
     * Check if the player has won the level.
     * @return Whether the player has won the level.
     */
    public  boolean checkWinCondition() {
        return checkReachedFlag();
    }

    /**
     * Update all entity's status in the level.
     * @param input Any input the player may have given the program.
     */
    public void update(Input input) {
        // Loss condition
        if (checkPlayerDeath()) {
            // Move player down until off-screen
            player.die();
            // Stop the player moving once they're dead
            return;
        }

        // Move entities left and/or right, and set direction for player
        if (input.isDown(Keys.LEFT)) {
            moveEntitiesX(false);
        }
        if (input.isDown(Keys.RIGHT)) {
            moveEntitiesX(true);
        }

        checkCollectibleCollisions();

        // Check for player collision with enemy
        for (Enemy e: enemies) {
            if (player.euclideanDistance(e) < player.collisionRange(e) && !e.hasDamagedPlayer() && !player.isInvincible()) {
                player.takeDamage(e.getDamage());
                e.setHasDamagedPlayer(true);
            }
        }
        updatePlayerJumping(input);
    };

    /**
     * Update player's position in a jump.
     * @param input Any input the player may have given the program.
     */
    public void updatePlayerJumping(Input input) {
        if (input.wasPressed(Keys.UP)) {
            player.startJumping();
        }
        if (player.isJumping()) {
            player.jump();
        }
    }

    /**
     * Draw in world.
     */
    public void drawWorld() {
        platform.draw();
        player.draw();
        endFlag.draw();
        for (Enemy e: enemies) {
            e.randomlyMoveX();
            e.draw();
        }
        for (Coin c: coins) {
            c.draw();
        }
        SCORE.drawConcatenate(String.valueOf(player.getScore()));
        PLAYER_HEALTH.drawConcatenate(String.valueOf((Math.round(player.getHealth() * 100))));
    }

    /**
     * Move all game entities along the x-axis.
     * @param movingRight Whether the entities are moving right or left.
     */
    public void moveEntitiesX(boolean movingRight) {
        player.setFacingRight(movingRight);
        endFlag.moveX(movingRight);

        if (platform.getX() < Platform.MAX_X) {
            platform.moveX(movingRight);
        }
        for (Coin c : coins) {
            c.moveX(movingRight);
        }
        for (Enemy e : enemies) {
            e.moveX(movingRight);
        }
    }

    /**
     * Check if player has collided with collectibles.
     */
    public void checkCollectibleCollisions() {
        // Check for player collision with coin
        for (Coin c: new ArrayList<>(coins)) {
            if (player.collidedWith(c)) {
                player.incrementScore(c.getValue() * player.getScoreMultiplier());
                c.setCollected(true);
            }
            if (c.isCollected()) {
                c.playCollectAnimation();
                if (c.isOffScreen()) {
                    coins.remove(c);
                }
            }
        }
    }

    /**
     * Default constructor for the level.
     * @param game_props Properties file containing game information.
     * @param message_props Properties file containing information about text.
     */
    public Level(Properties game_props, Properties message_props) {

        final String FONT = game_props.getProperty("font");

        enemies = new ArrayList<>();
        coins = new ArrayList<>();

        SCORE = new Text(
                message_props.getProperty("score"),
                new Font(FONT, Integer.parseInt(game_props.getProperty("score.fontSize"))),
                Integer.parseInt(game_props.getProperty("score.x")),
                Integer.parseInt(game_props.getProperty("score.y"))
        );

        PLAYER_HEALTH = new Text(
                message_props.getProperty("health"),
                new Font(FONT, Integer.parseInt(game_props.getProperty("playerHealth.fontSize"))),
                Integer.parseInt(game_props.getProperty("playerHealth.x")),
                Integer.parseInt(game_props.getProperty("playerHealth.y"))
        );

    }
}