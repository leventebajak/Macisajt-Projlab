public class Cistern extends Node {
    public Cistern(String name) { super(name); }

    @Override
    public void Step() {}
    @Override
    public int AddWater(int amount) { return amount; }
    @Override
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
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső foglalt?")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
            Skeleton.Return("grabbed=false");
            return false;
        } else {
            if (Skeleton.TrueFalseQuestion("A megfogni kívánt csőnek van szabad vége")) {
                Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
                pipe.SetOccupied(true);
                Skeleton.Return("grabbed=true");
                return true;
            } else {
                Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
                Skeleton.Return("grabbed=false");
                return false;
            }
        }

    }
}
