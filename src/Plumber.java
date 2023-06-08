import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Szerelő csapat játékosait reprezentáló osztály. Felelőssége a csövek megjavítása és új pumpa lerakása.
 */
public class Plumber extends Player {

    Plumber(String name) {
        super(name);
    }

    /**
     * A szerelő grafikus megjelenítésére szolgáló alakzat színe
     */
    private static final Color DEFAULT_COLOR = Color.WHITE;

    /**
     * Szerelő kirajzolása a pályára
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
     * Eldönti, hogy  paraméterként kapott pont rajta van-e a szerelőn.
     * A játékosra kattintva nem történik semmi
     *
     * @param point a vizsgált pont
     */
    @Override
    public boolean intersect(Point point) {
        return false; // A játékosra kattintva nem történik semmi
    }

    /**
     * Megjeleníti a szerelő nevét, és a szerelő akcióit kezelő gombokat
     *
     * @param gameWindow az ablak, amin megjeleníti a szerelő nevét és a szerelő akcióit kezelő gombokat
     */
    @Override
    public void drawNameAndButtons(GameWindow gameWindow) {
        gameWindow.setPlayerPanel(new PlumberPanel(this));
    }

    /**
     * Az a cső, amit a szerelő felvett.
     */
    public Pipe grabbedPipe;

    /**
     * Ez az attribútum a szerelőnél lévő pumpa referenciája.
     */
    public Pump grabbedPump;

    /**
     * Pumpa átállítása, de csak akkor, ha a szerelő nem mozgat csövet.
     *
     * @param source      Az a cső, amelyből a pumpa szívja a vizet.
     * @param destination Az a cső, amelybe a pumpa pumpálja a vizet.
     */
    @Override
    public void redirect(Pipe source, Pipe destination) {
        if (grabbedPipe != null) return;
        super.redirect(source, destination);
    }

    /**
     * A jelenlegi mező megjavításának megkísérlése.
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet.
     */
    public void repair() {
        if (grabbedPipe != null) return;
        component.repair();
        actionPerformed = true;
    }

    /**
     * A szerelőnek való pumpa adás megkísérlése.
     */
    @Override
    public void receivePump() {
        if (grabbedPump != null) return;
        grabbedPump = new Pump();
    }

    /**
     * A játékosnál található pumpa lerakásának megkísérlése.
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet, és van pumpa a szerelőnél.
     */
    public void placePump() {
        if (grabbedPipe != null || grabbedPump == null) return;
        if (component.placePump(grabbedPump)) {
            setComponent(grabbedPump);
            grabbedPump = null;
            actionPerformed = true;
        }
    }

    /**
     * Egy megadott cső megfogásának megkísérlése.
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet.
     */
    public void grabPipe(Pipe pipe) {
        if (grabbedPipe != null) return;
        if (component.grabPipe(pipe)) {
            grabbedPipe = pipe;
            actionPerformed = true;
        }
    }

    /**
     * A játékos által megfogott cső lerakásának megkísérlése.
     */
    public void placePipe() {
        if (grabbedPipe == null) return;
        if (component.placePipe(grabbedPipe)) {
            grabbedPipe = null;
            actionPerformed = true;
        }
    }

    /**
     * A jelenlegi mező kilyukasztásának megkísérlése, ha a játékosnál nincs cső.
     */
    @Override
    public void leak() {
        if (grabbedPipe != null) return;
        super.leak();
    }

    /**
     * A jelenlegi mező ragadóssá tételének megkísérlése, ha a játékosnál nincs cső.
     */
    @Override
    public void makeItSticky() {
        if (grabbedPipe != null) return;
        super.makeItSticky();
    }
}