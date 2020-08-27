package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.MenuBackground;
/**
 * @author batel
 * the animation of the menu.
 * @param <T> - a generic parameter.
 */
public class MenuAnimation<T> implements Menu<Task<Void>> {
    private String menuTitle;
    private List<String> keys;
    private List<String> messages;
    private List<Task<Void>> returnVals;
    private List<String> keysOfSubMenu;
    private List<String> messagesOfSubMenu;
    private List<Menu<Task<Void>>> subMenus;
    private KeyboardSensor sensor;
    private AnimationRunner runner;
    private boolean stop;
    private int statusIndex;
    /**
     * constructor.
     * @param menuTitle - the title of the menu.
     * @param sensor - the keyboard sensor.
     * @param runner - run the menu.
     */
    public MenuAnimation(String menuTitle, KeyboardSensor sensor, AnimationRunner runner) {
        this.keys = new ArrayList<String>();
        this.messages = new ArrayList<String>();
        this.returnVals = new ArrayList<Task<Void>>();
        this.keysOfSubMenu = new ArrayList<String>();
        this.messagesOfSubMenu = new ArrayList<String>();
        this.subMenus = new ArrayList<Menu<Task<Void>>>();
        this.sensor = sensor;
        this.runner = runner;
        this.stop = false;
        this.menuTitle = menuTitle;
    }
    /**
     * draw the menu options to the surface and check which key is pressed.
     * @param d - the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
        MenuBackground background = new MenuBackground();
        background.drawOn(d);
        d.setColor(new Color(255, 4, 104));
        d.drawText(200, 150, this.menuTitle, 100);
        // the size in the for loop could be the size of any of the lists,
        // that because they have the same size.
        d.setColor(new Color(220, 124, 162));
        for (int i = 0; i < keys.size(); i++) {
            d.drawText(245, 250 + i * 36, this.messages.get(i), 30);
            d.drawText(205, 250 + i * 36, "(" + this.keys.get(i) + ") ", 30);
        }
        for (int i = 0; i < this.keysOfSubMenu.size(); i++) {
            d.drawText(245, 214 + i * 36, this.messagesOfSubMenu.get(i), 30);
            d.drawText(205, 214 + i * 36, "(" + this.keysOfSubMenu.get(i) + ") ", 30);
        }
        for (int i  = 0; i < this.keys.size(); i++) {
            if (this.sensor.isPressed(this.keys.get(i))) {
                this.statusIndex = i;
                this.stop = true;
            }
        }
        for (int i = 0; i < this.keysOfSubMenu.size(); i++) {
            if (this.sensor.isPressed(this.keysOfSubMenu.get(i))) {
                Menu<Task<Void>> subMenu = this.subMenus.get(i);
                this.runner.run(subMenu);
                subMenu.getStatus().run();
                ((MenuAnimation<Task<Void>>) subMenu).initializeStop();
            }
        }
    }
    /**
     * tell when to stop the animation.
     * @return a boolean.
     */
    public boolean shouldStop() {
        return this.stop;
    }
    /**
     * add an option to the select in the menu.
     * @param key - the key which pressed to choice the selection.
     * @param message - the message which displayed the selection.
     * @param returnVal - the task which going to run according the choice.
     */
    public void addSelection(String key, String message, Task<Void> returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.returnVals.add(returnVal);
    }

    /**
     * return the appropriate task according to the key that was pressing.
     * @return the task.
     */
    public Task<Void> getStatus() {
        return this.returnVals.get(statusIndex);
    }
    /**
     * initialize stop to be false again.
     */
    public void initializeStop() {
        this.stop = false;
    }
    /**
     * add a sub menu to a menu.
     * @param key - the key which pressed to choice the sub menu.
     * @param message - the message which displayed the selection.
     * @param subMenu - the sub menu.
     */
    public void addSubMenu(String key, String message, Menu<Task<Void>> subMenu) {
        this.keysOfSubMenu.add(key);
        this.messagesOfSubMenu.add(message);
        this.subMenus.add(subMenu);
    }
}
