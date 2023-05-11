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
     * @param args a parancs elvárt paraméterei:
     *             {@code new pipe <cső neve> <egyik csomópont neve> [másik csomópont neve]}
     * @return a létrehozott cső referenciája
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public static Pipe NEW(String[] args) throws IllegalArgumentException {
        if (args.length == 4) {
            try {

                Cistern cistern = (Cistern) Prototype.OBJECTS.get(args[3]);
                if (cistern == null) throw new IllegalArgumentException("Nem létezik ciszterna a megadott névvel!");
                Pipe pipe = new Pipe(args[2]);
                pipe.addNeighbor(cistern);
                cistern.addNeighbor(pipe);
                return pipe;
            } catch (ClassCastException ignored) {
                throw new IllegalArgumentException("Szabad végű cső csak ciszternához csatlakozhat!");
            }
        }
        if (args.length == 5) {
            try {
                Node neighbor1 = (Node) Prototype.OBJECTS.get(args[3]);
                Node neighbor2 = (Node) Prototype.OBJECTS.get(args[4]);
                if (neighbor1 == null || neighbor2 == null)
                    throw new IllegalArgumentException("Nem létezik csomópont a megadott névvel!");
                Pipe pipe = new Pipe(args[2]);
                pipe.addNeighbor(neighbor1);
                neighbor1.addNeighbor(pipe);
                pipe.addNeighbor(neighbor2);
                neighbor2.addNeighbor(pipe);
                return pipe;
            } catch (ClassCastException ignored) {
                throw new IllegalArgumentException("A cső végei csak csomópontok lehetnek!");
            }
        }
        throw new IllegalArgumentException("Érvénytelen paraméter!");
    }

    /**
     * Cső tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
    	String attr = new String();
    	args[2] = args[2].strip().toLowerCase();
    	switch (args[2]) {
        case "broken" -> { return "broken: " + broken; }
        case "leakable" ->  { return "leakable: " + leakable; }
        case "slippery" ->  { return "slippery: " + slippery; }
        case "sticky" ->  { return "sticky: " + sticky; }
        case "occupied" ->  { return "occupied: " + occupied; }
        case "capacity" ->  { return "capacity: " + CAPACITY; }
        case "waterlevel" ->  { return "waterLevel: " + waterLevel; }  
        case "leakablein" ->  { return "leakableIn: " + leakableIn; }  
        case "slipperyfor" ->  { return "slipperyFor: " + slipperyFor; }  
        case "stickyfor" ->  { return "stickyFor: " + stickyFor; }  
        case "nodes" ->  { 
        	attr.concat("nodes:");
        	for(Node n : nodes)
        		attr.concat(" "+ n.name);
        	}
        case "players" ->  { 
        	attr.concat("players:");
        	for(Player p : players)
        		attr.concat(" "+ p.name);
        	}
        default -> { 
        	throw new IllegalArgumentException("A csőnek nincs ilyen nevű tulajdonsága"); 
        	}
    	}
    	return attr;
    }

    /**
     * Cső tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        // TODO: set pipe
    }
}
