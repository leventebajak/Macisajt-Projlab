public class Spring extends Node {
    public Spring(String name) {
        super(name);
    }

    public void Step() {
        Skeleton.Call(this, "Step()");
        final int pipeCount = Skeleton.IntegerQuestion("A forráshoz csatlakozó csövek száma: ");
        pipes.clear();
        for (int i = 0; i < pipeCount; i++) {
            pipes.add(new Pipe("spring.pipes[" + i + "]"));
            pipes.get(i).AddWater(1);
        }
        Skeleton.Return();
    }
    public int AddWater(int amount) { return amount; }
    public int RemoveWater(int amount) { return amount; }
    @Override
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + "): Sikertelen");
        Skeleton.Return(false);
        return false;
    }
}
