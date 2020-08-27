package game;

import java.io.File;
import java.io.IOException;
import java.util.List;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import general.Counter;
/**
 * @author batel pirov.
 * responsible for running the levels.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyBoard;
    private Counter score;
    private Counter lives;
    private HighScoresTable table;
    private DialogManager dialog;
    /**
     * constructor.
     * @param table - high score table.
     * @param ar - animation runner.
     * @param ks - key board sensor.
     * @param dialog - dialog manager.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, HighScoresTable table, DialogManager dialog) {
        this.animationRunner = ar;
        this.keyBoard = ks;
        this.score = new Counter(0);
        this.lives = new Counter(7); // start from 7 lives.
        this.table = table;
        this.dialog = dialog;
    }
    /**
     * run the levels that in the list.
     * @param levels - list of levels to run.
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level =
                    new GameLevel(levelInfo, this.keyBoard, this.animationRunner, this.score, this.lives);
            level.initialize();
            while (level.getRemainedBlocks().getValue() > 0 && this.lives.getValue() > 0) {
                level.playOneTurn();
                //checks if the turn stoped from the reason there are not blocks in the level anymore.
                if (level.getRemainedBlocks().getValue() != 0) {
                    this.lives.decrease(1);
                }
            }
            // the player lose.
            if (this.lives.getValue() < 1) {
                this.enterToTable();
                this.animationRunner.run(new KeyPressStoppableAnimation(this.keyBoard,
                        KeyboardSensor.SPACE_KEY, new EndScreen("Game Over. Your score is ", this.score)));
                break;
            }
        }
        // the player won.
        if (this.lives.getValue() > 0) {
            this.enterToTable();
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyBoard,
                    KeyboardSensor.SPACE_KEY, new EndScreen("You Win! Your score is ", this.score)));
        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyBoard,
                KeyboardSensor.SPACE_KEY, new HighScoresAnimation(this.table)));
    }
    /**
     * enter the player to the high score table.
     */
    private void enterToTable() {
        List<ScoreInfo> scoreList = this.table.getHighScores();
        if (scoreList.size() < this.table.size()
                || this.score.getValue() > scoreList.get(scoreList.size() - 1).getScore()) {
            //ask the player for his name:
            String name = this.dialog.showQuestionDialog("Name", "What is your name?", "");
            // add him to the table.
            this.table.add(new ScoreInfo(name, this.score.getValue()));
        }
        try {
            this.table.save(new File("highscores"));
        } catch (IOException e) {
            System.err.println("beaaya");
        }
    }
    /**
     * return the score and lives to their previous value for the next game.
     */
    public void initializeScoreAndLives() {
        this.lives = new Counter(7);
        this.score = new Counter(0);
    }
 }