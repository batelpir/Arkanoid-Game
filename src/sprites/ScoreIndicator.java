package sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import graphics.Rectangle;
import general.Counter;
/**
 * @author batel pirov.
 * is in charge of displaying the current score.
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;
    private Rectangle rect;
    /**
     * constructor.
     * @param scoreCounter - the current score.
     * @param rect - the rectangle at the top of the screen
     * which the number of lives going to be written on it.
     */
    public ScoreIndicator(Counter scoreCounter, Rectangle rect) {
        this.scoreCounter = scoreCounter;
        this.rect = rect;
    }
    /**
     * draw the sprite to the screen.
     * @param d - the surface.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int width = (int) this.rect.getWidth();
        int hight = (int) this.rect.getHeight();
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(x, y, width, hight);
        d.setColor(Color.black);
        d.drawRectangle(x, y, width, hight);
        d.drawText(x + width / 2 - 30, y + hight / 2 + 4,
                "Score: " + Integer.toString(this.scoreCounter.getValue()), 13);
    }
    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
    }
}
