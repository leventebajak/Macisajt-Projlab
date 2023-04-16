import java.util.ArrayList;
import java.util.Arrays;

public abstract class Node extends Component {
    protected final ArrayList<Pipe> pipes = new ArrayList<>();

    public Node(String name) { super(name); }

    public void InitializePipes(Pipe... pipes){ this.pipes.addAll(Arrays.asList(pipes)); }

    public void AddNeighbor(Component component) { 
    	Skeleton.Call(this, "AddNeighbor(" + component + ")");
    	this.pipes.add((Pipe) component); 
    	Skeleton.Return();
    }
    public void RemoveNeighbor(Component component) {
        Skeleton.Call(this, "RemoveNeighbor(" + component + ")");
        pipes.remove(component);
        Skeleton.Return();
    }
}
