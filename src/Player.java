public class Player extends Printable {
    protected Component component;

    Player(String name) { super(name); }

    public void SetComponent(Component component) { this.component = component; }

    public void Redirect(Pipe source, Pipe destination) {}
    public void Move(Component neighbor) {
        Skeleton.Call(this, "Move()");
        if(neighbor.Accept(this)){
            component.Remove(this);
            SetComponent(neighbor);
        }
        Skeleton.Return();
    }
    public void ReceivePump() {
        Skeleton.Call(this, "ReceivePump(): Sikertelen");
        Skeleton.Return();
    }
}
