public class Player extends Printable {
    protected Component component;

    Player(String name) { super(name); }

    public void SetComponent(Component component) { this.component = component; }

    public void Redirect(Pipe source, Pipe destination) {}
    public void Move(Component neighbor) {}
    public void ReceivePump() {}
}
