package listeners;
import sprites.Ball;
import sprites.Block;
import general.Counter;
import game.GameLevel;
/**
 * @author batel pirov.
 * a BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
  private GameLevel game;
  private Counter remainingBlocks;
  /**
   * constructor.
   * @param game - the game the blockRemover is work on.
   * @param remainedBlocks - the number of blocks.
   */
  public BlockRemover(GameLevel game, Counter remainedBlocks) {
     this.game = game;
     this.remainingBlocks = remainedBlocks;
  }
  /**
   * Blocks that are hit and reach 0 hit-points are removing from the game.
   * Also remove this listener from the block
   * that is being removed from the game.
   * @param beingHit - the block which being hit.
   * @param hitter - the ball which hit.
   */
  public void hitEvent(Block beingHit, Ball hitter) {
      if (beingHit.getHitPoints() == 0) {
          beingHit.removeFromGame(game);
          beingHit.removeHitListener(this);
          this.remainingBlocks.decrease(1);
      }
  }
}
