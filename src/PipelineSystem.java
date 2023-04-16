import java.util.ArrayList;

public class PipelineSystem extends Printable {
    private int collectedWater = 0;
    private int leakedWater = 0;
    private final ArrayList<Component> components = new ArrayList<>();

    PipelineSystem() {
        super("Game.pipelineSystem");
        Skeleton.Create(this);
        final int springCount = Skeleton.IntegerQuestion("A hegyi források száma:");
        for (int i = 0; i < springCount; i++) {
            Spring newSpring = new Spring("spring" + (1 + i));
            Skeleton.Create(newSpring);
            Skeleton.Return();
            AddComponent(newSpring);
        }
        final int cisternCount = Skeleton.IntegerQuestion("A ciszternák száma:");
        for (int i = 0; i < cisternCount; i++) {
            Cistern newCistern = new Cistern("cistern" + (1 + i));
            Skeleton.Create(newCistern);
            Skeleton.Return();
            AddComponent(newCistern);
        }
        final int pumpCount = Skeleton.IntegerQuestion("A pumpák száma:");
        for (int i = 0; i < pumpCount; i++) {
            Pump newPump = new Pump("pump" + (1 + i));
            Skeleton.Create(newPump);
            Skeleton.Return();
            AddComponent(newPump);
        }
        final int pipeCount = Skeleton.IntegerQuestion("A csövek száma:");
        for (int i = 0; i < pipeCount; i++) {
            Pipe newPipe = new Pipe("pipe" + (1 + i));
            Skeleton.Create(newPipe);
            Skeleton.Return();
            AddComponent(newPipe);
        }
        Skeleton.Return();
    }
    PipelineSystem(String name) { super(name); }

    public void CollectWater(int amount) {
        Skeleton.Call(this, "CollectWater(" + amount + ")");
        collectedWater += amount;
        Skeleton.Return();
    }
    public void LeakWater(int amount) {
        Skeleton.Call(this, "LeakWater(" + amount + ")");
        leakedWater += amount;
        Skeleton.Return();
    }
    public void AddComponent(Component component) {
        Skeleton.Call(this, "AddComponent(" + component + ")");
        components.add(component);
        Skeleton.Return();
    }
}
