package listeners;
import sprites.Ball;
import sprites.Block;
import general.Counter;
import game.GameLevel;
/**
 * @author batel pirov.
 * a BallRemover is in charge of removing balls, and updating an available-balls counter.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBalls;
    /**
     * constructor.
     * @param game - the game the ballRemover is work on.
     * @param remainedBalls - the number of balls.
     */
    public BallRemover(GameLevel game, Counter remainedBalls) {
        this.game = game;
        this.remainingBalls = remainedBalls;
    }
    /**
     * remove the ball from the game and update the balls counter.
     * @param beingHit - the block which being hit.
     * @param hitter - the ball which hit.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove the ball which hit.
        hitter.removeFromGame(game);
        // if this is the last ball - remove the listener.
        if (this.remainingBalls.getValue() == 1
                && (game.getNumOfLives().getValue() == 1 || this.game.getRemainedBlocks().getValue() == 0)) {
            beingHit.removeHitListener(this);
        }
        // updating the current number of balls.
        this.remainingBalls.decrease(1);
    }
}