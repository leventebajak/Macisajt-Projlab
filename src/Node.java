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
    public Node(String name) { super(name); }

    /**
     * Inicializáló függvény, ami inicializálja a csomóponthoz tartozó csöveket.
     * 
     * @param pipes A csomópont kezdeti csövei.
     */
    public void InitializePipes(Pipe... pipes){ this.pipes.addAll(Arrays.asList(pipes)); }

    /**
     * Szomszédos cső beállítása.
     * 
     * @param component Az új szomszédos cső
     */
    public void AddNeighbor(Component component) { 
    	Skeleton.Call(this, "AddNeighbor(" + component + ")");
    	this.pipes.add((Pipe) component); 
    	Skeleton.Return();
    }
    
    /**
     * Egy szomszédos cső törlése.
     *
     * @param component A törlendő szomszédos cső
     */
    public void RemoveNeighbor(Component component) {
        Skeleton.Call(this, "RemoveNeighbor(" + component + ")");
        pipes.remove(component);
        Skeleton.Return();
    }
}
