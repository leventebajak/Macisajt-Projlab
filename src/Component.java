import java.util.ArrayList;

public abstract class Component  extends Printable {
    protected static PipelineSystem pipelineSystem;
    protected boolean broken;
    protected final ArrayList<Player> players = new ArrayList<>();

    public Component(String name) { super(name); }

    public void SetBroken(boolean broken) {
        Skeleton.Call(this, "SetBroken(" + broken + ")");
        this.broken = broken;
        Skeleton.Return();
    }
    public static void SetPipelineSystem(PipelineSystem pipelineSystem) { Component.pipelineSystem = pipelineSystem; }
    public void SetPlayers(Player player){ this.players.add(player); }

    public abstract void Step();
    public abstract void AddNeighbor(Component component);
    public abstract void RemoveNeighbor(Component component);
    public abstract int AddWater(int amount);
    public abstract int RemoveWater(int amount);
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
    public void Repair() {
        Skeleton.Call(this, "Repair(): Sikertelen");
        Skeleton.Return();
    }
    public void Leak() {
        Skeleton.Call(this, "Leak(): Sikertelen");
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
