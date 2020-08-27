package game;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import levels.LevelSpecificationReader;
/**
 * @author batel
 * the task which play the game.
 */
public class PlayGameTask implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> levels;
    private String path;
    /**
     * constructor.
     * @param gameFlow - run the levels in the game.
     * @param levels - the levels which run in the game.
     * @param path - the path that the levels read from.
     */
    public PlayGameTask(GameFlow gameFlow, List<LevelInformation> levels, String path) {
        this.gameFlow = gameFlow;
        this.levels = levels;
        this.path = path;
    }
    /**
     * read the levels from the path and run them.
     * @return null.
     */
    public Void run() {
        gameFlow.initializeScoreAndLives();
        Reader reader = null;
        this.levels = new ArrayList<LevelInformation>();
        InputStream is =
                ClassLoader.getSystemClassLoader().getResourceAsStream(path);
        try {
            reader = new InputStreamReader(is);
        } catch (Exception e) {
            System.out.println("cannot find the file");
        }
        LevelSpecificationReader levelSpesificationReader;
        try {
            levelSpesificationReader = new LevelSpecificationReader();
            this.levels = levelSpesificationReader.fromReader(reader);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.gameFlow.runLevels(levels);
        return null;
    }
}
