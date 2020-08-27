package levels;

import sprites.Block;
/**
 * @author batel.
 * block creator interface.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     * @param xpos - the x position of upper left.
     * @param ypos - the y position of upper left.
     * @return a block.
     */
    Block create(int xpos, int ypos);
}
