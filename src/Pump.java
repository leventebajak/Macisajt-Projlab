public class Pump extends Node {
    private final int capacity = 1;
    private int waterLevel;
    private int lifetime;
    private Pipe source;
    private Pipe destination;

    public Pump(String name) { super(name); }

    private void SetWaterLevel(int waterLevel) { this.waterLevel = waterLevel; }
    private void SetLifetime(int lifetime) { this.lifetime = lifetime; }
    private void SetSource(Pipe source) { this.source = source; }
    private void SetDestination(Pipe destination) { this.destination = destination; }

    @Override
    public void Step() {}
    @Override
    public int AddWater(int amount) { return amount; }
    @Override
    public int RemoveWater(int amount) { return amount; }
    @Override
    public void Repair() {}
    @Override
    public void Redirect(Pipe source, Pipe destination) {}
    @Override
    public boolean GrabPipe(Pump pump) { return false; }
    @Override
    public boolean PlacePipe(Pump pump) { return false; }
}
