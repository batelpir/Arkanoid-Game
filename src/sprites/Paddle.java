package sprites;

import java.awt.Color;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import graphics.Point;
import graphics.Rectangle;
import game.GameLevel;
import general.Velocity;
import general.Collidable;
/**
 * @author batel pirov.
 * this class implements the sprite and collidable interfaces.
 * it also represent a paddle which have a shape, color and more information.
 * the paddle moves from side to side in the screen.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;
    private java.awt.Color color;
    private double upperEdgeLength;
    private int speed;
    /**
     * constructor.
     * @param rectangle - paddles shape.
     * @param color - the color of the paddle.
     * @param keyBoard - the key board sensor to move the paddle.
     * @param speed - the speed of the paddle.
     */
    public Paddle(Rectangle rectangle, java.awt.Color color, biuoop.KeyboardSensor keyBoard, int speed) {
        this.rectangle = rectangle;
        this.color = color;
        this.keyboard = keyBoard;
        this.upperEdgeLength = this.rectangle.getUpperLeft().distance(this.rectangle.getUpperRight());
        this.speed = speed;
    }
    /**
     * make the paddle move to the left.
     */
    public void moveLeft() {
        double x = this.rectangle.getUpperLeft().getX();
        x = x - this.speed;
        // check limits.
        if (x >= 20) {
            this.rectangle = new Rectangle(new Point(x, this.rectangle.getUpperLeft().getY()) ,
                    this.rectangle.getWidth(), this.rectangle.getHeight());
        }
    }
    /**
     * make the paddle move to the right.
     */
    public void moveRight() {
        double x = this.rectangle.getUpperLeft().getX();
        x = x + this.speed;
        // check limits.
        if (x <= 780 - this.upperEdgeLength) {
            this.rectangle = new Rectangle(new Point(x, this.rectangle.getUpperLeft().getY()) ,
                    this.rectangle.getWidth(), this.rectangle.getHeight());
        }
    }
    /**
     * notify the paddle that time has passed.
     * in this case, we check if left or right keys were pressed
     * and moving the paddle accordingly.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
          }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
          }
    }
    /**
     * draw the paddle on the surface d.
     * @param d - a surface.
     */
    public void drawOn(DrawSurface d) {
        int x = (int) this.rectangle.getUpperLeft().getX();
        int y = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int hight = (int) this.rectangle.getHeight();
        d.setColor(this.color);
        d.fillRectangle(x, y, width, hight);
        d.setColor(Color.black);
        d.drawRectangle(x, y, width, hight);
    }
    /**
     * returns paddles shape.
     * @return paddles shape (his rectangle).
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit.
     * @param hitter - the ball which hit the paddle.
     * @param collisionPoint - the collision Point of the ball and the paddle.
     * @param currentVelocity - the velocity of the ball before changing.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double oneRegion = this.upperEdgeLength / 5;
        double xUpperLeft = this.rectangle.getUpperLeft().getX();
        double yUpperLeft = this.rectangle.getUpperLeft().getY();
        // calculating the speed using pythagoras sentence.
        double currentSpeed = Math.sqrt(currentVelocity.getDx() * currentVelocity.getDx()
                + currentVelocity.getDy() * currentVelocity.getDy());
        // calculating velocities according to location:
        if (Math.abs(collisionPoint.getX() - xUpperLeft) <= 0.001) {
            Velocity v = Velocity.fromAngleAndSpeed(300, currentSpeed);
            return v;
        } else if (collisionPoint.getX() >= xUpperLeft && collisionPoint.getX() < (xUpperLeft + oneRegion)
                        && collisionPoint.getY() == yUpperLeft) {
                   Velocity v = Velocity.fromAngleAndSpeed(300, currentSpeed);
                   return v;
        } else if (collisionPoint.getX() >= (xUpperLeft + oneRegion)
                        && collisionPoint.getX() < (xUpperLeft + 2 * oneRegion)
                        && collisionPoint.getY() == yUpperLeft) {
                   Velocity v = Velocity.fromAngleAndSpeed(330, currentSpeed);
                   return v;
        } else if (collisionPoint.getX() >= (xUpperLeft + 2 * oneRegion)
                        && collisionPoint.getX() < (xUpperLeft + 3 * oneRegion)
                        && collisionPoint.getY() == yUpperLeft) {
                   return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        } else if (collisionPoint.getX() >= (xUpperLeft + 3 * oneRegion)
                        && collisionPoint.getX() < (xUpperLeft + 4 * oneRegion)
                        && collisionPoint.getY() == yUpperLeft) {
                   Velocity v = Velocity.fromAngleAndSpeed(30, currentSpeed);
                   return v;
        } else if (collisionPoint.getX() >= (xUpperLeft + 4 * oneRegion)
                        && collisionPoint.getX() <= (xUpperLeft + 5 * oneRegion)
                        && collisionPoint.getY() == yUpperLeft) {
                   Velocity v = Velocity.fromAngleAndSpeed(60, currentSpeed);
                   return v;
        } else if (Math.abs(collisionPoint.getX() - (xUpperLeft + 5 * oneRegion)) <= 0.001) {
                   Velocity v = Velocity.fromAngleAndSpeed(60, currentSpeed);
                   return v;
        }
        // for the other edges:
        if (this.rectangle.getRightEdge().isOnLine(collisionPoint)) {
            return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy());
        }
        if (this.rectangle.getDownerEdge().isOnLine(collisionPoint)) {
            return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        }
        if (this.rectangle.getLeftEdge().isOnLine(collisionPoint)) {
            return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy());
        }
        return currentVelocity; //default.
    }
    /**
     * Add this paddle to the game.
     * @param g - our game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this); // now paddle is a part of the sprites.
        g.addCollidable(this); // now paddle is a part of the collidables.
    }
 }
