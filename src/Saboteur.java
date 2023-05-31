import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Szabotőr csapat játékosait megvalósító osztály. Felelőssége a csövek kilyukasztása.
 */
public class Saboteur extends Player {

    Saboteur(String name) {
        super(name);
    }

    /**
     * A szabotőr grafikus megjelenítésére szolgáló alakzat színe
     */
    private static final Color DEFAULT_COLOR = Color.BLACK;

    /**
     * Szabotőr kirajzolása a pályára
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    @Override
    public void drawOnMap(Graphics g) {
        // A játékos megjelenítése a komponens fölött
        int x = component.center.x;
        int y = component.center.y + component.players.indexOf(this) * -30;
        if (component instanceof Node) y -= 20;

        if (Game.getActivePlayer() == this)
            g.setColor(ACTIVE_COLOR);
        else
            g.setColor(DEFAULT_COLOR);

        var trianglePoints = new Polygon(new int[]{x, x - 20, x + 20}, new int[]{y, y - 30, y - 30}, 3);

        g.fillPolygon(trianglePoints);
        g.setColor(OUTLINE_COLOR);
        g.drawPolygon(trianglePoints);

        if (!ableToMove) {
            var stickyTrianglePoints = new Polygon(new int[]{x, x - 4, x + 4}, new int[]{y - 15, y - 21, y - 21}, 3);
            g.setColor(STICKY_COLOR);
            g.fillPolygon(stickyTrianglePoints);
            g.drawPolygon(stickyTrianglePoints);
        }
    }

    /**
     * Eldönti, hogy  paraméterként kapott pont rajta van-e a szabotőrön.
     * A játékosra kattintva nem történik semmi
     *
     * @param point a vizsgált pont
     */
    @Override
    public boolean intersect(Point point) {
        return false; // A játékosra kattintva nem történik semmi
    }

    /**
     * Megjeleníti a szabotőr nevét, és a szabotőr akcióit kezelő gombokat
     *
     * @param gameWindow az ablak, amin megjeleníti a szabotőr nevét és a szabotőr akcióit kezelő gombokat
     */
    @Override
    public void drawNameAndButtons(GameWindow gameWindow) {
        gameWindow.setPlayerPanel(new SaboteurPanel(this));
    }

    /**
     * A jelenlegi mező csúszóssá tételének megkísérlése.
     */
    public void makeItSlippery() {
        component.makeItSlippery();
        actionPerformed = true;
    }
}
