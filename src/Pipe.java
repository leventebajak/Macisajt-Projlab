import java.util.ArrayList;

/**
 * A csővezeték hálózat csöveit megvalósító osztály.
 * A víz szállításáért felelős.
 */
public class Pipe extends Component {
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
     * A cső konstruktora.
     *
     * @param name: A cső neve
     */
    public Pipe(String name) {
        super(name);
    }

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
        // TODO: step pipe (stickyFor, slipperyFor, leakableIn)
        if (broken || nodes.size() != 2) PIPELINE_SYSTEM.leakWater(1);
    }

    /**
     * A cső egy szomszédjának hozzáadása a csomópontok (nodes) listába.
     *
     * @param component A cső egy hozzáadandó szomszédja
     */
    public void addNeighbor(Component component) {
        // TODO: check node reference before adding
        nodes.add((Node) component);
    }

    /**
     * A cső egy szomszédjának eltávolítása a csomópontok (nodes) listából.
     *
     * @param component A cső egy eltávolítandó szomszédja
     */
    public void removeNeighbor(Component component) {
        // TODO: check node reference before removing
        nodes.remove((Node) component);
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
        // TODO: pipe accepting player
        if (occupied || nodes.size() != 2) return false;

        occupied = true;
        players.add(player);
        return true;
    }

    /**
     * A csövön tartózkodó játékos eltávolítása a csövön tartózkodó játékosok listájából.
     *
     * @param player A eltávolítandó játékos
     */
    @Override
    public void remove(Player player) {
        players.remove(player);
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
        if (leakable) broken = true;
    }

    /**
     * Pumpa lerakásának megkísérlése.
     *
     * @param pump Az lerakni kívánt pumpa
     * @return Minden esetben igaz
     */
    @Override
    public boolean placePump(Pump pump) {
        // TODO: pipe naming the created pipe and saving its reference in Prototype.objects
        Pipe newPipe = new Pipe("newPipe");
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
     * A cső csúszóssá tételének megkísérlése.
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
        // TODO: leakbaleIn értékének sorsolása
    }

    /**
     * Beállítja a cső {@link Pipe#slipperyFor} attribútumát egy véletlen értékre 1 és 5 között.
     */
    private void setSlipperyFor() {
        // TODO: slipperyFor értékének sorsolása
    }

    /**
     * Beállítja a cső {@link Pipe#stickyFor} attribútumát egy véletlen értékre 1 és 5 között.
     */
    private void setStickyFor() {
        // TODO: stickyFor értékének sorsolása
    }

    /**
     * Új cső létrehozása a megadott névvel és csomópontokkal.
     *
     * @param args
     * @return
     */
    public static Pipe NEW(String[] args) {
        // TODO: new pipe
        return null;
    }

    /**
     * Cső tulajdonságainak lekérdezése.
     *
     * @param args
     * @return
     */
    public String stat(String[] args) {
        // TODO: stat pipe
        return null;
    }

    /**
     * Cső tulajdonságainak beállítása.
     *
     * @param args
     */
    public void set(String[] args) {
        // TODO: set pipe
    }
}
