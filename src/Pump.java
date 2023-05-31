import javax.swing.JOptionPane;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 * Pumpa komponens megvalósítása.
 * Felelőssége a csövek összekötése, és egy kijelölt
 * csőből egy másik kijelölt csőbe víz pumpálása.
 */
public class Pump extends Node {

    /**
     * A pumpa grafikus megjelenítésére szolgáló alakzat színe, amikor a pumpa ki van lyukasztva
     */
    private static final Color BROKEN_COLOR = new Color(0xff6600);

    /**
     * A pumpa grafikus megjelenítésére szolgáló alakzat alapértelmezett színe
     */
    private static final Color DEFAULT_COLOR = Color.YELLOW;

    /**
     * Pumpa kirajzolása a pályára
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    @Override
    public void drawOnMap(Graphics g) {
        g.setColor(broken ? BROKEN_COLOR : DEFAULT_COLOR);
        int x = center.x - radius;
        int y = center.y - radius;
        g.fillOval(x, y, radius * 2, radius * 2);
        g.setColor(OUTLINE_COLOR);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawOval(x, y, radius * 2, radius * 2);

        g.setColor(Color.blue);
        if (source != null) {
            Point spipe = new Point(source.center.x - 5, source.center.y - 5);
            drawArrow(g, spipe.x, spipe.y, center.x, center.y);
        }
        if (destination != null) {
            Point dpipe = new Point(destination.center.x, destination.center.y);
            double distance = Point.distance(dpipe.x, dpipe.y, center.x, center.y);
            Point point = new Point();
            point.x = (int) (center.x + (dpipe.x - center.x) / (distance) * 40);
            point.y = (int) (center.y + (dpipe.y - center.y) / (distance) * 40);
            drawArrow(g, center.x, center.y, point.x, point.y);
        }
    }

    /**
     * A víz áramlását reprezentáló nyilak rajzolása
     *
     * @param g  A rajzoláshoz használt grafikai objektum
     * @param x0 A nyíl kezdőpontjának X koordinátája
     * @param y0 A nyíl kezdőpontjának Y koordinátája
     * @param x1 A nyíl végpontjának X koordinátája
     * @param y1 A nyíl végpontjának Y koordinátája
     */
    private static void drawArrow(Graphics g, double x0, double y0, double x1, double y1) {
        int ix2, iy2, ix3, iy3;
        double sinPhi, cosPhi, dx, dy, xk1, yk1, s;
        dx = x1 - x0;
        dy = y1 - y0;
        int maxArrowWidth = 8;
        s = Math.sqrt(dy * dy + dx * dx);
        int headLength = 25;

        double arrowAngle = Math.atan((double) maxArrowWidth / headLength);

        sinPhi = dy / s;
        cosPhi = dx / s;
        xk1 = -headLength * Math.cos(arrowAngle);
        yk1 = headLength * Math.sin(arrowAngle);

        ix2 = (int) (x1 + xk1 * cosPhi - yk1 * sinPhi);
        iy2 = (int) (y1 + xk1 * sinPhi + yk1 * cosPhi);
        ix3 = (int) (x1 + xk1 * cosPhi + yk1 * sinPhi);
        iy3 = (int) (y1 + xk1 * sinPhi - yk1 * cosPhi);
        Polygon p = new Polygon();
        p.addPoint((int) x1, (int) y1);
        p.addPoint(ix2, iy2);
        p.addPoint(ix3, iy3);
        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(p);
        g.setColor(OUTLINE_COLOR);
        g.drawPolygon(p);
    }

    /**
     * Eldönti, hogy  paraméterként kapott pont rajta van-e a pumpán
     *
     * @param point a vizsgált pont
     */
    @Override
    public boolean intersect(Point point) {
        double distance = Math.sqrt(Math.pow(point.x - center.x, 2) + Math.pow(point.y - center.y, 2));
        return Math.abs(distance) <= radius;
    }

    public boolean isBroken() {
        return broken;
    }

    /**
     * A pumpa töröttsége.
     */
    private boolean broken = false;

    /**
     * A pumpa állandó víz kapacítása ami 1 értéket vesz fel
     */
    private static final int CAPACITY = 1;

    /**
     * A pumpában tárolt víz mennyísége
     */
    private int waterLevel = 0;

    /**
     * A pumpa élettartalma
     */
    private int lifetime;

    /**
     * A pumpa forráscsöve
     */
    private Pipe source = null;

    /**
     * A pumpa célcsöve
     */
    private Pipe destination = null;

    /**
     * A pumpa konstruktora.
     */
    public Pump() {
        setLifetime();
    }

    /**
     * A pumpa élettartalmának beállítása.
     */
    private void setLifetime() {
        lifetime = (int) (Math.random() * 20) + 1;
    }

    /**
     * A pumpa forrás csővének beállítása.
     *
     * @param source Az új forrás cső ahonnan kapni fogja a vizet a pumpa
     */
    private void setSource(Pipe source) {
        if (source == null || pipes.contains(source))
            this.source = source;
        else System.out.println("A cső nem szomszédos a pumpával!");
    }

    /**
     * A pumpa cél csővének beállítása.
     *
     * @param destination Az új cél cső ahova adni fogja a vizet
     */
    private void setDestination(Pipe destination) {
        if (destination == null || pipes.contains(destination))
            this.destination = destination;
        else System.out.println("A cső nem szomszédos a pumpával!");
    }

    /**
     * A pumpa léptetése.
     * A pumpa vizet kap egy csőtől és vizet továbbít egy szomszédos csőnek.
     * Ha lejár az élettartalma a pumpának akkor elromlik.
     */
    public void step() {
        decreaseLifetime();
        broken = lifetime == 0;
        if (broken) {
            PIPELINE_SYSTEM.leakWater(removeWater(PIPELINE_SYSTEM.flowRate));
            return;
        }

        if (source != null)
            addWater(source.removeWater(Math.min(CAPACITY - waterLevel, PIPELINE_SYSTEM.flowRate)));
        if (destination != null)
            removeWater(destination.addWater(Math.min(waterLevel, PIPELINE_SYSTEM.flowRate)));
    }

    /**
     * A pumpa életerejének a csökkentése 1 egységgel.
     */
    private void decreaseLifetime() {
        lifetime = Math.max(0, lifetime - 1);
    }

    /**
     * A pumpához egy adott mennyiségű víz hozzáadása.
     *
     * @param amount A hozzáadandó víz mennyisége
     * @return A hozzáadott víz mennyisége
     */
    public int addWater(int amount) {
        amount = Math.min(amount, PIPELINE_SYSTEM.flowRate);
        final int added = waterLevel + amount <= CAPACITY ? amount : 0;
        waterLevel += added;
        return added;
    }

    /**
     * A pumpából egy adott mennyiségű víz eltávolítása.
     *
     * @param amount A eltávolítandó víz mennyisége
     * @return Az eltávolított víz mennyisége
     */
    public int removeWater(int amount) {
        amount = Math.min(amount, PIPELINE_SYSTEM.flowRate);
        final int removed = waterLevel - amount >= 0 ? amount : waterLevel;
        waterLevel -= removed;
        return removed;
    }

    /**
     * A cső megjavítása.
     */
    @Override
    public void repair() {
        if (broken) {
            broken = false;
            setLifetime();
        }
    }

    /**
     * A pumpa átirányítása.
     *
     * @param source      Az új forráscső
     * @param destination Az új célcső
     */
    @Override
    public void redirect(Pipe source, Pipe destination) {
        setSource(source);
        setDestination(destination);
    }

    /**
     * Egy cső felvétele az adott pumpánál.
     *
     * @param pipe A felvenni kívánt cső
     * @return Ha sikerült megfogni a csövet akkor igaz, egyébként hamis
     */
    @Override
    public boolean grabPipe(Pipe pipe) {
        if (!pipes.contains(pipe))
            return false;

        if (pipe.getOccupied()) return false;

        if (pipe == source) setSource(null);
        if (pipe == destination) setDestination(null);
        pipe.removeNeighbor(this);
        removeNeighbor(pipe);
        pipe.setOccupied(true);
        return true;
    }

    /**
     * Egy cső lerakása az adott pumpánál.
     *
     * @param pipe A lerakandó cső
     * @return Ha sikerült lerakni a csövet akkor igaz, egyébként hamis
     */
    @Override
    public boolean placePipe(Pipe pipe) {
        if (pipes.contains(pipe))
            return false;

        // Nézzük meg, hogy össze vannak-e már kötve
        if (pipe.getNeighbors() == 1)
            for (var p : pipes)
                if (p.isNeighborWith(this) && p.isNeighborWith(pipe.getRandomNode())) {
                    JOptionPane.showMessageDialog(null, "A két csomópont már össze van kötve!", "Hiba!", JOptionPane.INFORMATION_MESSAGE);
                    return false;
                }

        pipe.addNeighbor(this);
        addNeighbor(pipe);
        pipe.setOccupied(false);
        return true;
    }
}