package general;
import graphics.Point;
/**
 * @author batel pirov.
 * this class represents the velocity of the ball.
 * Velocity specifies the change in position on the `x` and the `y` axis.
 */
public class Velocity {
    private double dx;
    private double dy;
    /**
     * constructor.
     * @param dx - the progress on x axis.
     * @param dy - the progress on y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }
    /**
     * returns the progress on x axis.
     * @return the progress on x axis.
     */
    public double getDx() {
        return this.dx;
    }
    /**
     * returns the progress on y axis.
     * @return the progress on y axis.
     */
    public double getDy() {
        return this.dy;
    }
    /**
     * Take a point with position (x,y) and return a new point.
     * with position (x+dx, y+dy).
     * @param p - the point.
     * @return newPoint - the point which was given plus dx and dy.
     */
    public Point applyToPoint(Point p) {
        Point newPoint = new Point(p.getX() + dx, p.getY() + dy);
        return newPoint;
    }
    /**
     * A new constructor to velocity, by angle and speed.
     * @param angle - the angle.
     * @param speed - the given speed.
     * @return velocity - the velocity that the method created.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = speed * Math.sin(Math.toRadians(angle));
        double dy = -speed * Math.cos(Math.toRadians(angle));
        return new Velocity(dx, dy);
    }
}