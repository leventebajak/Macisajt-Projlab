public class Spring extends Node {
    public Spring(String name) {
        super(name);
    }

    public void Step() {}
    public int AddWater(int amount) { return amount; }
    public int RemoveWater(int amount) { return amount; }
    @Override
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + "): Sikertelen");
        Skeleton.Return(false);
        return false;
    }
}
