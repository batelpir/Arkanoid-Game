package game;

import biuoop.DrawSurface;
/**
 * @author batel pirov
 * pause the game when pressing 'p'.
 */
public class PauseScreen implements Animation {
    /**
     * draws the string of pausing the game.
     * @param d - the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
       d.drawText(165, d.getHeight() / 2, "paused -- press space to continue", 32);
    }
    /**
     * never stops, therefore the boolean is false.
     * @return a boolean.
     */
    public boolean shouldStop() { return false; }
 }