import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * A csővezeték hálózat csöveit megvalósító osztály.
 * A víz szállításáért felelős.
 */
public class Pipe extends Component {

    /**
     * A cső grafikus megjelenítésére szolgáló vonal alapértelmezett színe
     */
    private static final Color DEFAULT_COLOR = Color.GRAY;

    /**
     * A cső grafikus megjelenítésére szolgáló vonal színe, amikor a cső ki van lyukasztva
     */
    private static final Color BROKEN_COLOR = new Color(0xff6600);

    /**
     * A cső grafikus megjelenítésére szolgáló vonal színe, amikor a cső csúszós
     */
    private static final Color SLIPPERY_COLOR = new Color(0x9bedff);

    /**
     * A cső grafikus megjelenítésére szolgáló vonal színe, amikor a cső ragadós
     */
    private static final Color STICKY_COLOR = new Color(0x91ff77);

    /**
     * A cső grafikus megjelenítésére szolgáló vonal vastagsága
     */
    public static final int width = 10;

    /**
     * Cső kirajzolása a pályára
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    @Override
    public void drawOnMap(Graphics g) {
        g.setColor(OUTLINE_COLOR);
        ((Graphics2D) g).setStroke(new BasicStroke(width + 4));
        if (nodes.size() == 2) {
            this.center = new Point((nodes.get(0).center.x + nodes.get(1).center.x) / 2, (nodes.get(0).center.y + nodes.get(1).center.y) / 2);
            g.drawLine(nodes.get(0).center.x, nodes.get(0).center.y, nodes.get(1).center.x, nodes.get(1).center.y);
        } else if (nodes.size() == 1) {
            this.center = new Point(nodes.get(0).center.x, nodes.get(0).center.y);
            g.drawLine(nodes.get(0).center.x, nodes.get(0).center.y, nodes.get(0).center.x + (int) (Math.sin(this.hashCode() % 360) * 50), nodes.get(0).center.y - (int) (Math.cos(this.hashCode() % 360) * 50));
        } else return;

        ((Graphics2D) g).setStroke(new BasicStroke(width));
        if (broken)
            g.setColor(BROKEN_COLOR);
        else if (slippery)
            g.setColor(SLIPPERY_COLOR);
        else if (sticky)
            g.setColor(STICKY_COLOR);
        else
            g.setColor(DEFAULT_COLOR);

        if (nodes.size() == 2)
            g.drawLine(nodes.get(0).center.x, nodes.get(0).center.y, nodes.get(1).center.x, nodes.get(1).center.y);
        else if (nodes.size() == 1)
            g.drawLine(nodes.get(0).center.x, nodes.get(0).center.y, nodes.get(0).center.x + (int) (Math.sin(this.hashCode() % 360) * 50), nodes.get(0).center.y - (int) (Math.cos(this.hashCode() % 360) * 50));
    }

    /**
     * Eldönti, hogy  paraméterként kapott pont rajta van-e a csövön
     *
     * @param point a vizsgált pont
     */
    @Override
    public boolean intersect(Point point) {
        Point pipeSourcePoint = nodes.get(0).center;
        Point pipeDestinationPoint;

        if (nodes.size() == 1) {
            pipeDestinationPoint = new Point(center.x + (int) (Math.sin(this.hashCode() % 360) * 50), center.y - (int) (Math.cos(this.hashCode() % 360) * 50));
        } else {
            pipeDestinationPoint = nodes.get(1).center;
        }

        double distanceAC = pipeSourcePoint.distance(point);
        double distanceBC = pipeDestinationPoint.distance(point);
        double distanceAB = pipeSourcePoint.distance(pipeDestinationPoint);

        return Math.abs((distanceAC + distanceBC) - distanceAB) <= 0.2;
    }

    /**
     * Eldönti, hogy a cső törött-e
     */
    public boolean isBroken() {
        return broken;
    }

    /**
     * Eldönti, hogy a cső csúszós-e
     */
    public boolean isSlippery() {
        return slippery;
    }

    /**
     * Eldönti, hogy a cső ragadós-e
     */
    public boolean notSticky() {
        return !sticky;
    }

    /**
     * Eldönti, hogy a cső szomszédos-e a parméterként kpott csomóponttal
     *
     * @param node a vizsgált csomópont
     */
    public boolean isNeighborWith(Node node) {
        return nodes.contains(node);
    }

    /**
     * Eldönti, hogy a cső kilyukasztható-e
     */
    public boolean isLeakable() {
        return leakable;
    }

    /**
     * A csőhöz kapcsolódó csomópontok.
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
    private boolean slippery = false;

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
        if (!leakable && --leakableIn == 0) leakable = true;
        if ((broken || nodes.size() != 2)) {
            var leaked = Math.min(PIPELINE_SYSTEM.flowRate, waterLevel);
            PIPELINE_SYSTEM.leakWater(leaked);
            waterLevel -= leaked;
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
        pump.center = this.center;
        PIPELINE_SYSTEM.addComponent(pump);
        Pipe newPipe = new Pipe();
        newPipe.center = new Point((nodes.get(0).center.x + pump.center.x) / 2, (nodes.get(0).center.y + pump.center.y) / 2);
        PIPELINE_SYSTEM.addComponent(newPipe);
        newPipe.addNeighbor(nodes.get(0));
        nodes.get(0).addNeighbor(newPipe);
        removeNeighbor(nodes.get(0));
        newPipe.addNeighbor(pump);
        pump.addNeighbor(newPipe);
        pump.addNeighbor(this);
        addNeighbor(pump);
        center = new Point((nodes.get(0).center.x + nodes.get(1).center.x) / 2, (nodes.get(0).center.y + nodes.get(1).center.y) / 2);
        pump.redirect(this, newPipe);
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
     * Beállítja a cső {@link Pipe#leakableIn} attribútumát egy véletlen értékre 1 és 8 között.
     */
    private void setLeakableIn() {
        leakableIn = (int) (Math.random() * 8) + 1;
    }

    /**
     * Beállítja a cső {@link Pipe#slipperyFor} attribútumát egy véletlen értékre 1 és 8 között.
     */
    private void setSlipperyFor() {
        slipperyFor = (int) (Math.random() * 8) + 1;
    }

    /**
     * Beállítja a cső {@link Pipe#stickyFor} attribútumát egy véletlen értékre 1 és 8 között.
     */
    private void setStickyFor() {
        stickyFor = (int) (Math.random() * 8) + 1;
    }
}
