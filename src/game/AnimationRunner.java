package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
/**
 * @author batel pirov.
 *  is the looping code.
 *  gets an animation and runs it until a sign from "shouldStop" method.
 */
public class AnimationRunner {
    private GUI gui;
    private Sleeper sleeper;
    private int framesPerSecond;
    /**
     * constructor.
     * @param framesPerSecond - 60 in our case.
     * @param gui - the gui of the game.
     */
    public AnimationRunner(int framesPerSecond, GUI gui) {
        this.framesPerSecond = framesPerSecond;
        this.gui = gui;
    }
    /**
     * runs an animation.
     * @param animation - the animation it runs.
     */
    public void run(Animation animation) {
       this.sleeper = new Sleeper();
       int millisecondsPerFrame = 1000 / this.framesPerSecond;
       while (!animation.shouldStop()) {
          long startTime = System.currentTimeMillis(); // timing
          DrawSurface d = gui.getDrawSurface();
          animation.doOneFrame(d);
          gui.show(d);
          long usedTime = System.currentTimeMillis() - startTime;
          long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
          if (milliSecondLeftToSleep > 0) {
              this.sleeper.sleepFor(milliSecondLeftToSleep);
          }
       }
    }
 }