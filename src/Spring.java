import java.awt.Color;
import java.awt.Graphics;

/**
 * A hegyi forrásokat valósítja meg. Felelőssége a víz pumpálása minden szomszédos csőbe minden kör elején.
 */
public class Spring extends Node {

    @Override
    public void drawOnMap(Graphics g) {
        // TODO: forrás felrajzolása a center attribútum használatával
    	g.setColor(Color.GREEN);
        int radius = 15; 
        
        int x = center.X() - radius;
        int y = center.Y() - radius;
        
        g.fillOval(x, y, radius * 2, radius * 2);
    }

    @Override
    public boolean intersect(Point point) {
        // TODO: metszet eldöntése
        return false;
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
