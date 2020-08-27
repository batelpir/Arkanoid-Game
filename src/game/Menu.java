package game;
/**
 * @author batel
 * a generic interface extends Animation interface.
 * @param <T>
 */
public interface Menu<T> extends Animation {
    /**
     * add an option to the select in the menu.
     * @param key - the key which pressed to choice the selection.
     * @param message - the message which displayed the selection.
     * @param returnVal - the task which going to run according the choice.
     */
    void addSelection(String key, String message, T returnVal);
    /**
     * return the appropriate task according to the key that was pressing.
     * @return a generic argument.
     */
    T getStatus();
    /**
     * add a sub menu to a menu.
     * @param key - the key which pressed to choice the sub menu.
     * @param message - the message which displayed the selection.
     * @param subMenu - the sub menu.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);
 }
