public class Cistern extends Node {
    public Cistern(String name) { super(name); }

    public void Step() {}
    public int AddWater(int amount) { return amount; }
    public int RemoveWater(int amount) { return amount; }
    @Override
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + "): Sikeres");
        AddPlayer(player);
        player.ReceivePump();
        Skeleton.Return(true);
        return true;
    }
    @Override
    public boolean GrabPipe(Pipe pipe) {
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső foglalt?") || !Skeleton.TrueFalseQuestion("A megfogni kívánt csőnek van szabad vége")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
            Skeleton.Return(false);
            return false;
        } else {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
            pipe.SetOccupied(true);
            Skeleton.Return(true);
            return true;
        }
    }
}
