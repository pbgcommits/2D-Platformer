package levels;

import bagel.*;
import bagel.util.Colour;
import entities.*;
import entities.collectibles.*;
import main.Text;

import java.util.ArrayList;
import java.util.Properties;

/**
 * The third level of the game.
 */
public class Level3 extends Level {
    private Boss boss;
    private ArrayList<FlyingPlatform> flyingPlatforms;
    private ArrayList<Fireball> fireballs;
    private ArrayList<DoubleScorePower> doubleScores;
    private ArrayList<InvinciblePower> invinciblePowers;
    private int framesOfDoubleScoreRemaining = -1;
    private int framesOfInvincibilityRemaining = -1;
    private final Text BOSS_HEALTH;

    /**
     * Check if the player has won the level.
     * @return Whether the player has won the level.
     */
    @Override
    public boolean checkWinCondition() {
        return checkReachedFlag() && boss.getHealth() <= 0;
    }

    /**
     * Update all entity's status in the level.
     * @param input Any input the player may have given the program.
     */
    @Override
    public void update(Input input) {
        super.update(input);

        // Check for boss death
        if (boss.getHealth() <= 0 && boss.getY() < Window.getHeight() + 5 * boss.getRadius()) {
            boss.die();
        }

        if (boss.getFramesUntilFireballAttempt() > 0) {
            boss.decrementFramesUntilFireballAttempt();
        }

        // Logic for when player enters the boss zone
        if (Math.abs(boss.getX() - player.getX()) < boss.getACTIVATION_RANGE()) {
            // Throw player fireball - always thrown towards the boss
            if (input.wasPressed(Keys.S)) {
                boolean playerThrowingRight = player.getX() < boss.getX();
                fireballs.add(new Fireball(player.getX(), player.getY(), playerThrowingRight, true));
            }
            if (boss.getFramesUntilFireballAttempt() <= 0) {
                // Randomly throw boss fireball - always thrown towards the player
                if (boss.successfulFireballThrow()) {
                    boolean bossFacingRight = player.getX() > boss.getX();
                    fireballs.add(new Fireball(boss.getX(), boss.getY(), bossFacingRight, false));
                    boss.setFramesUntilFireballAttempt(boss.getFRAMES_BETWEEN_FIREBALL_ATTEMPT());
                }
                else {
                    boss.setFramesUntilFireballAttempt(boss.getFRAMES_BETWEEN_FIREBALL_ATTEMPT());
                }
            }
        }

        // Check for fireball collisions
        for (Fireball f: new ArrayList<>(fireballs)) {
            f.moveX(!f.isGoingRight(), f.getShootingSpeed());
            if (player.euclideanDistance(f) < player.collisionRange(f) && !f.thrownByPlayer()) {
                player.takeDamage(f.getDamage());
                fireballs.remove(f);
            }
            else if (boss.euclideanDistance(f) < boss.collisionRange(f)
                    && f.thrownByPlayer()) {
                boss.takeDamage(f.getDamage());
                fireballs.remove(f);
            }
            else if (f.getX() > Window.getWidth() || f.getX() < 0) {
                fireballs.remove(f);
            }
        }
    }

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
        for (Fireball f: fireballs) {
            f.draw();
        }
        boss.draw();
        BOSS_HEALTH.drawConcatenate(String.valueOf(Math.round(boss.getHealth() * 100)));
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
        for (Fireball f: fireballs) {
            f.moveX(movingRight);
        }
        boss.moveX(movingRight);
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
     * Default constructor for level 3.
     * @param game_props Properties file containing game information.
     * @param message_props Properties file containing information about text.
     */
    public Level3(Properties game_props, Properties message_props) {
        super(game_props, message_props);
        final String FONT = game_props.getProperty("font");
        BOSS_HEALTH = new Text(
                message_props.getProperty("health"),
                new Font(FONT, Integer.parseInt(game_props.getProperty("enemyBossHealth.fontSize"))),
                Integer.parseInt(game_props.getProperty("enemyBossHealth.x")),
                Integer.parseInt(game_props.getProperty("enemyBossHealth.y")),
                Colour.RED
        );

        boss = null;
        fireballs = new ArrayList<>();
        flyingPlatforms = new ArrayList<>();
        doubleScores = new ArrayList<>();
        invinciblePowers = new ArrayList<>();

        String level_file = game_props.getProperty("level3File");
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
                case "ENEMY_BOSS":
                    boss = new Boss(x, y);
                    break;
                case "FLYING_PLATFORM":
                    flyingPlatforms.add(new FlyingPlatform(x, y));
                    break;
            }
        }
    }
}
