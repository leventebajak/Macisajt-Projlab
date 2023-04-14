public class Saboteur extends Player {
    public Saboteur(String name) { super(name); }

    public void Leak() {
        Skeleton.Call(this, "Leak()");
        component.Leak();
        Skeleton.Return();
    }
}
