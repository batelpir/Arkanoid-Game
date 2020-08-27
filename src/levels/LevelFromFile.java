package levels;

import java.util.List;

import game.LevelInformation;
import general.Velocity;
import sprites.Block;
import sprites.Sprite;
/**
 * @author batel.
 * create a level info.
 */
public class LevelFromFile implements LevelInformation {
    private int numOfBalls; //count how much velocities.
    private List<Velocity> ballVelocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite background;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;
    /**
     * a constructor.
     * @param ballVelocities - a list of velocities.
     * @param paddleSpeed - the paddle speed.
     * @param paddleWidth - the paddle width.
     * @param levelName - the level name.
     * @param background - the background of the level.
     * @param blocks - the blocks of the level.
     * @param numBlocks - number of blocks.
     */
    public LevelFromFile(List<Velocity> ballVelocities, int paddleSpeed, int paddleWidth,
            String levelName, Sprite background, List<Block> blocks, int numBlocks) {
        this.ballVelocities = ballVelocities;
        this.numOfBalls = ballVelocities.size();
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.blocks = blocks;
        this.numberOfBlocksToRemove = numBlocks;
        this.background = background;
        this.levelName = levelName;
    }
    /**
     * returns the number of balls.
     * @return the number of balls.
     */
    public int numberOfBalls() {
        return this.numOfBalls;
    }
    /**
     * returns the list of ball velocities.
     * @return the list of ball velocities.
     */
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }
    /**
     * returns the paddle speed.
     * @return the paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }
    /**
     * returns the paddle width.
     * @return the paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }
    /**
     * returns the level name.
     * @return the level name.
     */
    public String levelName() {
        return this.levelName;
    }
    /**
     * returns the background.
     * @return the background.
     */
    public Sprite getBackground() {
        return this.background;
    }
    /**
     * returns the list of the block.
     * @return the list of the block.
     */
    public List<Block> blocks() {
        return this.blocks;
    }
    /**
     * returns the number of blocks.
     * @return the number of blocks.
     */
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }
}
