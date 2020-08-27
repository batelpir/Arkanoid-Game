package game;
/**
 * @author batel
 * this task is running the high scores, and display it on the screen.
 */
public class ShowHiScoresTask implements Task<Void> {
    private AnimationRunner runner;
    private Animation highScoresAnimation;
    /**
     * constructor.
     * @param runner - runner to run the high score animation.
     * @param highScoresAnimation - the high score animation.
     */
    public ShowHiScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
       this.runner = runner;
       this.highScoresAnimation = highScoresAnimation;
    }
    /**
     * run the high score animation.
     * @return null.
     */
    public Void run() {
       this.runner.run(this.highScoresAnimation);
       return null;
    }
 }
