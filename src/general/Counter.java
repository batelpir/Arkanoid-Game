package general;

/**
 * @author batel pirov.
 * a class that can count.
 * it has the 'counter' which is the current count.
 */
public class Counter {
    private int counter;
    /**
     * constructor.
     * @param counter - the current count.
     */
    public Counter(int counter) {
        this.counter = counter;
    }
    /**
     * add number to current count.
     * @param number - the number we add.
     */
    public void increase(int number) {
        this.counter = this.counter + number;
    }
    /**
     * subtract number from current count.
     * @param number - the number we subtract.
     */
    public void decrease(int number) {
        this.counter = this.counter - number;
    }
    /**
     * get current count.
     * @return the cirrent count.
     */
    public int getValue() {
        return this.counter;
    }
}
