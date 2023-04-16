import java.util.ArrayList;

public class Component  extends Printable {
    protected boolean broken;
    protected PipelineSystem pipelineSystem;
    protected ArrayList<Player> players = new ArrayList<>();

    public Component(String name) { super(name); }

    public void SetBroken(boolean broken) {
        Skeleton.Call(this, "SetBroken(" + broken + ")");
        this.broken = broken;
        Skeleton.Return();
    }
    public void SetPipelineSystem(PipelineSystem pipelineSystem) { this.pipelineSystem = pipelineSystem; }
    public void SetPlayers(Player player){ this.players.add(player); }

    public void Step() {}
    public void AddNeighbor(Component component) {}
    public void RemoveNeighbor(Component component) {}
    public int AddWater(int amount) { return amount; }
    public int RemoveWater(int amount) { return amount; }
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + ")");
        AddPlayer(player);
        Skeleton.Return(true);
        return true;
    }
    public void Remove(Player player) {
        Skeleton.Call(this, "Remove(" + player + ")");
        players.remove(player);
        Skeleton.Return();
    }
    public void Repair() {}
    public void Leak() {
        Skeleton.Call(this, "Leak()");
        Skeleton.Return();
    }
    public void Redirect(Pipe source, Pipe destination) {
        Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikertelen");
        Skeleton.Return();
    }
    public boolean PlacePump(Pump pump) { return false; }
    public boolean GrabPipe(Pipe pipe) { return false; }
    public boolean PlacePipe(Pipe pipe) { return false; }

    protected void AddPlayer(Player player) {
        Skeleton.Call(this, "AddPlayer(" + player + ")");
        players.add(player);
        Skeleton.Return();
    }
}
