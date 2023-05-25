import java.awt.Graphics;

public interface Drawable {
    void drawOnMap(Graphics g);

    boolean intersect(Point point);
}
