import java.awt.Point;
import java.util.ArrayList;

/*
 * To Do:
 *  - might want to change coordinate system used
 */
public class Node<T> {

    private Point p;
    private T val;
    private boolean selected;

    /**
     * Creates a node with a value and a point
     * 
     * @param val Value of any type
     * @param p   Point on the screen
     */
    public Node(T val, Point p) {
        this.val = val;
        this.p = p;
    }

    /**
     * Creates a node with a value and point at 0, 0
     * 
     * @param val Value of any type
     */
    public Node(T val) {
        this(val, new Point(0, 0));
    }

    /**
     * Sets value to new value
     * 
     * @param val New value
     */
    public void setVal(T val) {
        this.val = val;
    }

    /**
     * @return Returns the nodes value
     */
    public T getVal() {
        return this.val;
    }

    /**
     * Sets position
     * 
     * @param x
     * @param y
     */
    public void setPos(int x, int y) {
        this.p.setLocation(x, y);
    }

    /**
     * @return Returns a Point of the nodes position
     */
    public Point getPos() {
        return p;
    }

    /**
     * @return status of selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets node to selected
     * 
     * @param selected true if its selected, false if unselected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String toString() {
        String str = val + ": [x: " + p.getX() + ", y: " + p.getY() + "]";
        return str;
    }

}
