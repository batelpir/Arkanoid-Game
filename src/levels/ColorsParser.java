package levels;

import java.awt.Color;
import java.lang.reflect.Field;
/**
 * @author batel.
 * get some string of color and make according it a real color.
 */
public class ColorsParser {
    /**
     * parse color definition and return the specified color.
     * @param s - the color's string.
     * @return a color.
     */
    public Color colorFromString(String s) {
        String[] parts = s.split("\\(");
        // if it is RGB:
        if (parts.length == 3) {
            String part3 = parts[2];
            String[] parts2 = part3.split("\\)");
            return this.colorFromRGB(parts2[0]);
        }
        String[] parts2 = parts[1].split("\\)");
        return this.colorFromColorName(parts2[0]);
    }
    /**
     * @param indexes - the indexes of RGB color.
     * @return a color.
     */
    private Color colorFromRGB(String indexes) {
        String[] parts = indexes.split(",");
        int index1 = Integer.parseInt(parts[0]);
        int index2 = Integer.parseInt(parts[1]);
        int index3 = Integer.parseInt(parts[2]);
        return new Color(index1, index2, index3);
    }
    /**
     * convert the given color's string to the specified color.
     * @param s - the given string.
     * @return the color.
     */
    private Color colorFromColorName(String s) {
        try {
            Field field = Color.class.getField(s);
            return (Color) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed parsing the color");
        }
    }
}