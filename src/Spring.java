import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * A hegyi forrásokat valósítja meg. Felelőssége a víz pumpálása minden szomszédos csőbe minden kör elején.
 */
public class Spring extends Node {

    /**
     * A hegyi forrás grafikus megjelenítésére szolgáló alakzat színe
     */
    private static final Color DEFAULT_COLOR = Color.GREEN;

    /**
     * Hegyi forrás kirajzolása a pályára
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    @Override
    public void drawOnMap(Graphics g) {
        g.setColor(DEFAULT_COLOR);
        int x = center.x - radius;
        int y = center.y - radius;
        g.fillOval(x, y, radius * 2, radius * 2);
        g.setColor(OUTLINE_COLOR);
        ((Graphics2D) g).setStroke(new BasicStroke(2));
        g.drawOval(x, y, radius * 2, radius * 2);
    }

    /**
     * Eldönti, hogy  paraméterként kapott pont rajta van-e a hegyi forráson.
     *
     * @param point a vizsgált pont
     */
    @Override
    public boolean intersect(Point point) {
        double distance = Math.sqrt(Math.pow(point.x - center.x, 2) + Math.pow(point.y - center.y, 2));
        return Math.abs(distance) <= radius;
    }

    /**
     * Víz pumpálása minden szomszédos csőbe.
     */
    public void step() {
        for (var pipe : pipes)
            pipe.addWater(PIPELINE_SYSTEM.flowRate);
    }

    /**
     * Ennek a függvénynek a törzse üres, mert a hegyi forrásokba nem pumpálható víz.
     *
     * @param amount a bejövő víz mennyisége
     * @return minden esetben 0
     */
    public int addWater(int amount) {
        return 0;
    }

    /**
     * Víz szívása a hegyi forrásból.
     *
     * @param amount a kimenő víz mennyisége
     * @return minden esetben a paraméterként kapott szám
     */
    public int removeWater(int amount) {
        return Math.min(amount, PIPELINE_SYSTEM.flowRate);
    }

    /**
     * Játékos fogadása a mezőre. A játékosok nem tudnak hegyi forrásra lépni.
     *
     * @param player a fogadott játékos
     * @return minden esetben hamis
     */
    @Override
    public boolean accept(Player player) {
        return false;
    }
}
