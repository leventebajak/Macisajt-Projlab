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
        if (occupied || nodes.size() != 2) return false;

        if (sticky) player.setAbleToMoveIn();

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
        occupied = false;
    }

    /**
     * A cső egy végének lekérdezése véletlenszerűen.
     * Csúszós csöveknél használandó.
     * @return egy véletlen csomópont
     */
    public Node getRandomNode() throws NullPointerException {
        if (nodes.isEmpty())
            throw new NullPointerException("A csőhöz nem kapcsolódik csomópont!");
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
        int i = 1;
        while (Prototype.OBJECTS.containsKey("Pipe" + i)) i++;
        Pipe newPipe = new Pipe("Pipe" + i);
        PIPELINE_SYSTEM.addComponent(newPipe);
        Prototype.OBJECTS.put(newPipe.name, newPipe);
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
        if (args.length == 2) {
            var result = new StringBuilder(this.toString());
            result.append("\nnodes:");
            for (Node n : nodes)
                result.append(" ").append(n.name);
            result.append("\nplayers:");
            for (Player p : players)
                result.append(" ").append(p.name);
            result.append("\nbroken: ").append(broken);
            result.append("\nleakable: ").append(leakable);
            result.append("\nslippery: ").append(slippery);
            result.append("\nsticky: ").append(sticky);
            result.append("\noccupied: ").append(occupied);
            result.append("\ncapacity: ").append(CAPACITY);
            result.append("\nwaterLevel: ").append(waterLevel);
            result.append("\nleakableIn: ").append(leakableIn);
            result.append("\nslipperyFor: ").append(slipperyFor);
            result.append("\nstickyFor: ").append(stickyFor);
            return result.toString();
        }

        if (args.length != 3) throw new IllegalArgumentException("Érvénytelen paraméter!");
        return switch (args[2].strip().toLowerCase()) {
            case "broken" -> "broken: " + broken;
            case "leakable" -> "leakable: " + leakable;
            case "slippery" -> "slippery: " + slippery;
            case "sticky" -> "sticky: " + sticky;
            case "occupied" -> "occupied: " + occupied;
            case "capacity" -> "capacity: " + CAPACITY;
            case "waterlevel" -> "waterLevel: " + waterLevel;
            case "leakablein" -> "leakableIn: " + leakableIn;
            case "slipperyfor" -> "slipperyFor: " + slipperyFor;
            case "stickyfor" -> "stickyFor: " + stickyFor;
            case "nodes" -> {
                var result = new StringBuilder("nodes:");
                for (Node n : nodes)
                    result.append(" ").append(n.name);
                yield result.toString();
            }
            case "players" -> {
                var result = new StringBuilder("players:");
                for (Player p : players)
                    result.append(" ").append(p.name);
                yield result.toString();
            }
            default -> throw new IllegalArgumentException("A csőnek nincs ilyen nevű tulajdonsága");
        };
    }

    /**
     * Cső tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        if (args.length != 4) throw new IllegalArgumentException("Hiányzó paraméter!");

        args[3] = args[3].strip();
        switch (args[2].strip().toLowerCase()) {
            case "broken" -> {
                switch (args[3]) {
                    case "true" -> broken = true;
                    case "false" -> broken = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "leakable" -> {
                switch (args[3]) {
                    case "true" -> leakable = true;
                    case "false" -> leakable = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "slippery" -> {
                switch (args[3]) {
                    case "true" -> slippery = true;
                    case "false" -> slippery = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "sticky" -> {
                switch (args[3]) {
                    case "true" -> sticky = true;
                    case "false" -> sticky = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "occupied" -> {
                switch (args[3]) {
                    case "true" -> occupied = true;
                    case "false" -> occupied = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "waterlevel" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    assert 0 <= value && value <= CAPACITY;
                    waterLevel = value;
                } catch (NumberFormatException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "leakablein" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    assert 0 <= value && value <= 5;
                    leakableIn = value;
                    leakable = leakableIn == 0;
                } catch (NumberFormatException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "slipperyfor" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    assert 0 <= value && value <= 5;
                    slipperyFor = value;
                    slippery = !(slipperyFor == 0);
                } catch (NumberFormatException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "stickyfor" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    assert 0 <= value && value <= 5;
                    stickyFor = value;
                    sticky = !(stickyFor == 0);
                } catch (NumberFormatException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
        }
    }
}
