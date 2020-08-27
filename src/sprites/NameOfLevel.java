package sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import graphics.Rectangle;
/**
 * @author batel pirov.
 * is sit at the top of the screen and indicate the name of the level.
 */
public class NameOfLevel implements Sprite {
    private String name;
    private Rectangle rect;
    /**
     * constructor.
     * @param name - the name of the level.
     * @param rect - the rectangle at the top of the screen
     * which the name of the level going to be written on it.
     */
    public NameOfLevel(String name, Rectangle rect) {
        this.name = name;
        this.rect = rect;
    }
    /**
     * draw the sprite to the screen.
     * @param d - the surface.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int hight = (int) this.rect.getHeight();
        d.setColor(Color.black);
        d.drawText(x + 650, y + hight / 2 + 4, this.name, 12);
    }
    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
    }
}