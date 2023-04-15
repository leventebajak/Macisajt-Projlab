import java.util.ArrayList;

public class PipelineSystem extends Printable {
    private final int collectedWater = 0;
    private final int leakedWater = 0;
    private ArrayList<Component> components;

    PipelineSystem(String name) { super(name); }

    public void CollectWater(int amount) {}
    public void LeakWater(int amount) {}
    public void AddComponent(Component component) {}
}
