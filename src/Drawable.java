import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Drawable {

    Color OUTLINE_COLOR = Color.BLACK;

    void drawOnMap(Graphics g);

    boolean intersect(Point point);
}
