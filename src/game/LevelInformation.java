package game;

import java.util.List;
import general.Velocity;
import sprites.Block;
import sprites.Sprite;
/**
 * @author batel pirov
 * an interface with the following methods.
 */
public interface LevelInformation {
    /**
     * the number of balls in the level.
     * @return the number of balls in the level.
     */
    int numberOfBalls();
    /**
     * The initial velocity of each ball.
     * @return a list of velocities.
     */
    List<Velocity> initialBallVelocities();
    /**
     * the paddle speed.
     * @return the paddle speed.
     */
    int paddleSpeed();
    /**
     * the paddle width.
     * @return the paddle width.
     */
    int paddleWidth();
    /**
     * the level name.
     * will be displayed at the top of the screen.
     * @return a string of the level name.
     */
    String levelName();
    /**
     * Returns a sprite with the background of the level.
     * @return a sprite which with the background to the level.
     */
    Sprite getBackground();
    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     * @return list of blocks.
     */
    List<Block> blocks();
    /**
     * Number of levels that should be removed
     * before the level is considered to be "cleared".
     * @return number Of Blocks To Remove
     */
    int numberOfBlocksToRemove();
 }