package sprites;

import biuoop.DrawSurface;
import graphics.Point;
import graphics.Line;
import graphics.Rectangle;
import game.GameLevel;
import general.Velocity;
import general.Collidable;
import game.GameEnvironment;
/**
 * @author batel pirov.
 * This class represents a ball. it defined his velocity, size,
 * his center point and also his boundaries.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private Point pStart;
    private Point pEnd;
    private GameEnvironment gameEnvironment;
    /**
     * constructor.
     * @param center - the center of the ball
     * @param r - radius.
     * @param color - the ball's color.
     * @param gameEnvironment - gameEnvironment needs to know about the ball.
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }
    /**
     * constructor.
     * @param x - x value of the center of the ball.
     * @param y - y value of the center of the ball.
     * @param r - radius.
     * @param color - the ball's color.
     * @param gameEnvironment - gameEnvironment needs to know about the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color, GameEnvironment gameEnvironment) {
        Point p = new Point(x, y);
        this.center = p;
        this.r = r;
        this.color = color;
        this.gameEnvironment = gameEnvironment;
    }
    /**
     * to access the x value of the center point.
     * @return the x value of the center point.
     */
    public double getX() {
        return this.center.getX();
    }
    /**
     * to access the y value of the center point.
     * @return the y value of the center point.
     */
    public double getY() {
        return this.center.getY();
    }
    /**
     * to access the size of the ball.
     * @return the radius of the ball.
     */
    public int getSize() {
        return this.r;
    }
    /**
     * to access the color of the ball.
     * @return the color of the ball.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**
     * set the boundaries of the screen which the ball going to bounce in.
     * @param pointStart - the start point of the screen.
     */
    public void setPStart(Point pointStart) {
        this.pStart = pointStart;
    }
    /**
     * set the boundaries of the screen which the ball going to bounce in.
     * @param pointEnd - the end point of the screen.
     */
    public void setPEnd(Point pointEnd) {
        this.pEnd = pointEnd;
    }
    /**
     * another way to set the boundaries of the screen.
     * @param x - the x value of the start point that set the boundaries.
     * @param y - the y value of the start point that set the boundaries.
     */
    public void setPStart(int x, int y) {
        Point point = new Point(x, y);
        this.pStart = point;
    }
    /**
     * another way to set the boundaries of the screen.
     * @param x - the x value of the end point that set the boundaries.
     * @param y - the y value of the end point that set the boundaries.
     */
    public void setPEnd(int x, int y) {
        Point point = new Point(x, y);
        this.pEnd = point;
    }
    /**
     * draw the ball on the given DrawSurface.
     * @param surface - the given DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), r);
    }
    /**
     * move one step.
     */
    public void timePassed() {
      this.moveOneStep();
    }
    /**
     * add that ball to this game sprite collection.
     * @param g - the game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this); // now ball is a part of the sprites.
    }
    /**
     * remove that ball from this game sprite collection.
     * @param game - the game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this); // now ball is not a part of the sprites.
    }
    /**
     * set the velocity of the ball.
     * @param v - the velocity of the ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }
    /**
     * another way to set the velocity of the ball.
     * @param dx - the progress on x axis.
     * @param dy  the progress on y axis.
     */
    public void setVelocity(double dx, double dy) {
        Velocity v = new Velocity(dx, dy);
        this.velocity = v;
    }
    /**
     * return the velocity of the ball.
     * @return the velocity of the ball.
     */
    public Velocity getVelocity() {
        return this.velocity;
    }
    /**
     * This method changes the direction of the ball if it collides in a collidable object.
     * it also set a new center and direction to the ball.
     */
    public void moveOneStep() {
        Line trajectory = new Line(this.center, this.velocity.applyToPoint(this.center));
        // if there no collisions.
        if (this.gameEnvironment.getClosestCollision(trajectory) == null) {
            this.center = this.getVelocity().applyToPoint(this.center);
        } else {
            int changed = 0; // flag.
            Point collisionPoint = this.gameEnvironment.getClosestCollision(trajectory).collisionPoint();
            double collisionX = collisionPoint.getX();
            double collisionY = collisionPoint.getY();
            Collidable collisionObject = this.gameEnvironment.getClosestCollision(trajectory).collisionObject();
            Rectangle colObjectRect = collisionObject.getCollisionRectangle();
            //move the ball to "almost" the hit point.
            // for the corners:
            if (collisionPoint.equals(colObjectRect.getUpperLeft())) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX - this.r, collisionY - this.r);
                changed = 1;
            }
            if (collisionPoint.equals(colObjectRect.getUpperRight())) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX + this.r, collisionY - this.r);
                changed = 1;
            }
            if (collisionPoint.equals(colObjectRect.getDownerLeft())) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX - this.r, collisionY + this.r);
                changed = 1;
            }
            if (collisionPoint.equals(colObjectRect.getDownerRight())) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX + this.r, collisionY + this.r);
                changed = 1;
            }
            // to the remaining points:
            if (collisionX == colObjectRect.getUpperLeft().getX() && changed != 1) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX - this.r, collisionY);
            }
            if (collisionX == colObjectRect.getUpperRight().getX() && changed != 1) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX + this.r, collisionY);
            }
            if (collisionY == colObjectRect.getUpperLeft().getY() && changed != 1) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX, collisionY - this.r);
            }
            if (collisionY == colObjectRect.getDownerLeft().getY() && changed != 1) {
                this.setVelocity(collisionObject.hit(this, collisionPoint, this.velocity));
                this.center = new Point(collisionX, collisionY + this.r);
            }
        }
    }
}
