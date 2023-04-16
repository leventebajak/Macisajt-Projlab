public class Pump extends Node {
    private final int capacity = 1;
    private int waterLevel;
    private int lifetime;
    private Pipe source;
    private Pipe destination;

    public Pump(String name) { super(name); }

    private void SetWaterLevel(int waterLevel) { this.waterLevel = waterLevel; }
    private void SetLifetime() {
        Skeleton.Call(this, "SetLifetime()");
        this.lifetime = 100; //A lifetime értéke bármekkora lehet, még nem specifikáltuk
        Skeleton.Return();
    }
    private void SetSource(Pipe source) { this.source = source; }
    private void SetDestination(Pipe destination) { this.destination = destination; }

    @Override
    public void Step() {}
    @Override
    public int AddWater(int amount) { return amount; }
    @Override
    public int RemoveWater(int amount) { return amount; }
    @Override
    public void Repair() {
        Skeleton.Call(this, "Repair(): Sikeres");
        SetBroken(false);
        SetLifetime();
        Skeleton.Return();
    }
    @Override
    public void Redirect(Pipe source, Pipe destination) {
        Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikeres");
        SetSource(source);
        SetDestination(destination);
        Skeleton.Return();
    }
    @Override
    public boolean GrabPipe(Pipe pipe) { return false; }
    @Override
    public boolean PlacePipe(Pipe pipe) { return false; }
}
