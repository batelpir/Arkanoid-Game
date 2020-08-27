package game;
import biuoop.DrawSurface;
/**
 * @author batel pirov.
 * Interface that has the following methods.
 */
public interface Animation {
    /**
     * has the logic of one frame.
     * @param d - the draw surface.
     */
    void doOneFrame(DrawSurface d);
    /**
     * returns a boolean which indicates if
     * running the animation should stop or not.
     * @return a boolean.
     */
    boolean shouldStop();
 }