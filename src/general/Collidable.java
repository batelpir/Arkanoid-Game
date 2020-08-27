package general;
import graphics.Rectangle;
import sprites.Ball;
import graphics.Point;
/**
 * @author batel pirov
 * Interface class that has the following methods.
 */
public interface Collidable {
    /**
     * returns the "collision shape" of the object.
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();
    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity,
     * and return a new velocity.
     * @param hitter - the ball that hit.
     * @param collisionPoint - the collision Point of the ball and the object.
     * @param currentVelocity - the velocity of the ball before changing.
     * @return a new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
