import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Drawable interface, a játékbeli komponensek kirajzolását segíti
 */
public interface Drawable {

    /**
     * A pályára kirajzolandó objektumok körvonalának színe.
     */
    Color OUTLINE_COLOR = Color.BLACK;

    /**
     * Objektum kirajzolása a pályára
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    void drawOnMap(Graphics g);

    /**
     * Eldönti, hogy  paraméterként kapott pont rajta van-e az objektumon
     *
     * @param point a vizsgált pont
     */
    boolean intersect(Point point);
}
