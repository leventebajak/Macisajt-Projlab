public class Component {
    protected final String name;
    public Component(String name) { this.name = name; }

    @Override
    public String toString() { return '[' + name + ':' + getClass().getSimpleName() + ']'; }

    public void Leak() {
        Skeleton.Call(this, "Leak()");
        Skeleton.Return();
    }
}
