package graphics;
import java.util.List;
import java.util.ArrayList;
/**
 * @author Batel Pirov.
 * this class is represent the rectangle and the actions it can do.
 * this class also has a lot of information about the rectangle.
 */
public class Rectangle {
    private Point upperLeft;
    private Point downerLeft;
    private Point upperRight;
    private Point downerRight;
    private Line leftEdge;
    private Line rightEdge;
    private Line upperEdge;
    private Line downerEdge;
    private double width;
    private double height;

    // Create a new rectangle with location and width/height.
    /**
     * Create a new rectangle with location and width/height, and set more information.
     * @param upperLeft - the upper left point of the rectangle.
     * @param width - the rectangle width
     * @param height - the rectangle height.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.height = height;
        this.width = width;
        // and more information.
        this.downerLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        this.upperRight = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        this.downerRight = new Point(this.upperRight.getX(), this.downerLeft.getY());
        this.upperEdge = new Line(this.upperLeft, this.upperRight);
        this.downerEdge = new Line(this.downerLeft, this.downerRight);
        this.leftEdge = new Line(this.downerLeft, this.upperLeft);
        this.rightEdge = new Line(this.downerRight, this.upperRight);
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     * @param line - the line we find intersection with.
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> list = new ArrayList<Point>();
        // find intersection with the left edge.
        if (line.isIntersecting(this.leftEdge)) {
            list.add(line.intersectionWith(this.leftEdge));
        }
        // find intersection with the right edge.
        if (line.isIntersecting(this.rightEdge)) {
            list.add(line.intersectionWith(this.rightEdge));
        }
        // find intersection with the upper edge.
        if (line.isIntersecting(this.upperEdge)) {
            list.add(line.intersectionWith(this.upperEdge));
        }
        if (line.isIntersecting(this.downerEdge)) {
            list.add(line.intersectionWith(this.downerEdge));
        }
        // check if there two equals points in the list and create new array list.
        List<Point> checkedList = new ArrayList<Point>();
        for (Point point : list) {
            if (!checkedList.contains(point)) {
                checkedList.add(point);
            }
        }
        return checkedList;
    }
    /**
     * returns the width of the rectangle.
     * @return the width of the rectangle.
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * returns the height of the rectangle.
     * @return the width of the rectangle.
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * Returns the upper-left point of the rectangle.
     * @return the upper-left point of the rectangle.
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }
    /**
     * Returns the downer-left point of the rectangle.
     * @return the downer-left point of the rectangle.
     */
    public Point getDownerLeft() {
        return this.downerLeft;
    }
    /**
     * Returns the upper-right point of the rectangle.
     * @return the upper-right point of the rectangle.
     */
    public Point getUpperRight() {
        return this.upperRight;
    }
    /**
     * Returns the downer-right point of the rectangle.
     * @return the downer-right point of the rectangle.
     */
    public Point getDownerRight() {
        return this.downerRight;
    }
    /**
     * returns the upper edge of the rectangle.
     * @return the upper edge of the rectangle.
     */
    public Line getUpperEdge() {
        return this.upperEdge;
    }
    /**
     * returns the downer edge of the rectangle.
     * @return the downer edge of the rectangle.
     */
    public Line getDownerEdge() {
        return this.downerEdge;
    }
    /**
     * returns the right edge of the rectangle.
     * @return the right edge of the rectangle.
     */
    public Line getRightEdge() {
        return this.rightEdge;
    }
    /**
     * returns the left edge of the rectangle.
     * @return the left edge of the rectangle.
     */
    public Line getLeftEdge() {
        return this.leftEdge;
    }
 }