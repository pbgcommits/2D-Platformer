package main;

import bagel.*;

import levels.*;

import java.util.Properties;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2024
 *
 * @author Patrick Barton Grace
 */
public class ShadowMario extends AbstractGame {
    /**
     * The level the player is in.
     */
    private Level level = null;
    /**
     * Game background.
     */
    private final Image BACKGROUND_IMAGE;
    /**
     * Various pieces of text to be displayed throughout the game.
     */
    private final Text TITLE, INSTRUCTIONS, WIN_TEXT, LOSS_TEXT;

    /**
     * The current state of the game.
     */
    private GameState gameState = GameState.NOT_STARTED;

    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

        final String FONT = game_props.getProperty("font");

        TITLE = new Text(
                message_props.getProperty("title"),
                new Font(FONT, Integer.parseInt(game_props.getProperty("title.fontSize"))),
                Integer.parseInt(game_props.getProperty("title.x")),
                Integer.parseInt(game_props.getProperty("title.y"))
            );

        INSTRUCTIONS = new Text(
                message_props.getProperty("instruction"),
                new Font(FONT, Integer.parseInt(game_props.getProperty("instruction.fontSize"))),
                Integer.parseInt(game_props.getProperty("instruction.y"))
        );

        final Font MESSAGE_FONT = new Font(FONT, Integer.parseInt(game_props.getProperty("message.fontSize")));
        final int MESSAGE_Y = Integer.parseInt(game_props.getProperty("message.y"));

        WIN_TEXT = new Text(
                message_props.getProperty("gameWon"),
                MESSAGE_FONT,
                MESSAGE_Y
        );

        LOSS_TEXT = new Text(
                message_props.getProperty("gameOver"),
                MESSAGE_FONT,
                MESSAGE_Y
        );
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        ShadowMario game = new ShadowMario(game_props, message_props);
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        // Close window
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        switch (gameState) {
            case NOT_STARTED:
                // Draw title and instructions
                TITLE.draw();
                INSTRUCTIONS.draw();
                if (input.wasPressed(Keys.NUM_1)) {
                    initLevel(1);
                    gameState = GameState.STARTED;
                }
                else if (input.wasPressed(Keys.NUM_2)) {
                    initLevel(2);
                    gameState = GameState.STARTED;
                }
                else if (input.wasPressed(Keys.NUM_3)) {
                    initLevel(3);
                    gameState = GameState.STARTED;
                }
                break;
            case STARTED:
                if (level.checkWinCondition()) {
                    gameState = GameState.WON;
                    return;
                }
                if (level.checkLossCondition()) {
                    gameState = GameState.LOST;
                    return;
                }
                level.update(input);
                level.drawWorld();
                break;
            case WON:
                WIN_TEXT.draw();
                // Restart game
                if (input.wasPressed(Keys.SPACE)) {
                    gameState = GameState.NOT_STARTED;
                }
                break;
            case LOST:
                LOSS_TEXT.draw();
                // Restart game
                if (input.wasPressed(Keys.SPACE)) {
                    gameState = GameState.NOT_STARTED;
                }
                break;
        }
    }

    /**
     * Create the given level.
     * @param levelNum Which number level the player wants to play.
     */
    private void initLevel(int levelNum) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");

        switch (levelNum) {
            case 1:
                level = new Level1(game_props, message_props);
                break;
            case 2:
                level = new Level2(game_props, message_props);
                break;
            default:
                level = new Level3(game_props, message_props);
                break;
        }
    }
}
