import java.util.ArrayList;

/**
 * A csőrendszert megvalósító osztály.
 * Tárolja a csőrendszer komponenseit, és számon tartja, hogy
 * mennyi víz gyűlt össze, illetve hogy mennyi víz szivárgott ki.
 */
public class PipelineSystem extends Printable {

    /**
     * A begyűjött víz összmennyísége, kezdetben 0 értékű
     */
    private int collectedWater = 0;

    /**
     * A kifolyt víz összmennyísége, kezdetben 0 értékű
     */
    private int leakedWater = 0;

    /**
     * A csővezetékrendszer komponens tárolója
     */
    private final ArrayList<Component> components = new ArrayList<>();

    /**
     * A csővezetékrendszer egy konstruktora, amely létrehozz adott számú hegyet, ciszternát, pumpát és csövet
     */
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
    /**
     * A csővezetékrendszer egy konstruktora
     * @param name A csővezetékrendszer neve
     */
    PipelineSystem(String name) { super(name); }

    /**
     * A begyűjtött víz hozzáadása a csővezetékrendszer begyűjtött víz számlálójához
     * @param amount A begyűjtött víz mennyisége
     */
    public void CollectWater(int amount) {
        Skeleton.Call(this, "CollectWater(" + amount + ")");
        collectedWater += amount;
        Skeleton.Return();
    }

    /**
     * A kifolyt víz hozzáadása a csővezetékrendszer kifolyt víz számlálójához
     * @param amount A kifolyt víz mennyisége
     */
    public void LeakWater(int amount) {
        Skeleton.Call(this, "LeakWater(" + amount + ")");
        leakedWater += amount;
        Skeleton.Return();
    }
    /**
     * Egy komponens felvétele a csővezetékrendszerhez
     * @param component A hozzáadni kívánt komponens
     */
    public void AddComponent(Component component) {
        Skeleton.Call(this, "AddComponent(" + component + ")");
        components.add(component);
        Skeleton.Return();
    }
}
