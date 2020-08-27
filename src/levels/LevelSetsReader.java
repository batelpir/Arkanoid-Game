package levels;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import game.GameFlow;
import game.LevelInformation;
import game.Menu;
import game.PlayGameTask;
import game.Task;
/**
 * @author batel.
 * has a method that reads the level sets.
 */
public class LevelSetsReader {
    /**
     * reads the level sets and add selection to sub- menu according it.
     * @param reader - the reader.
     * @param subMenu - the sub menu.
     * @param gf - the game flow.
     * @return sub menu.
     * @throws IOException - a possible exception.
     */
    public Menu<Task<Void>> fromReader(Reader reader, Menu<Task<Void>> subMenu, GameFlow gf) throws IOException {
        LineNumberReader lineNumberReader = null;
        lineNumberReader = new LineNumberReader(reader);
        String key = null;
        String name = null;
        String path = null;
        List<LevelInformation> levels = null;
        String line;
        try {
            // reading all the file's lines.
            while ((line = lineNumberReader.readLine()) != null) {
                // if the line is odd - key and name of choose:
                if (lineNumberReader.getLineNumber() % 2 != 0) {
                    String[] keyAndName = line.split(":");
                    key = keyAndName[0];
                    name = keyAndName[1];
                } else {
                    path = line;
                    levels = new ArrayList<>();
                    PlayGameTask pgt = new PlayGameTask(gf, levels, path);
                    subMenu.addSelection(key, name, pgt);
                }
            }
        } catch (IOException e) {
            System.out.println("cannot read the level sets");
        }
        return subMenu;
    }
}
