package levels;

import java.awt.Color;
import graphics.Rectangle;
import graphics.Point;
import java.util.Map;

import sprites.Block;
/**
 * @author batel.
 * a class which implements Block Creator interface.
 * create a block according to the given data.
 */
public class CreateABlock implements BlockCreator {
    private int width;
    private Color stroke;
    private Map<Integer, String> fills;
    private int height;
    private int hitPoints;
    /**
     * a constructor.
     * @param width - the width of the block.
     * @param stroke - the stroke of the block.
     * @param fills - one of the fills is the fill of the block.
     * @param height - the height of the block.
     * @param hitPoints - the number of hits the block can get.
     */
    public CreateABlock(int width, Color stroke, Map<Integer, String> fills, int height, int hitPoints) {
        this.width = width;
        this.stroke = stroke;
        this.height = height;
        this.fills = fills; // fills in key 0 is the regular fill.
        this.hitPoints = hitPoints;
    }
    /**
     * create the block.
     * @param xpos - the upper left x.
     * @param ypos - the upper left y.
     * @return a block.
     */
    public Block create(int xpos, int ypos) {
        Rectangle rec = new Rectangle(new Point(xpos, ypos), this.width, this.height);
        Block block = new Block(rec, this.hitPoints, this.fills, this.stroke);
        return block;
    }
}
