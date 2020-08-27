package game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author batel
 * Create an high-scores table with the specified size and the previous .
 */
public class HighScoresTable implements Serializable {
    private int sizeOfTable;
    private List<ScoreInfo> scores;
    /**
     * constructor.
     * @param size - the size of the table.
     */
    public HighScoresTable(int size) {
        this.sizeOfTable = size;
        this.scores = new ArrayList<ScoreInfo>();
    }
    /**
     * Add a high-score.
     * @param score - the score which added.
     */
    public void add(ScoreInfo score) {
        if (this.scores.isEmpty()) {
            this.scores.add(score);
        } else {
            int index = this.getRank(score.getScore());
            if (index <= this.scores.size()) {
                this.scores.add(index - 1, score);
                if (this.scores.size() > this.sizeOfTable) {
                    this.scores.remove(sizeOfTable);
                }
            } else {
                this.scores.add(score);
                if (this.scores.size() > this.sizeOfTable) {
                    this.scores.remove(sizeOfTable);
                }
            }
        }
    }
    /**
     * Return table size.
     * @return table size.
     */
    public int size() {
        return this.sizeOfTable;
    }
    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     * @return list of score info.
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }
    /**
     * return the rank of the current score.
     * @param score - the score.
     * @return the rank of the current score
     */
    public int getRank(int score) {
        for (int i = 1; i <= this.scores.size(); i++) {
            if (this.scores.get(i - 1).getScore() < score) {
                return i;
            }
        }
        return this.scores.size() + 1;
    }
    /**
     * Clears the table.
     */
    public void clear() {
        this.scores.clear();
    }
    /**
     * load the file of the high scores.
     * @param filename - the file.
     * @throws IOException - a possible io exception.
     */
    public void load(File filename) throws IOException {
        FileInputStream fileIn = null;
        ObjectInputStream objectInputStream = null;
        try {
            fileIn = new FileInputStream(filename);
            objectInputStream = new ObjectInputStream(fileIn);
            HighScoresTable table = (HighScoresTable) objectInputStream.readObject();
            this.sizeOfTable = table.size();
            this.scores = table.getHighScores();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) { // Some other problem
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.out.println("faild to close the file.");
            }
        }
    }
    /**
     * Save table data to the specified file.
     * @param filename - the file that it save into him.
     * @throws IOException - a possible io exception.
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                                   new FileOutputStream(filename.getName()));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            System.out.println("faild to write to the file.");
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed closing file: " + filename.getName());
            }
        }
    }
    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     * @param filename - the file to load from it.
     * @return a high score table.
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoreTable = new HighScoresTable(5);
        try {
            if (filename.exists()) {
                highScoreTable.load(filename);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return highScoreTable;
    }
}
