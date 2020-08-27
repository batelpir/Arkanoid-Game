package graphics;
import java.util.List;
/**
 * @author Batel pirov.
 * This class represents a line which has an start point and an end point.
 * the line also has a middle point, his intersection with y axis and with other lines and more.
 */
public class Line {
    private Point start;
    private Point end;
    private Point middle;
    private double incline;
    private double intersectionWithY;
    /**
     * constructor.
     * @param start - the start point of the line.
     * @param end - the end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    /**
     * constructor.
     * @param x1 - the x value of 'start' point.
     * @param y1 - the y value of 'start' point.
     * @param x2 - the x value of 'end' point.
     * @param y2 - the y value of 'end' point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }
    /**
     * Calculating the length of this line.
     * @return the length of the line.
     */
    public double length() {
        // According to length formula.
        double square = ((this.start.getX() - this.end.getX()) * (this.start.getX() - this.end.getX())
                + (this.start.getY() - this.end.getY()) * (this.start.getY() - this.end.getY()));
        double root = Math.sqrt(square);
        return root;
    }
    /**
     * calculating the middle of the line.
     * @return the middle point of the line.
     */
    public Point middle() {
        double middleX = (this.start.getX() + this.end.getX()) / 2;
        double middleY = (this.start.getY() + this.end.getY()) / 2;
        this.middle = new Point(middleX, middleY);
        return this.middle;
    }
    /**
     * Calculating the line's incline.
     * @return the incline of the line.
     */
    public double incline() {
        // according to incline formula.
        this.incline = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());
        return this.incline;
    }
    /**
     * Calculating the intersection point with y axis.
     * @return the intersection point with y axis.
     */
    public double intersectionWithY() {
        this.intersectionWithY = this.start.getY() - this.incline() * this.start.getX();
        return this.intersectionWithY;
    }
    /**
     *  Returns the start point of the line.
     * @return the start point of the line.
     */
    public Point start() {
        return this.start;
    }
    /**
     *  Returns the end point of the line.
     * @return the end point of the line.
     */
    public Point end() {
        return this.end;
    }
    /**
     * Returns true if the lines intersect, false otherwise.
     * @param other - another line.
     * @return a boolean - true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        if (this.intersectionWith(other) == null) {
            return false;
        }
            return true;
    }
    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * @param other - another line.
     * @return the intersection point between two lines or null.
     */
    public Point intersectionWith(Line other) {
        // if both of the lines are vertical and with different x.
        if (this.start.getX() == this.end.getX() && other.start.getX() == other.end.getX()
                && this.start.getX() != other.start.getX()) {
            return null;
        }
        // the case both of the lines are vertical and with the same x.
        if (this.start.getX() == this.end.getX() && other.start.getX() == other.end.getX()) {
            double y1 = this.start.getY();
            double y2 = this.end.getY();
            double y3 = other.start.getY();
            double y4 = other.end.getY();
            double min1, min2, max1, max2, max, min, notMin, notMax;
            // finding y maximal and y minimal.
            if (y1 > y2) {
                min1 = y2;
                max1 = y1;
            } else {
                min1 = y1;
                max1 = y2;
            }
            if (y3 > y4) {
                min2 = y4;
                max2 = y3;
            } else {
                min2 = y3;
                max2 = y4;
            }
            if (min1 > min2) {
                min = min2;
                notMin = min1;
            } else {
                min = min1;
                notMin = min2;
            }
            if (max1 > max2) {
                max = max1;
                notMax = max2;
            } else {
                max = max2;
                notMax = max1;
            }
            // checking if there is a meeting point between the lines.
            if (this.length() + other.length() < (max - min)) {
                return null;
            } else {
                // finding intersection point.
                if (y1 == notMin || y1 == notMax) {
                    // return one point from the intersection.
                    Point intersection = new Point(this.start.getX(), y1);
                    return intersection;
                } else if (y2 == notMin || y2 == notMax) {
                    // return one point from the intersection.
                    Point intersection = new Point(this.end.getX(), y2);
                    return intersection;
                }
            }
        }
        // if one of the lines is vertical.
        if (this.start.getX() == this.end.getX()) {
            return intersactionOfVertical(other, this.start.getX());
        }
        if (other.start.getX() == other.end.getX()) {
            return intersactionOfVertical(other.start.getX(), other);
        }
        // now all the lines are not vertical.
        if (this.incline() == other.incline()) {
            if (this.intersectionWithY() != other.intersectionWithY()) {
                return null;
                // on the same line.
            } else {
                // find minimal x and maximal x.
                double x1 = this.start.getX();
                double x2 = this.end.getX();
                double x3 = other.start.getX();
                double x4 = other.end.getX();
                double min1, min2, max1, max2, max, min, notMin, notMax;
                if (x1 > x2) {
                    min1 = x2;
                    max1 = x1;
                } else {
                    min1 = x1;
                    max1 = x2;
                }
                if (x3 > x4) {
                    min2 = x4;
                    max2 = x3;
                } else {
                    min2 = x3;
                    max2 = x4;
                }
                if (min1 > min2) {
                    min = min2;
                    notMin = min1;
                } else {
                    min = min1;
                    notMin = min2;
                }
                if (max1 > max2) {
                    max = max1;
                    notMax = max2;
                } else {
                    max = max2;
                    notMax = max1;
                }
                // checking if there is a meeting point between the lines.
                if (this.length() + other.length() < (max - min)) {
                    return null;
                } else {
                    // finding intersection point.
                    if (x1 == notMin || x1 == notMax) {
                        Point intersection = new Point(x1, this.start.getY());
                        return intersection;
                    } else if (x2 == notMin || x2 == notMax) {
                        Point intersection = new Point(x2, this.end.getY());
                        return intersection;
                    }
                }
            }
        }
        // now all the lines are not vertical or parallel.
        // find the intersection point of two regular lines.
        double intersectionX = (this.intersectionWithY() - other.intersectionWithY())
                               / (other.incline() - this.incline());
        double intersectionY = this.incline() * intersectionX + this.intersectionWithY();
        // checking range.
        Point intersection =
                new Point(Math.round(intersectionX * 100.0) / 100.0, Math.round(intersectionY * 100.0) / 100.0);
        double distance1 = this.start.distance(intersection);
        double distance2 = this.end.distance(intersection);
        double distance3 = this.start.distance(this.end);
        double distance4 = other.start.distance(intersection);
        double distance5 = other.end.distance(intersection);
        double distance6 = other.start.distance(other.end);
        if (Math.abs(distance1 + distance2 - distance3) <= 0.01
                && Math.abs(distance4 + distance5 - distance6) <= 0.01) {
            return intersection;
        }
        return null;
        //return intersection;
    }
    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * taking care on the case which 'this' line is the vertical line.
     * @param other - another line to find intersection with.
     * @param verticalX - the x of the vertical line ('this' line).
     * @return the intersection point or null.
     */
    private Point intersactionOfVertical(Line other, double verticalX) {
        // checks if verticalX is between of the two x.
        if ((verticalX <= other.start.getX() || verticalX <= other.end.getX())
                && (verticalX >= other.start.getX() || verticalX >= other.end.getX())) {
            double y = other.incline() * verticalX + other.intersectionWithY();
            if (y < this.start.getY() && y < this.end.getY()) {
                return null;
            } else if (y > this.start.getY() && y > this.end.getY()) {
                return null;
            } else {
                Point intersaction = new Point(verticalX, y);
                return intersaction;
            }
        }
        return null;
    }
    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     * taking care on the case which 'other' line is the vertical line.
     * @param verticalX - the x of the vertical line.
     * @param other - another line to find intersection with ('other' line).
     * @return the intersection point or null.
     */
    private Point intersactionOfVertical(double verticalX, Line other) {
        // checks if verticalX is between of the two x.
        if ((verticalX <= this.start.getX() || verticalX <= this.end.getX())
                && (verticalX >= this.start.getX() || verticalX >= this.end.getX())) {
            double y = this.incline() * verticalX + this.intersectionWithY();
            if (y < other.start.getY() && y < other.end.getY()) {
                return null;
            } else if (y > other.start.getY() && y > other.end.getY()) {
                return null;
            } else {
                Point intersaction = new Point(verticalX, y);
                return intersaction;
            }
        }
        return null;
    }
    /**
     * return true is the lines are equal, false otherwise.
     * @param other - another line.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        // checks if the start and end points are equal one to each other.
        if (this.start.getX() == other.start.getX() && this.start.getY() == other.start.getY()
                && this.end.getX() == other.end.getX() && this.end.getY() == other.end.getY()) {
            return true;
        }
        // checks if start point is equal to end point.
        if (this.start.getX() == other.end.getX() && this.start.getY() == other.end.getY()
                && this.end.getX() == other.start.getX() && this.end.getY() == other.start.getY()) {
            return true;
        }
        return false;
    }
    /**
     *  return the closest intersection point to the start of the line,
     *  or null if this line does not intersect with the rectangle .
     * @param rect - the rectangle we search intersection with.
     * @return null or the closest intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        if (rect.intersectionPoints(this).isEmpty()) {
            return null;
        } else {
            List<Point> list = rect.intersectionPoints(this);
            if (list.size() == 1) {
                return list.get(0);
            }
            // the array has at most 2 points, this is the case is has 2 points.
            if (list.get(0).distance(this.start) < list.get(1).distance(this.start)) {
                return list.get(0);
            } else {
                return list.get(1);
            }
        }
    }
    /**
     * check if a point is on the line.
     * @param p - a point.
     * @return a boolean that tell if the point is on the line.
     */
    public boolean isOnLine(Point p) {
        // a point could be also a line.
        return this.isIntersecting(new Line(p, p));
    }
}
