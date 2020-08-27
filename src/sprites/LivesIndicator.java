package sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import graphics.Rectangle;
import general.Counter;
/**
 * @author batel pirov.
 * is sit at the top of the screen and indicate the number of lives.
 */
public class LivesIndicator implements Sprite {
    private Counter livesCounter;
    private Rectangle rect;
    /**
     * constructor.
     * @param livesCounter - the current number of lives.
     * @param rect - the rectangle at the top of the screen
     * which the number of lives going to be written on it.
     */
    public LivesIndicator(Counter livesCounter, Rectangle rect) {
        this.livesCounter = livesCounter;
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
        d.setColor(Color.red);
        d.drawText(x + 30, y + hight / 2 + 4, "Lives: " + Integer.toString(this.livesCounter.getValue()), 12);
    }
    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
    }
}