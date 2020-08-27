package levels;

import java.util.Map;

import sprites.Block;
/**
 * @author batel.
 * has the information about the symbols which gave.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;
    /**
     * constructor.
     * @param spacerWidths - the map of the width.
     * @param blockCreators - the block creator map.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.blockCreators = blockCreators;
        this.spacerWidths = spacerWidths;
    }
    /**
     * returns true if 's' is a valid space symbol.
     * @param s - the space symbol.
     * @return a boolean.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }
    /**
     * returns true if 's' is a valid block symbol.
     * @param s - block symbol.
     * @return a boolean.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }
    /**
     * Return a block according to the definitions associated
     * with symbol s. The block will be located at position (xpos, ypos).
     * @param s - block symbol.
     * @param xpos - the upper left x.
     * @param ypos - the upper left y.
     * @return a block.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }
    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     * @param s - space symbol.
     * @return the width according the symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}
