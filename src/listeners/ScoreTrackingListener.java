package listeners;
import sprites.Ball;
import sprites.Block;
import general.Counter;
/**
 * @author batel pirov.
 * a ScoreTrackingListener is update the current counter when blocks are being hit and removed.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    /**
     * constructor.
     * @param scoreCounter - the current score in the game.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
       this.currentScore = scoreCounter;
    }
    /**
     * update the current score according the type of the hitting.
     * @param beingHit - the block which being hit.
     * @param hitter - the ball which hit.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(15);
            beingHit.removeHitListener(this);
        } else {
            this.currentScore.increase(5);
        }
    }
 }