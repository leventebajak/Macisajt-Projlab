import java.util.ArrayList;

public abstract class Node extends Component {
    protected final ArrayList<Pipe> pipes = new ArrayList<>();

    public Node(String name) { super(name); }

    public void AddNeighbor(Component component) {
        pipes.add((Pipe)component);
    }
    public void RemoveNeighbor(Component component) {
        Skeleton.Call(this, "RemoveNeighbor(" + component + ")");
        pipes.remove(component);
        Skeleton.Return();
    }
}
