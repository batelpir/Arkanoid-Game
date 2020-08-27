package game;

import java.io.Serializable;
/**
 * @author batel.
 * has the information about player and his score.
 */
public class ScoreInfo implements Serializable {
    private String name;
    private int score;
   /**
    * constructor.
    * @param name - the name of the player.
    * @param score - the players score.
    */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * return the player name.
     * @return the player name.
     */
    public String getName() {
        return this.name;
    }
    /**
     * returns the players score.
     * @return the players score.
     */
    public int getScore() {
        return this.score;
    }
}
