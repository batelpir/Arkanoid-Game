package game;

import biuoop.DrawSurface;
import general.Counter;
/**
 * @author batel pirov.
 * the screen which appear if the player is win or lose.
 */
public class EndScreen implements Animation {
    private String string;
    private Counter scoreToPrint;
    /**
     * constructor.
     * @param s - the string which printed before the score.
     * @param score - the score in the end of the game.
     */
    public EndScreen(String s, Counter score) {
        this.string = s;
        this.scoreToPrint = score;
    }
    /**
     * draw the string and the score.
     * @param d -the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
       d.drawText(185, d.getHeight() / 2, string + Integer.toString(this.scoreToPrint.getValue()), 32);
    }
    /**
     * never stops, therefore the boolean is false.
     * @return a boolean.
     */
    public boolean shouldStop() { return false; }
 }