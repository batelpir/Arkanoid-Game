package game;
/**
 * @author batel
 * a generic interface.
 * @param <T> - a generic argument.
 */
public interface Task<T> {
    /**
     * run the task.
     * @return a generic argument.
     */
    T run();
}
