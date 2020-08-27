package graphics;
/**
 * @author Batel Pirov.
 * This class represents a point which has an x and a y value.
 */
public class Point {
    private double x;
    private double y;
    /**
     * constructor.
     * @param x - x value.
     * @param y - y value.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // distance -- return the distance of this point to the other point
    /**
     * Calculating the distance of this point to the other point.
     * @param other - another point.
     * @return root - the distance.
     */
    public double distance(Point other) {
        // According to distance formula.
        double square = ((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
        double root = Math.sqrt(square);
        return root;
    }
    /**
     * return true is the points are equal, false otherwise.
     * @param other - another point.
     * @return a boolean.
     */
    public boolean equals(Point other) {
        if (this.x == other.x && this.y == other.y) {
            return true;
        }
            return false;
    }
    /**
     * return the x value of this point.
     * @return the x value of this point.
     */
    public double getX() {
        return this.x;
    }
    /**
     * return the y value of this point.
     * @return the y value of this point.
     */
    public double getY() {
        return this.y;
    }
}
