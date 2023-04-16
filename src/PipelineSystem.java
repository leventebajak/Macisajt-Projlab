import java.util.ArrayList;

public class PipelineSystem extends Printable {
    private final int collectedWater = 0;
    private final int leakedWater = 0;
    private final ArrayList<Component> components = new ArrayList<>();

    PipelineSystem() {
        super("Game.pipelineSystem");
        Skeleton.Call(this, "PipelineSystem(): Létrehozás");
        final int springCount = Skeleton.IntegerQuestion("A hegyi források száma: ");
        for (int i = 0; i < springCount; i++) {
            Spring newSpring = new Spring("spring" + (1 + i));
            Skeleton.Call(newSpring, "Spring(): Létrehozás");
            Skeleton.Return();
            AddComponent(newSpring);
        }
        final int cisternCount = Skeleton.IntegerQuestion("A ciszternák száma: ");
        for (int i = 0; i < cisternCount; i++) {
            Cistern newCistern = new Cistern("cistern" + (1 + i));
            Skeleton.Call(newCistern, "Cistern(): Létrehozás");
            Skeleton.Return();
            AddComponent(newCistern);
        }
        final int pumpCount = Skeleton.IntegerQuestion("A pumpák száma: ");
        for (int i = 0; i < pumpCount; i++) {
            Pump newPump = new Pump("pump" + (1 + i));
            Skeleton.Call(newPump, "Pump(): Létrehozás");
            Skeleton.Return();
            AddComponent(newPump);
        }
        final int pipeCount = Skeleton.IntegerQuestion("A csövek száma: ");
        for (int i = 0; i < pipeCount; i++) {
            Pipe newPipe = new Pipe("pipe" + (1 + i));
            Skeleton.Call(newPipe, "Pipe(): Létrehozás");
            Skeleton.Return();
            AddComponent(newPipe);
        }
        Skeleton.Return();
    }
    PipelineSystem(String name) { super(name); }

    public void CollectWater(int amount) {}
    public void LeakWater(int amount) {}
    public void AddComponent(Component component) {
        Skeleton.Call(this, "AddComponent(" + component + ")");
        components.add(component);
        Skeleton.Return();
    }
}
