package levels;

import bagel.Input;
import bagel.Keys;
import entities.*;
import entities.collectibles.*;

import java.util.ArrayList;
import java.util.Properties;

/**
 * The second level of the game.
 */
public class Level2 extends Level {
    private ArrayList<FlyingPlatform> flyingPlatforms;
    private ArrayList<DoubleScorePower> doubleScores;
    private ArrayList<InvinciblePower> invinciblePowers;
    private int framesOfDoubleScoreRemaining = -1;
    private int framesOfInvincibilityRemaining = -1;

    /**
     * Update player's position in a jump.
     * @param input Any input the player may have given the program.
     */
    @Override
    public void updatePlayerJumping(Input input) {
        if (input.wasPressed(Keys.UP)) {
            player.startJumping();
        }
        if (player.isJumping()) {
            player.jump(flyingPlatforms);
        }
        if (player.walkedOffEdge(flyingPlatforms)) {
            player.fall();
        }
    }

    /**
     * Draw in world.
     */
    @Override
    public void drawWorld() {
        super.drawWorld();
        for (DoubleScorePower d: doubleScores) {
            d.draw();
        }
        for (InvinciblePower i: invinciblePowers) {
            i.draw();
        }
        for (FlyingPlatform f: flyingPlatforms) {
            f.randomlyMoveX();
            f.draw();
        }
    }

    /**
     * Move all game entities along the x-axis.
     * @param movingRight Whether the entities are moving right or left.
     */
    @Override
    public void moveEntitiesX(boolean movingRight) {
        super.moveEntitiesX(movingRight);
        for (DoubleScorePower d: doubleScores) {
            d.moveX(movingRight);
        }
        for (InvinciblePower i: invinciblePowers) {
            i.moveX(movingRight);
        }
        for (FlyingPlatform f: flyingPlatforms) {
            f.moveX(movingRight);
        }
    }

    /**
     * Check if player has collided with collectibles.
     */
    @Override
    public void checkCollectibleCollisions() {
        super.checkCollectibleCollisions();
        // Check for player collision with double score powerups
        for (DoubleScorePower d: new ArrayList<>(doubleScores)) {
            if (player.collidedWith(d)) {
                player.setScoreMultiplier(DoubleScorePower.getScoreMultiplier());
                framesOfDoubleScoreRemaining = d.getDuration();
                d.setCollected(true);
            }
            if (d.isCollected()) {
                d.playCollectAnimation();
                if (d.isOffScreen()) {
                    doubleScores.remove(d);
                }
            }
        }
        // Check for collision with invincibility powerups
        for (InvinciblePower i: new ArrayList<>(invinciblePowers)) {
            if (player.collidedWith(i)) {
                player.setInvincible(true);
                framesOfInvincibilityRemaining = i.getDuration();
                i.setCollected(true);
            }
            if (i.isCollected()) {
                i.playCollectAnimation();
                if (i.isOffScreen()) {
                    invinciblePowers.remove(i);
                }
            }
        }
        updatePowerUpDurations();
    }

    /**
     * Update how long powerups have left.
     */
    public void updatePowerUpDurations() {
        if (framesOfDoubleScoreRemaining > 0) {
            framesOfDoubleScoreRemaining--;
            if (framesOfDoubleScoreRemaining <= 0) {
                player.setScoreMultiplier(1);
            }
        }
        if (framesOfInvincibilityRemaining > 0) {
            framesOfInvincibilityRemaining--;
            if (framesOfInvincibilityRemaining <= 0) {
                player.setInvincible(false);
            }
        }
    }

    /**
     * Default constructor for level 2.
     * @param game_props Properties file containing game information.
     * @param message_props Properties file containing information about text.
     */
    public Level2(Properties game_props, Properties message_props) {
        super(game_props, message_props);

        flyingPlatforms = new ArrayList<>();
        doubleScores = new ArrayList<>();
        invinciblePowers = new ArrayList<>();

        String level_file = game_props.getProperty("level2File");
        final ArrayList<String[]> LEVEL_INFO = main.IOUtils.readCsv(level_file);
        // Save info for easier access
        for (String[] line: LEVEL_INFO) {
            int x = Integer.parseInt(line[1]);
            int y = Integer.parseInt(line[2]);
            switch (line[0]) {
                case "PLATFORM":
                    platform = new Platform(x, y);
                    break;
                case "PLAYER":
                    player = new Player(x, y);
                    break;
                case "COIN":
                    coins.add(new Coin(x, y));
                    break;
                case "ENEMY":
                    enemies.add(new Enemy(x, y));
                    break;
                case "END_FLAG":
                    endFlag = new EndFlag(x, y);
                    break;
                case "DOUBLE_SCORE":
                    doubleScores.add(new DoubleScorePower(x, y));
                    break;
                case "INVINCIBLE_POWER":
                    invinciblePowers.add(new InvinciblePower(x, y));
                    break;
                case "FLYING_PLATFORM":
                    flyingPlatforms.add(new FlyingPlatform(x, y));
                    break;
            }
        }
    }
}
