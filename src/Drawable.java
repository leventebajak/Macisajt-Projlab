import java.awt.Graphics;
import java.awt.Point;

public interface Drawable {
    void drawOnMap(Graphics g);

    boolean intersect(Point point);
}
