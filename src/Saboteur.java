public class Saboteur {
    private Component component;
    private final String name;
    public Saboteur(String name) { this.name = name; }

    public void SetComponent(Component component) { this.component = component; }

    @Override
    public String toString() { return '[' + name + ':' + getClass().getSimpleName() + ']'; }

    public void Leak() {
        Skeleton.Call(this, "Leak()");
        component.Leak();
        Skeleton.Return();
    }
}
