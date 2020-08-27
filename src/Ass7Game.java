import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.AnimationRunner;
import game.GameFlow;
import game.KeyPressStoppableAnimation;
import game.Menu;
import game.MenuAnimation;
import game.ShowHiScoresTask;
import game.Task;
import game.HighScoresAnimation;
import game.HighScoresTable;
import levels.LevelSetsReader;
/**
 * @author batel pirov.
 * a main to run our game.
 */
public class Ass7Game {
    /**
     * run all the program.
     * if there are an argument in "args" array, the argument would be a path to set levels.
     * if there isn't an argument in "args" array, the level set would be the default.
     * @param args - not used.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("game", 800, 600);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(60, gui);
        DialogManager dialog = gui.getDialogManager();
        File file = new File("highscores");
        HighScoresTable table = HighScoresTable.loadFromFile(file);
        try {
            table.save(file);
        } catch (IOException e) {
            System.err.println("beaaya");
        }
        GameFlow gf = new GameFlow(runner, keyboard, table, dialog);
        Task<Void> quitTask = new Task<Void>() {
            public Void run() {
              System.exit(1);
               return null;
            }
        };
        // create the sub menu:
        Reader reader = null;
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("Level Sets", keyboard, runner);
        InputStream is = null;
        if (args.length == 0) {
            // read the default file:
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("level_sets.txt");
        } else {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
        }
        try {
            reader = new InputStreamReader(is);
        } catch (Exception e) {
            System.out.println("cannot find the file");
        }
        LevelSetsReader levelSetsReader;
        try {
            levelSetsReader = new LevelSetsReader();
            subMenu = levelSetsReader.fromReader(reader, subMenu, gf);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // create a menu.
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid", keyboard, runner);
        KeyPressStoppableAnimation hsa = new KeyPressStoppableAnimation(keyboard,
                KeyboardSensor.SPACE_KEY, new HighScoresAnimation(table));
        menu.addSelection("h", "High scores", new ShowHiScoresTask(runner, hsa));
        menu.addSelection("q", "Quit", quitTask);
        menu.addSubMenu("s", "Start game", subMenu);
        while (true) {
           runner.run(menu);
           // wait for user selection
           Task<Void> task = menu.getStatus();
           task.run();
           gf.initializeScoreAndLives();
           hsa.initializeStop();
           // return menu's 'stop' parameter to be false again so it can run again.
           ((MenuAnimation<Task<Void>>) menu).initializeStop();
        }
    }
}
