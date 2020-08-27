package sprites;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import biuoop.DrawSurface;
import graphics.Rectangle;
import levels.ColorsParser;
import graphics.Point;
import listeners.HitListener;
import game.GameLevel;
import general.Velocity;
import general.Collidable;
/**
 * @author batel pirov.
 * this class implements the collidable and sprite interfaces.
 * it is also represents a block which have a shape, color and times it had collide.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private int counter;
    private Map<Integer, Color> colors;
    private Map<Integer, Image> images;
    private Color stroke;
    private List<HitListener> hitListeners;
    /**
     * constructor.
     * @param rectangle - is the shape of the block.
     * @param count - counter how many times the ball collided with the block.
     * @param fills - a map of possible fills to the blocks in one game.
     * @param stroke = the color of the stroke of the block.
     */
    public Block(Rectangle rectangle, int count,  Map<Integer, String> fills, Color stroke) {
        this.rectangle = rectangle;
        this.counter = count;
        this.colors = null;
        this.images = null;
        this.recognize(fills);
        this.stroke = stroke;
        this.hitListeners = new ArrayList<HitListener>();
    }
    /**
     * returns the rectangle of the block.
     * @return the rectangle of the block.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }
    /**
     * draws the block and his counter on the surface.
     * @param surface - the given DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int hight = (int) this.rectangle.getHeight();
        // if the fills is only colors.
        if (this.images == null) {
            if (this.counter > 1 && this.colors.containsKey(this.counter)) {
                surface.setColor(this.colors.get(this.counter));
                surface.fillRectangle(x, y, width, hight);
            } else {
                surface.setColor(this.colors.get(0)); // the regular fill.
                surface.fillRectangle(x, y, width, hight);
            }
        }
        // if the fills is only images.
        if (this.colors == null) {
            if (this.counter > 1 && this.images.containsKey(this.counter)) {
                surface.drawImage(x, y, this.images.get(this.counter)); // draw the image at location x, y.
            } else {
                surface.drawImage(x, y, this.images.get(0)); // draw the image at location x, y.
            }
        }
        // if the fills is also images and also colors:
        if (this.colors != null && this.images != null) {
            if (this.counter > 1
                    && (this.images.containsKey(this.counter) || this.colors.containsKey(this.counter))) {
                if (this.colors.containsKey(this.counter)) {
                    surface.setColor(this.colors.get(this.counter));
                    surface.fillRectangle(x, y, width, hight);
                } else {
                    surface.drawImage(x, y, this.images.get(this.counter)); // draw the image at location x, y.
                }
            } else {
                if (this.colors.containsKey(0)) {
                    surface.setColor(this.colors.get(0)); // the regular fill.
                    surface.fillRectangle(x, y, width, hight);
                } else {
                    surface.drawImage(x, y, this.images.get(0)); // draw the image at location x, y.
                }
            }
        }
        if (this.stroke != null) {
            surface.setColor(stroke);
            surface.drawRectangle(x, y, width, hight);
        }
        surface.setColor(Color.white);
        if (hight < 5) {
            return;
        }
        /*
        if (this.counter == 0) {
            surface.drawText(x + width / 2, y + hight / 2, "X", 10);
        } else {
            surface.drawText(x + width / 2, y + hight / 2, Integer.toString(this.counter), 10);
        }*/
    }
    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {
    }
    /**
     * add the block to be one of the sprites or the collidables of the game.
     * @param g - the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this); // now block is a part of the sprites.
        g.addCollidable(this); // now block is a part of the collidables.
    }
    /**
     * remove the block from to be one of the sprites or the collidables of the game.
     * @param game - the game
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this); // now block is not a part of the collidables.
        game.removeSprite(this); // now block is not a part of the sprites.
    }
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit.
     * @param hitter - the ball which hit.
     * @param collisionPoint - the collision Point of the ball and the block.
     * @param currentVelocity - the velocity of the ball before changing.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // Checking a collision with the corners.
        if (collisionPoint.equals(this.rectangle.getDownerLeft())
                || collisionPoint.equals(this.rectangle.getDownerRight())
                || collisionPoint.equals(this.rectangle.getUpperLeft())
                || collisionPoint.equals(this.rectangle.getUpperRight())) {
            this.notifyCounter();
            this.notifyHit(hitter);
            return  new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy() * -1);
        }
        // checking collisions with the block's edges.
        if (this.rectangle.getDownerEdge().isOnLine(collisionPoint)) {
            this.notifyCounter();
            this.notifyHit(hitter);
            return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        }
        if (this.rectangle.getLeftEdge().isOnLine(collisionPoint)) {
            this.notifyCounter();
            this.notifyHit(hitter);
            return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy());
        }
        if (this.rectangle.getUpperEdge().isOnLine(collisionPoint)) {
            this.notifyCounter();
            this.notifyHit(hitter);
            return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        }
        if (this.rectangle.getRightEdge().isOnLine(collisionPoint)) {
            this.notifyCounter();
            this.notifyHit(hitter);
            return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy());
        }
        return currentVelocity; // default.
    }
    /**
     * counting the times that a collision has occurred.
     */
    public void notifyCounter() {
        if (counter > 0) {
        this.counter = this.counter - 1;
        }
    }
    /**
     * notify all listeners about a hit event.
     * @param hitter - the ball which hit.
     */
    private void notifyHit(Ball hitter) {
       // Make a copy of the hitListeners before iterating over them.
       List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
       // Notify all listeners about a hit event:
       for (HitListener hl : listeners) {
          hl.hitEvent(this, hitter);
       }
    }
    /**
     * Add hl as a listener to hit events.
     * @param hl - the listener which added.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl - the listener which removed.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    /**
     * returns the number of times that has been left to remove the block.
     * @return the number of times that has been left to remove the block.
     */
    public int getHitPoints() {
        return this.counter;
    }
    /**
     * get an image's string and makes him be an image.
     * @param s - the string which represent the image.
     * @return an image.
     * @throws IOException - a possible exception.
     */
    private Image makeAnImage(String s) throws IOException {
        //String imageName = s.substring(s.indexOf("\\(") + 1, s.indexOf("\\)"));
        String[] parts = s.split("\\(");
        String imageName = parts[1].substring(0, parts[1].length() - 1);
        // load the image data into an java.awt.Image object.
        Image img = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("Error: failed to load image");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println("Failing closing the image");
                }
            }
        }
        return img;
    }
    /**
     * sorting the fills to colors map and images map according to the strings in fills map.
     * @param fills - map of strings that representing fills to blocks in one level.
     */
    private void recognize(Map<Integer, String> fills) {
        // check if the fill is a color or an image:
        for (String value : fills.values()) {
            if (value.startsWith("color")) {
                if (this.colors == null) {
                    this.colors = new TreeMap<>();
                }
                ColorsParser cp = new ColorsParser();
                Color currentColor = cp.colorFromString(value);
                for (Integer key : fills.keySet()) {
                    if (fills.get(key).equals(value)) {
                        this.colors.put(key, currentColor);
                    }
                }
            } else {
                if (this.images == null) {
                    this.images = new TreeMap<>();
                }
                try {
                    Image img = this.makeAnImage(value);
                    for (Integer key : fills.keySet()) {
                        if (fills.get(key).equals(value)) {
                            this.images.put(key, img);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("make an image was fail.");
                }
            }
        }
    }
    /**
     * return the width of the block.
     * @return the width of the block.
     */
    public int getWidth() {
        return ((int) this.rectangle.getWidth());
    }
}
