package game;
import java.util.List;
import java.util.ArrayList;
import graphics.Point;
import graphics.Line;
import general.Collidable;
import general.CollisionInfo;
/**
 * @author batel pirov.
 * in this class we have all the information about the objects in the game.
 */
public class GameEnvironment {
    private List<Collidable> collidables;
    /**
     * constructor.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }
    /**
     * returns the list of the collidables.
     * @return the list of the collidables.
     */
    public List<Collidable> getGameEnvironment() {
        return this.collidables;
    }
    /**
     * add the given collidable to the environment.
     * @param c - an object of collidable type.
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }
    /**
     * remove the given collidable from the environment.
     * @param c - an object of collidable type.
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }
    /**
     * find the information about the closest collision that is going to occur.
     * @param trajectory - the line which the object move on it.
     * @return information about the collision or null if trajectory is not intersect with any of the collidables.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // list of the trajectory collision points.
        List<Point> collisionPoints = new ArrayList<Point>();
        for (Collidable c : collidables) {
            // if there is an intersection with some collidable:
            Point mayCollision = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (mayCollision != null) {
                // collect all the closest collision points into a list array.
                collisionPoints.add(mayCollision);
            }
        }
        // find the closest collision:
        if (!collisionPoints.isEmpty()) {
            double minDistant = trajectory.start().distance(collisionPoints.get(0));
            Point closestPoint = collisionPoints.get(0);
            for (Point p : collisionPoints) {
                if (trajectory.start().distance(p) < minDistant) {
                    minDistant = trajectory.start().distance(p);
                    closestPoint = p;
                }
            }
            // finding the object whose collision point is the closest point we found.
            Collidable colligionObject = null;
            for (Collidable c : collidables) {
                if (trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle()) != null) {
                    if (trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle()).equals(closestPoint)) {
                        colligionObject = c;
                    }
                }
            }
            return new CollisionInfo(closestPoint, colligionObject);
        }
        // the case there no collision:
        return null;
    }
}
