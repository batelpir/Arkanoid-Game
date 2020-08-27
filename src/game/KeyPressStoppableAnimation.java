package game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
/**
 * @author batel pirov.
 * gets an stoppable animation and stop it according the key which was given.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;
    /**
     * constructor.
     * @param sensor - the key board sensor.
     * @param key - the key which pressed to stop the animation.
     * @param animation - the animation which runs until pressing the key.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = this.animation.shouldStop();
        this.isAlreadyPressed = true;
 }

    /**
     * call to 'do one frame' of the animation and checks when to stop the running.
     * @param d - the draw surface.
     */
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);
        // the case which the key was pressed now but was not pressed before.
        if (this.sensor.isPressed(key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        }
        // after start running that animation we check if the key is pressed.
        if (!this.sensor.isPressed(key)) {
            this.isAlreadyPressed = false;
        }
    }

    /**
     * returns a sign to stop running the animation.
     * @return a sign to stop running the animation.
     */
    public boolean shouldStop() { return this.stop; }
    /**
     * initialize stop to be false again.
     */
    public void initializeStop() {
        this.stop = false;
    }
}