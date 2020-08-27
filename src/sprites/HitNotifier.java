package sprites;

import listeners.HitListener;
/**
 * @author batel pirov.
 * Interface that has the following methods.
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl - the listener which added.
     */
    void addHitListener(HitListener hl);
    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl - the listener which removed.
     */
    void removeHitListener(HitListener hl);
 }