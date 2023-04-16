import java.util.ArrayList;
import java.util.Arrays;

public class Pipe extends Component {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private boolean occupied = false;
    private final int capacity = 1;
    private int waterLevel = 0;

    public Pipe(String name) { super(name); }

    private void InitializeWaterLevel(int waterLevel) { this.waterLevel = waterLevel; }
    public void InitializePlayers(Player... players){
        super.InitializePlayers(players);
        if (players.length > 0)
            occupied = true;
    }
    public void InitializeNodes(Node... nodes){ this.nodes.addAll(Arrays.asList(nodes)); }
    public void SetOccupied(boolean occupied) {
        Skeleton.Call(this, "SetOccupied(" + occupied + ")");
        this.occupied = occupied;
        Skeleton.Return();
    }

    public void Step() {
    	Skeleton.Call(this, "Step()");
    	if (Skeleton.TrueFalseQuestion("Törött a cső, vagy nincs bekötve az egyik végpontja?")) {
            pipelineSystem.LeakWater(1);
        }
    	Skeleton.Return();
    }
    
    public void AddNeighbor(Component component) {
        Skeleton.Call(this, "AddNeighbor(" + component + ")");
        nodes.add((Node) component);
        Skeleton.Return();
    }
    public void RemoveNeighbor(Component component) {
        Skeleton.Call(this, "RemoveNeighbor(" + component + ")");
        nodes.remove((Node) component);
        Skeleton.Return();
    }
    public int AddWater(int amount) {
        Skeleton.Call(this, "AddWater(" + amount + ")");
        InitializeWaterLevel(Skeleton.IntegerQuestion("A csőben lévő viz mennyisége:"));
        final int added = waterLevel + amount <= capacity ? amount : 0;
        waterLevel += added;
        Skeleton.Return(added);
        return added;
    }
    public int RemoveWater(int amount) {
        Skeleton.Call(this, "RemoveWater(" + amount + ")");
        InitializeWaterLevel(Skeleton.IntegerQuestion("A csőben lévő viz mennyisége:"));
        final int removed = waterLevel - amount >= 0 ? amount : waterLevel;
        waterLevel -= removed;
        Skeleton.Return(removed);
        return removed;
    }
    @Override
    public boolean Accept(Player player) {
        if(Skeleton.TrueFalseQuestion("Foglalt a cső?")){
            Skeleton.Call(this, "Accept(" + player + "): Sikertelen" );
            Skeleton.Return(false);
            return false;
        } else {
            Skeleton.Call(this, "Accept(" + player + "): Sikeres");
            SetOccupied(true);
            AddPlayer(player);
            Skeleton.Return(true);
            return true;
        }
    }
    @Override
    public void Remove(Player player) {
        Skeleton.Call(this, "Remove(" + player + ")");
        players.remove(player);
        SetOccupied(false);
        Skeleton.Return();
    }
    @Override
    public void Repair() {
        Skeleton.Call(this, "Repair()");
        SetBroken(false);
        Skeleton.Return();
    }
    @Override
    public void Leak() {
        Skeleton.Call(this, "Leak()");
        SetBroken(true);
        Skeleton.Return();
    }
    @Override
    public boolean PlacePump(Pump pump) {
        Skeleton.Call(this, "PlacePump(" + pump + ")");
        Pipe newPipe = new Pipe("newPipe");
        Skeleton.Create(newPipe);
        Skeleton.Return();
        pipelineSystem.AddComponent(newPipe);
        newPipe.AddNeighbor(nodes.get(0));
        RemoveNeighbor(nodes.get(0));
        newPipe.AddNeighbor(pump);
        pump.AddNeighbor(newPipe);
        pump.AddNeighbor(this);
        AddNeighbor(pump);
        Skeleton.Return();
        return true;
    }
}
