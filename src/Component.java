import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A csővezeték rendszer elemeinek az absztrakt őse.
 * Az elemeken hívható valamennyi metódust tartalmazza. Az elemek alapértelmezett működését írja le.
 * Az alapértelmezettől eltérő működés a leszármazott osztályokban felül lesz definiálva.
 */
public abstract class Component implements Drawable, Serializable {

    /**
     * Komponens középpontja a pályán
     */
    public Point center = null;

    /**
     * Komponens kirajzolása a pályára
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    @Override
    public abstract void drawOnMap(Graphics g);

    /**
     * Eldönti, hogy  paraméterként kapott pont rajta van-e a komponensen
     *
     * @param point a vizsgált pont
     */
    @Override
    public abstract boolean intersect(Point point);

    /**
     * A komponenst tartalmazó csőrendszer.
     */
    protected static PipelineSystem PIPELINE_SYSTEM;

    /**
     * A komponensen tartózkodó játékosok.
     */
    protected final ArrayList<Player> players = new ArrayList<>();

    /**
     * A kör végén végrehajtandó feladatokat megvalósító függvény.
     */
    public abstract void step();

    /**
     * Szomszédos komponens beállítása.
     *
     * @param component Az új szomszédos komponens
     */
    public abstract void addNeighbor(Component component);

    /**
     * Egy szomszédos komponens törlése.
     *
     * @param component A törlendő szomszédos komponens
     */
    public abstract void removeNeighbor(Component component);

    /**
     * Absztrakt metódus a víz pumpálás megvalósításához.
     *
     * @param amount A hozzáadandó víz mennyisége
     * @return A hozzáadott víz mennyisége
     */
    public abstract int addWater(int amount);

    /**
     * Absztrakt metódus a víz szívás megvalósításához.
     *
     * @param amount A eltávolítandó víz mennyisége
     * @return Az eltávolított víz mennyisége
     */
    public abstract int removeWater(int amount);

    /**
     * Játékos fogadása a komponensre. A visszatérési értékkel válaszol, hogy tudja-e fogadni a játékost.
     *
     * @param player A játékos akit fogani kell
     * @return Ha sikerült fogadni akkor igaz, egyébként hamis
     */
    public abstract boolean accept(Player player);

    /**
     * Játékos eltávolítása a komponensről.
     *
     * @param player A eltávolítandó játékos
     */
    public void remove(Player player) {
        players.remove(player);
    }

    /**
     * A komponens megjavításának megkísérlése.
     */
    public void repair() {
    }

    /**
     * A komponens kilyukasztásának megkísérlése. A függvény a törzse üres, mert csak csövön értelmezett.
     */
    public void leak() {
    }

    /**
     * A komponens átirányításának megkísérlése. A függvény a törzse üres, mert csak pumpán értelmezett.
     *
     * @param source      Az új forrás cső
     * @param destination Az új cél cső
     */
    public void redirect(Pipe source, Pipe destination) {
    }

    /**
     * Pumpa elhelyezésének megkísérlése. Csak csövön értelmezett.
     *
     * @param pump A lerakni kívánt pumpa
     */
    public boolean placePump(Pump pump) {
        return false;
    }

    /**
     * Cső megragadásának megkísérlése. A visszatérési értékkel közli, hogy sikeres volt-e a művelet.
     *
     * @param pipe A megfogandó cső
     */
    public boolean grabPipe(Pipe pipe) {
        return false;
    }

    /**
     * Cső elengedése/elhelyezése. A visszatérési értékkel közli, hogy sikeres volt-e a művelet.
     *
     * @param pipe A lerakandó cső
     */
    public boolean placePipe(Pipe pipe) {
        return false;
    }

    /**
     * A komponens csúszóssá tételének megkísérlése. A függvény csak csövön értelmezett.
     */
    public void makeItSlippery() {
    }

    /**
     * A komponens ragadóssá tételének megkísérlése. A függvény csak csövön értelmezett.
     */
    public void makeItSticky() {
    }
}
