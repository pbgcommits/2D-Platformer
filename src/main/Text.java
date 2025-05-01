package main;

import bagel.DrawOptions;
import bagel.Font;
import bagel.Window;
import bagel.util.Colour;

/**
 * Class that contains text to be printed to the screen.
 */
public class Text {

    private String message;
    private final Font FONT;
    private int x;
    private int y;
    private Colour colour = Colour.WHITE;

    /**
     * Draw the message to the screen.
     */
    public void draw() {
        FONT.drawString(message, x, y, new DrawOptions().setBlendColour(colour));
    }

    /**
     * Draw the message to the screen + additional text.
     * @param message Additional text to add after the text's default message.
     */
    public void drawConcatenate(String message) {
        FONT.drawString(String.format(this.message + message), x, y, new DrawOptions().setBlendColour(colour));
    }

    /**
     * Constructor for text class.
     * @param message The contents of the text.
     * @param font The font the text uses.
     * @param x The x coordinate of the text.
     * @param y The y coordinate of the text.
     */
    public Text(String message, Font font, int x, int y) {
        this.message = message;
        this.FONT = font;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for text class.
     * @param message The contents of the text.
     * @param font The font the text uses.
     * @param x The x coordinate of the text.
     * @param y The y coordinate of the text.
     * @param colour The colour of the text.
     */
    public Text(String message, Font font, int x, int y, Colour colour) {
        this.message = message;
        this.FONT = font;
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    /**
     * Constructor for text class. Initialises x-coordinate to the centre of the window.
     * @param message The contents of the text.
     * @param font The font the text uses.
     * @param y The y coordinate of the text.
     */
    public Text(String message, Font font, int y) {
        this.message = message;
        this.FONT = font;
        this.x = (int) (Window.getWidth()/2.0 - font.getWidth(message)/2.0);
        this.y = y;
    }
}
