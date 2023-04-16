import java.util.ArrayList;

public abstract class Node extends Component {
    private final ArrayList<Pipe> pipes = new ArrayList<>();

    public Node(String name) { super(name); }

    public void AddNeighbor(Component component) {}
    public void RemoveNeighbor(Component component) {}
}
