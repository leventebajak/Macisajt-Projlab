import java.util.ArrayList;

public class Node extends Component {
    private ArrayList<Pipe> pipes;

    public Node(String name) { super(name); }

    @Override
    public void AddNeighbor(Component component) {}
    @Override
    public void RemoveNeighbor(Component component) {}
}
