import java.util.ArrayList;

public class Pipe extends Component {
    private ArrayList<Node> nodes;
    private boolean occupied = false;
    private final int capacity = 1;
    private int waterLevel;

    public Pipe(String name) { super(name); }

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
    @Override
    public void Step() {}
    @Override
    public void AddNeighbor(Component component) {}
    @Override
    public void RemoveNeighbor(Component component) {}
    @Override
    public int AddWater(int amount) { return amount; }
    @Override
    public int RemoveWater(int amount) { return amount; }
    @Override
    public boolean Accept(Player player) {
        if(!occupied){
            Skeleton.Call(this, "Accept(" + player + "): Sikeres");
            SetOccupied(true);
            AddPlayer(player);
            Skeleton.Return(true);
            return true;
        } else {
            Skeleton.Call(this, "Accept(" + player + "): Sikertelen" );
            Skeleton.Return(false);
            return false;
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
        Skeleton.Call(this, "Repair(): Sikeres");
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
    public boolean PlacePump(Pump pump) { return false; }
}
