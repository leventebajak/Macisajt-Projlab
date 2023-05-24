import javax.swing.*;

public interface Drawable {
    void drawOnMap(JPanel map);

    boolean intersect(Point point);
}
