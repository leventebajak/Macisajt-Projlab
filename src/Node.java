import java.util.ArrayList;

/**
 * A csővezeték hálózat csomópontjainak absztrakt ősosztálya.
 * Felelőssége a csövekkel való kapcsolat megvalósítása.
 */
public abstract class Node extends Component {

    /**
     * A csomóponthoz kapcsolódó csövek.
     */
    protected final ArrayList<Pipe> pipes = new ArrayList<>();

    /**
     * Csomópont konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Node(String name) {
        super(name);
    }

    /**
     * Játékos fogadása a csomópontra. A visszatérési értékkel válaszol, hogy tudja-e fogadni a játékost.
     *
     * @param player A játékos akit fogani kell
     * @return Ha sikerült fogadni akkor igaz, egyébként hamis
     */
    public boolean accept(Player player) {
        if(pipes.contains(player.component) || this == player.component){
            players.add(player);
            return true;
        }
        System.out.println("Lépés nem sikerült - nem szomszédos csőről próbál a node-ra lépni a játékos!");
        return false;
    }


    /**
     * Szomszédos cső beállítása.
     *
     * @param component Az új szomszédos cső
     */
    public void addNeighbor(Component component) {
        // TODO: check pipe reference before adding
        if(component instanceof Pipe) { //lehet, hogy nem kéne használni, csak egy tipp - Domonkos
            this.pipes.add((Pipe) component);
        }
    }

    /**
     * Egy szomszédos cső törlése.
     *
     * @param component A törlendő szomszédos cső
     */
    public void removeNeighbor(Component component) {
        // TODO: check pipe reference before removing
        if(component instanceof Pipe){ //lehet, hogy nem kéne használni, csak egy tipp - Domonkos
            pipes.remove((Pipe) component);
        }
    }
}
