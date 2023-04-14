public class Cistern extends Node {
    public Cistern(String name) { super(name); }

    @Override
    public void Step() {}
    @Override
    public int AddWater(int amount) { return amount; }
    @Override
    public int RemoveWater(int amount) { return amount; }
    @Override
    public boolean Accept(Player player) { return true; }
    @Override
    public boolean GrabPipe(Pump pump) { return false; }
}
