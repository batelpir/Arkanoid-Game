package levels;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.InputStream;
import biuoop.DrawSurface;
import sprites.Sprite;
/**
 * @author batel pirov.
 * get an image or color's string and converting them to a background to the level.
 */
public class BackgroundMaker implements Sprite {
    private boolean isImage;
    private boolean isColor;
    private Image img;
    private Color color;
    /**
     * constructor.
     * @param s - the string which represent the background.
     */
    public BackgroundMaker(String s) {
        this.isColor = false;
        this.isImage = false;
        if (s.startsWith("image")) {
          this.isImage = true;
          String[] parts = s.split("\\(");
          String imageName = parts[1].substring(0, parts[1].length() - 1);
          try {
              this.img = this.makeAnImage(imageName);
          } catch (IOException e) {
              System.out.println("Error: failed to load image");
          }
        } else {
            this.isColor = true;
            ColorsParser colorsParser = new ColorsParser();
            this.color = colorsParser.colorFromString(s);
        }
    }
    /**
     * get an image string and use it to create an image.
     * @param imageName - the image string.
     * @return an image.
     * @throws IOException - a possible exception.
     */
    private Image makeAnImage(String imageName) throws IOException {
        // load the image data into an java.awt.Image object
        Image image = null;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(imageName);
        try {
            image = ImageIO.read(is);
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
        return image;
    }
    /**
     * draw the image of the background.
     * @param d - the draw surface.
     */
    public void drawOn(DrawSurface d) {
        if (this.isImage) {
            d.drawImage(0, 0, img); // draw the image.
        } else {
            d.setColor(color);
            d.fillRectangle(0, 0, 800, 600);
        }
    }
    /**
     * do nothing.
     */
    public void timePassed() { }
}
