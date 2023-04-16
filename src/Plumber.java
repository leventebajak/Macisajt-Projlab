public class Plumber extends Player {
    private Pipe grabbedPipe;
    private Pump grabbedPump;

    Plumber(String name) { super(name); }

    public void SetGrabbedPump(Pump pump) {
        Skeleton.Call(this, "SetGrabbedPump(" + pump + ")");
        this.grabbedPump = pump;
        Skeleton.Return();
    }
    public void SetGrabbedPipe(Pipe pipe) {
        Skeleton.Call(this, "SetGrabbedPipe(" + pipe + ")");
        this.grabbedPipe = pipe;
        Skeleton.Return();
    }

    @Override
    public void Redirect(Pipe source, Pipe destination) {
        if (Skeleton.TrueFalseQuestion("Van pumpa a szerelőnél?")){
            Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikertelen");
        } else {
            Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikeres");
            component.Redirect(source, destination);
        }
        Skeleton.Return();
    }
    public void Repair() {
        if (Skeleton.TrueFalseQuestion("Van pumpa a szerelőnél?")) {
            Skeleton.Call(this, "Repair(): Sikertelen");
        } else {
            Skeleton.Call(this, "Repair(): Sikeres");
            component.Repair();
        }
        Skeleton.Return();

    }
    @Override
    public void ReceivePump() {
        if (Skeleton.TrueFalseQuestion("Van pumpa a szerelőnél?")) {
            Skeleton.Call(this, "ReceivePump(): Sikertelen");
        } else {
            Skeleton.Call(this, "ReceivePump(): Sikeres");
            Pump newPump = new Pump("newPump");
            Skeleton.Call(newPump, "Pump(): Létrehozás");
            Skeleton.Return();
            SetGrabbedPump(newPump);
        }
        Skeleton.Return();
    }
    public void PlacePump() {}
    public void GrabPipe(Pipe pipe) {
        if (Skeleton.TrueFalseQuestion("Van cső a szerelőnél?")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
        } else {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
            if (component.GrabPipe(pipe))
                SetGrabbedPipe(pipe);
        }
        Skeleton.Return();
    }
    public void PlacePipe() {}
}
