import java.util.ArrayList;

public class Component  extends Printable {
    protected boolean broken;
    protected PipelineSystem pipelineSystem;
    protected ArrayList<Player> players;

    public Component(String name) { super(name); }

    public void SetBroken(boolean broken) {
        Skeleton.Call(this, "SetBroken(" + broken + ")");
        this.broken = broken;
        Skeleton.Return();
    }
    public void SetPipelineSystem(PipelineSystem pipelineSystem) { this.pipelineSystem = pipelineSystem; }

    public void Step() {}
    public void AddNeighbor(Component component) {}
    public void RemoveNeighbor(Component component) {}
    public int AddWater(int amount) { return amount; }
    public int RemoveWater(int amount) { return amount; }
    public boolean Accept(Player player) { return true; }
    public void Remove(Player player) {}
    public void Repair() {}
    public void Leak() {
        Skeleton.Call(this, "Leak()");
        Skeleton.Return();
    }
    public void Redirect(Pipe source, Pipe destination) {}
    public boolean PlacePump(Pump pump) { return false; }
    public boolean GrabPipe(Pump pump) { return false; }
    public boolean PlacePipe(Pump pump) { return false; }

    private void AddPlayer(Player player) {}
}
