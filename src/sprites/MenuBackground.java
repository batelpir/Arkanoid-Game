package sprites;

import java.awt.Color;
import biuoop.DrawSurface;
/**
 * @author batel.
 * background to the menu.
 */
public class MenuBackground implements Sprite {
    /**
     * draws the background - painting the screen.
     * @param d - the surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(255, 205, 225));
        d.fillRectangle(0, 0, 800, 600);
    }
    /**
     * do nothing.
     */
    public void timePassed() {
    }
}
