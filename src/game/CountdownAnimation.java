package game;

import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;
/**
 * @author batel pirov.
 * The CountdownAnimation will display the given gameScreen,
 * for numOfSeconds seconds, and on top of them it will show
 * a countdown from countFrom back to 1, where each number will
 * appear on the screen for (numOfSeconds / countFrom) secods, before
 * it is replaced with the next one.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private int temp;
    /**
     * constructor.
     * @param numOfSeconds - always 60.
     * @param countFrom - the number we count from.
     * @param gameScreen - list of sprites for the background.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.numOfSeconds = numOfSeconds;
        this.temp = countFrom;
    }
    /**
     * count from 3 to 1.
     * @param d - the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
        // draw the background.
        this.gameScreen.drawAllOn(d);
        Sleeper sl = new Sleeper();
        // sleep for all number unless number three.
        if (this.countFrom != this.temp) {
            sl.sleepFor((long) (this.numOfSeconds / this.temp * 1000));
        }
        if (this.countFrom > 0) {
            d.setColor(Color.pink);
            d.drawText(390, d.getHeight() / 2, Integer.toString(this.countFrom), 32);
        }
        this.countFrom = this.countFrom - 1;
    }
    /**
     * indicate the time the counting should stop.
     * @return a boolean.
     */
    public boolean shouldStop() {
        if (this.countFrom == -1) {
            return true;
        }
        return false;
    }
}
