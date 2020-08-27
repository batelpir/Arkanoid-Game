package sprites;
import biuoop.DrawSurface;
/**
 * @author batel pirov
 * Interface that has the following methods.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     * @param d - the surface.
     */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
 }
