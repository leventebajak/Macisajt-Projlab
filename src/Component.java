import java.util.ArrayList;
import java.util.Arrays;

public abstract class Component  extends Printable {
    protected static PipelineSystem pipelineSystem;
    protected boolean broken;
    protected final ArrayList<Player> players = new ArrayList<>();

    public Component(String name) {
        super(name);
    }

    public void InitializePlayers(Player... players) {
        this.players.addAll(Arrays.asList(players));
    }

    public void SetBroken(boolean broken) {
        Skeleton.Call(this, "SetBroken(" + broken + ")");
        this.broken = broken;
        Skeleton.Return();
    }

    public static void SetPipelineSystem(PipelineSystem pipelineSystem) {
        Component.pipelineSystem = pipelineSystem;
    }

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
        Skeleton.Call(this, "Redirect(" + source + ", " + destination + "): Sikertelen");
        Skeleton.Return();
    }

    public boolean PlacePump(Pump pump) {
        Skeleton.Call(this, "PlacePump(" + pump + ")");
        Skeleton.Return(false);
        return false;
    }

    public boolean GrabPipe(Pipe pipe) {
        Skeleton.Call(this, "GrabPipe(" + pipe + ")");
        Skeleton.Return(false);
        return false;
    }

    public boolean PlacePipe(Pipe pipe) {
        Skeleton.Call(this, "PlacePipe(" + pipe + ")");
        Skeleton.Return(false);
        return false;
    }

    protected void AddPlayer(Player player) {
        Skeleton.Call(this, "AddPlayer(" + player + ")");
        players.add(player);
        Skeleton.Return();
    }
}
