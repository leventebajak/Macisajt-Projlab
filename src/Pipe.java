public class Pipe extends Component {
    public Pipe(String name) { super(name); }

    public void Leak() {
        Skeleton.Call(this, "Leak()");
        Skeleton.Return();
    }
}
