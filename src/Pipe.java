import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * A csővezeték hálózat csöveit megvalósító osztály.
 * A víz szállításáért felelős.
 */
public class Pipe extends Component {

    @Override
    public void drawOnMap(Graphics g) {
        // TODO: ki kell találni hogyan rajzoljuk ki a felvett csöveket és a ciszternánál lévő szabad csöveket
        g.setColor(Color.GRAY);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        if (nodes.size() == 2)
            g2.draw(new Line2D.Float(nodes.get(0).center.x, nodes.get(0).center.y,
                    nodes.get(1).center.x, nodes.get(1).center.y));
    }

    @Override
    public boolean intersect(Point point) {
        // TODO: metszet eldöntése (a cső csomópontok alatti része nem lehet metszet)
        return false;
    }

    /**
     * A csőhöz kapcsolódó csövek.
     */
    private final ArrayList<Node> nodes = new ArrayList<>();

    /**
     * A cső töröttsége.
     */
    private boolean broken = false;

    /**
     * Azt jellemzi, hogy a cső lyukasztható-e.
     */
    private boolean leakable = true;

    /**
     * Azt jellemzi, hogy hány körnek kell eltelnie ahhoz, hogy a komponens kilyukasztható legyen.
     */
    private int leakableIn = 0;

    /**
     * Azt jellemzi, hogy a cső csúszós-e.
     */
    public boolean slippery = false;

    /**
     * Azt jellemzi, hogy még hány körig csúszós a cső.
     */
    private int slipperyFor = 0;

    /**
     * Azt jellemzi, hogy a cső ragadós-e.
     */
    private boolean sticky = false;

    /**
     * Azt jellemzi, hogy még hány körig ragadós a cső.
     */
    private int stickyFor = 0;

    /**
     * Egy olyan érték amely megmutatja hogy a csövön van-e játékos. Ha van játékos akkor igaz egyébként hamis.
     */
    private boolean occupied = false;

    /**
     * A cső állandó víz kapacítása.
     */
    private static final int CAPACITY = 1;

    /**
     * A csőben tárolt víz mennyísége, kezdetben 0 értéket vesz fel.
     */
    private int waterLevel = 0;

    /**
     * A cső foglaltságának beállítása.
     * Akkor használandó, ha a ciszternánál egy szabad végű csövet vesz fel a játékos.
     * Ennek következtében minden cső letevésekor is használni kell a függvényt.
     *
     * @param occupied az új foglaltság
     * @see Cistern#grabPipe(Pipe)
     * @see Pump#placePipe(Pipe)
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * A cső foglaltságának lekérdezése.
     *
     * @return a cső foglaltsága
     */
    public boolean getOccupied() {
        return occupied;
    }

    /**
     * A cső szomszédai számának lekérdezése.
     *
     * @return a cső szomszédainak száma
     */
    public int getNeighbors() {
        return nodes.size();
    }

    /**
     * A cső léptetése. Ha lyukas, vagy éppen viszik, akkor kifolyik belőle a víz.
     */
    public void step() {
        if (sticky && --stickyFor <= 0) sticky = false;
        if (slippery && --slipperyFor <= 0) slippery = false;
        if (!leakable && --leakableIn <= 0) leakable = true;
        if (broken || nodes.size() != 2) {
            waterLevel = 0;
            PIPELINE_SYSTEM.leakWater(1);
        }
    }

    /**
     * A cső egy szomszédjának hozzáadása a csomópontok (nodes) listába.
     *
     * @param component A cső egy hozzáadandó szomszédja
     */
    public void addNeighbor(Component component) {
        try {
            nodes.add((Node) component);
        } catch (ClassCastException ignored) {
            System.out.println("A komponens nem egy csomópont!");
        }
    }

    /**
     * A cső egy szomszédjának eltávolítása a csomópontok (nodes) listából.
     *
     * @param component A cső egy eltávolítandó szomszédja
     */
    public void removeNeighbor(Component component) {
        try {
            nodes.remove((Node) component);
        } catch (ClassCastException ignored) {
            System.out.println("A komponens nem egy csomópont!");
        }
    }

    /**
     * A csőhöz egy adott mennyiségű víz hozzáadása.
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
     * A csőből egy adott mennyiségű víz eltávolítása.
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
     * A csövön tartózkodó játékos hozzáadása a csövön tartózkodó játékosok listájába.
     *
     * @param player A hozzáadandó játékos
     * @return Ha sikerült hozzáadni akkor igaz, egyébként hamis
     */
    @Override
    public boolean accept(Player player) {
        try {
            if (player.component == null || nodes.contains((Node) player.component)) {
                if (occupied || nodes.size() != 2) return false;
                if (sticky) player.setAbleToMoveIn();
                occupied = true;
                players.add(player);
                return true;
            }
            return false;
        } catch (ClassCastException ignored) {
            System.out.println("A csőre csak csomópontról lehet lépni!");
            return false;
        }

    }

    /**
     * A csövön tartózkodó játékos eltávolítása a csövön tartózkodó játékosok listájából.
     *
     * @param player A eltávolítandó játékos
     */
    @Override
    public void remove(Player player) {
        players.remove(player);
        occupied = false;
    }

    /**
     * A cső egy végének lekérdezése véletlenszerűen.
     * Csúszós csöveknél használandó.
     *
     * @return egy véletlen csomópont
     */
    public Node getRandomNode() throws NullPointerException {
        if (nodes.isEmpty()) throw new NullPointerException("A csőhöz nem kapcsolódik csomópont!");
        return nodes.get((int) (Math.random() * nodes.size()));
    }

    /**
     * A cső megjavítása.
     */
    @Override
    public void repair() {
        if (broken) {
            broken = false;
            setLeakableIn();
        }
    }

    /**
     * A cső kilyukasztása.
     */
    @Override
    public void leak() {
        if (leakable) {
            broken = true;
            leakable = false;
        }
    }

    /**
     * Pumpa lerakásának megkísérlése.
     *
     * @param pump Az lerakni kívánt pumpa
     * @return Minden esetben igaz
     */
    @Override
    public boolean placePump(Pump pump) {
        PIPELINE_SYSTEM.addComponent(pump);
        Pipe newPipe = new Pipe();
        PIPELINE_SYSTEM.addComponent(newPipe);
        newPipe.addNeighbor(nodes.get(0));
        removeNeighbor(nodes.get(0));
        newPipe.addNeighbor(pump);
        pump.addNeighbor(newPipe);
        pump.addNeighbor(this);
        addNeighbor(pump);
        return true;
    }

    /**
     * A cső csúszóssá tételének megkísérlése.
     */
    @Override
    public void makeItSlippery() {
        if (!slippery) {
            slippery = true;
            setSlipperyFor();
        }
    }

    /**
     * A cső ragadóssá tételének megkísérlése.
     */
    @Override
    public void makeItSticky() {
        if (!sticky) {
            sticky = true;
            setStickyFor();
        }
    }

    /**
     * Beállítja a cső {@link Pipe#leakableIn} attribútumát egy véletlen értékre 1 és 5 között.
     */
    private void setLeakableIn() {
        leakableIn = (int) (Math.random() * 5) + 1;
    }

    /**
     * Beállítja a cső {@link Pipe#slipperyFor} attribútumát egy véletlen értékre 1 és 5 között.
     */
    private void setSlipperyFor() {
        slipperyFor = (int) (Math.random() * 5) + 1;
    }

    /**
     * Beállítja a cső {@link Pipe#stickyFor} attribútumát egy véletlen értékre 1 és 5 között.
     */
    private void setStickyFor() {
        stickyFor = (int) (Math.random() * 5) + 1;
    }
}
