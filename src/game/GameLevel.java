package game;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import graphics.Rectangle;
import graphics.Point;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;
import sprites.Paddle;
import sprites.SpriteCollection;
import sprites.Block;
import sprites.Sprite;
import sprites.ScoreIndicator;
import sprites.LivesIndicator;
import sprites.NameOfLevel;
import sprites.Ball;
import general.Counter;
import general.Collidable;
import general.Velocity;
/**
 * @author Batel pirov
 *  this class is responsible on the game.
 *  it initializes all the objects that part of the game and then run the game.
 */
public class GameLevel implements Animation {
    private LevelInformation levelInfo;
    private KeyboardSensor keyboard;
    private Paddle paddle;
    private Counter remainedBlock;
    private Counter numOfBalls;
    private Counter score;
    private Counter numOfLives;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private AnimationRunner runner;
    private boolean running;
    /**
     * constructor.
     * @param levelInfo - the level it run.
     * @param keyboard - key board sensor.
     * @param runner - the loop for running.
     * @param score - the score the game starts with.
     * @param lives - number of lives the game starts with.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard,
            AnimationRunner runner, Counter score, Counter lives) {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainedBlock = new Counter(0);
        this.numOfBalls = new Counter(0);
        this.score = score;
        this.numOfLives = lives;
        this.levelInfo = levelInfo;
        this.runner = runner;
        this.keyboard = keyboard;
    }
    /**
     * adding a collidable to the collidables list.
     * @param c - an object of type collidable.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }
    /**
     * remove a collidable from the collidables list.
     * @param c - an object of type collidable.
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }
    /**
     * adding a sprite to the sprites list.
     * @param s - an object of type sprite.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }
    /**
     * remove a sprite from the sprites list.
     * @param s - an object of type sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * returns the number of the remained block in the game.
     * @return - the number of the remained block in the game.
     */
    public Counter getRemainedBlocks() {
        return this.remainedBlock;
    }

    /**
     * returns the current number of lives in the game.
     * @return the current number of lives in the game.
     */
    public Counter getNumOfLives() {
        return this.numOfLives;
    }

    /**
     * Initialize a new game: create the Blocks and Ball and Paddle and add them to
     * the game.
     */
    public void initialize() {
        LevelInformation myInfo = this.levelInfo;
        // add the background as to the sprite list:
        this.addSprite(myInfo.getBackground());
        for (Block block : myInfo.blocks()) {
            block.addToGame(this);
            this.remainedBlock.increase(1);
            BlockRemover remover = new BlockRemover(this, this.remainedBlock);
            block.addHitListener(remover);
            ScoreTrackingListener st = new ScoreTrackingListener(this.score);
            block.addHitListener(st);
        }
        // for the sides:
        Rectangle recRight = new Rectangle(new Point(0, 40), 20, 760);
        Map<Integer, String> colorMap = new TreeMap<>();
        colorMap.put(0, "color(gray)");
        Block blockRight = new Block(recRight, 0, colorMap, Color.black);
        blockRight.addToGame(this);
        Rectangle recLeft = new Rectangle(new Point(780, 40), 20, 760);
        Block blockLeft = new Block(recLeft, 0, colorMap, Color.black);
        blockLeft.addToGame(this);
        Rectangle recUp = new Rectangle(new Point(0, 20), 800, 20);
        Block blockUp = new Block(recUp, 0, colorMap, Color.black);
        blockUp.addToGame(this);
        // a rectangle to print score, lives and name of level on it.
        Rectangle siRec = new Rectangle(new Point(0, 0), 800, 20);
        ScoreIndicator scoreSprite = new ScoreIndicator(score, siRec);
        this.addSprite(scoreSprite);
        LivesIndicator livesSprite = new LivesIndicator(numOfLives, siRec);
        this.addSprite(livesSprite);
        NameOfLevel currName = new NameOfLevel(this.levelInfo.levelName(), siRec);
        this.addSprite(currName);
        // a block to remove balls.
        Rectangle recDeath = new Rectangle(new Point(20, 598), 760, 2);
        Block blockDeathSpace = new Block(recDeath, 0, colorMap, Color.black);
        blockDeathSpace.addToGame(this);
        BallRemover remover = new BallRemover(this, this.numOfBalls);
        blockDeathSpace.addHitListener(remover);
    }
    /**
     * creates balls and a paddle for run the game.
     */
    private void createBallsAndAPaddle() {
        LevelInformation myInfo = this.levelInfo;
        int width = this.levelInfo.paddleWidth();
        // create a paddle.
        Rectangle rect = new Rectangle(new Point(400 - width / 2, 570), width, 13);
        Paddle p = new Paddle(rect, Color.yellow, this.keyboard, myInfo.paddleSpeed());
        p.addToGame(this);
        this.paddle = p;
        // create new balls.
        for (int i = 0; i < myInfo.numberOfBalls(); i++) {
            Ball ball = new Ball(400, 550, 5, java.awt.Color.orange, environment);
            Velocity v = myInfo.initialBallVelocities().get(i);
            ball.setVelocity(v);
            ball.addToGame(this); // adding ball to sprites array.
            this.numOfBalls.increase(1);
        }
    }
    /**
     * Notify the time the running of game level should stop.
     * @return a boolean.
     */
    public boolean shouldStop() {
        return !this.running;
    }
    /**
     * the logic from the previous playOneTurn.
     * @param d - the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
        LevelInformation myInfo = this.levelInfo;
        myInfo.getBackground().drawOn(d);
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        if (this.numOfBalls.getValue() == 0) {
            // remove the paddle of the current run:
            this.removeCollidable(paddle);
            this.removeSprite(paddle);
            this.running = false;
        }
        if (myInfo.blocks().size() - myInfo.numberOfBlocksToRemove() == this.remainedBlock.getValue()) {
            this.score.increase(100);
            this.running = false;
        }
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard, "space", new PauseScreen()));
        }
    }
    /**
     * Run one game - start the animation loop.
     */
    public void playOneTurn() {
        this.createBallsAndAPaddle();
        this.runner.run(new CountdownAnimation(2.0, 3, this.sprites)); // countdown before turn starts.
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }
}
