import java.util.ArrayList;
import java.util.Arrays;

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
     * Szomszédos cső beállítása.
     *
     * @param component Az új szomszédos cső
     */
    public void addNeighbor(Component component) {
        // TODO: check pipe reference before adding
        this.pipes.add((Pipe) component);
    }

    /**
     * Egy szomszédos cső törlése.
     *
     * @param component A törlendő szomszédos cső
     */
    public void removeNeighbor(Component component) {
        // TODO: check pipe reference before removing
        pipes.remove((Pipe) component);
    }
}
