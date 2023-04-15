public class Plumber extends Player {
    private Pipe grabbedPipe;
    private Pump grabbedPump;

    Plumber(String name) { super(name); }

    @Override
    public void Redirect(Pipe source, Pipe destination) {}
    public void Repair() {}
    @Override
    public void ReceivePump() { //TODO
        if(grabbedPump==null){
            Skeleton.Call(this, "ReceivePump(): Sikeres");
            //...
        } else {
            Skeleton.Call(this, "ReceivePump(): Sikertelen");
            Skeleton.Return();
        }
    }
    public void PlacePump() {}
    public void GrabPipe(Pipe pipe) {}
    public void PlacePipe() {}
}
