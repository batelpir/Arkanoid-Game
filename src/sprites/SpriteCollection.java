package sprites;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
/**
 * @author Batel Pirov.
 * this class is responsible about the sprites and what to do with them.
 */
public class SpriteCollection {
    private List<Sprite> sprites;
    /**
     * constructor.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }
    /**
     * add a sprite to the sprites list.
     * @param s - a sprite.
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }
    /**
     * remove a sprite from the sprites list.
     * @param s - a sprite.
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }
    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> sprites2 = new ArrayList<>(this.sprites);
        for (Sprite s : sprites2) {
            s.timePassed();
        }
    }
    /**
     * call drawOn(d) on all sprites.
     * @param d - the surface.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}