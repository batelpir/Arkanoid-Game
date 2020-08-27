package game;
import java.awt.Color;
import biuoop.DrawSurface;
/**
 * @author batel.
 * the animation of high scores.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;
    /**
     * a constructor.
     * @param scores - a high score table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }
    /**
     * draw the high score table to the surface.
     * @param d - the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
        d.setColor(new Color(255, 240, 190));
        d.fillRectangle(0, 0, 800, 600);
        d.setColor(new Color(255, 47, 131));
        d.drawText(75, 70, "High score table:" , 30);
        for (int i = 0; i < this.scores.getHighScores().size(); i++) {
            String name = this.scores.getHighScores().get(i).getName();
            String score = Integer.toString(this.scores.getHighScores().get(i).getScore());
            d.setColor(new Color(250, 150, 196));
            d.drawText(75, 100 + (i + 1) * 50, name + " " + score , 20);
        }
    }
    /**
     * tell when to stop the animation.
     * @return a boolean.
     */
    public boolean shouldStop() {
        return false;
    }
}
