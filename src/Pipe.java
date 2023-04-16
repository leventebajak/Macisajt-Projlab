import java.util.ArrayList;

public class Pipe extends Component {
    private final ArrayList<Node> nodes = new ArrayList<>();
    private boolean occupied = false;
    private final int capacity = 1;
    private int waterLevel = 0;

    public Pipe(String name) { super(name); }
    public Pipe() {
        super("newPipe");
        Skeleton.Call(this, "Pipe()");
        Skeleton.Return();
    }

    public void SetOccupied(boolean occupied) {
        Skeleton.Call(this, "SetOccupied(" + occupied + ")");
        this.occupied = occupied;
        Skeleton.Return();
    }
    private void SetWaterLevel(int waterLevel) { this.waterLevel = waterLevel; }
    public void SetPlayers(Player player){
        this.players.add(player);
        this.SetOccupied(true);
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
    	this.nodes.add((Node) component);
    	Skeleton.Return();
    }
    public void RemoveNeighbor(Component component) {
        Skeleton.Call(this, "RemoveNeighbor(" + component + ")");
        nodes.remove(component);
        Skeleton.Return();
    }
    public int AddWater(int amount) {
        Skeleton.Call(this, "AddWater(" + amount + ")");
        SetWaterLevel(Skeleton.IntegerQuestion("A csőben lévő viz mennyisége:"));
        final int added = waterLevel + amount <= capacity ? amount : 0;
        waterLevel += added;
        Skeleton.Return(added);
        return added;
    }
    public int RemoveWater(int amount) {
        Skeleton.Call(this, "RemoveWater(" + amount + ")");
        SetWaterLevel(Skeleton.IntegerQuestion("A csőben lévő viz mennyisége:"));
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
        Pipe newPipe=new Pipe();
        pipelineSystem.AddComponent(newPipe);
        newPipe.AddNeighbor(nodes.get(0)); //neighborNode:Node = nodes.get(0)
        this.RemoveNeighbor(nodes.get(0));  //neighborNode:Node = nodes.get(0)
        pump.AddNeighbor(this);
        pump.AddNeighbor(newPipe);
        newPipe.AddNeighbor(pump);
        this.AddNeighbor(pump);
        Skeleton.Return();
        return true;
    }
}
