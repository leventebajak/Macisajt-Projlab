public class Cistern extends Node {
    public Cistern(String name) { super(name); }

    public void Step() {
    	Skeleton.Call(this, "Step()");
        final int pipeCount = Skeleton.IntegerQuestion("A ciszternához csatlakozó csövek száma: ");
        pipes.clear();
        for (int i = 0; i < pipeCount; i++) {
            pipes.add(new Pipe("cistern.pipes[" + i + "]"));
            int removedWater = pipes.get(i).RemoveWater(1);
            this.AddWater(removedWater);
        }
        if (Skeleton.TrueFalseQuestion("Jöjjön létre új cső?")) {
            Pipe newPipe = new Pipe("newPipe");
            Skeleton.Call(newPipe, "Pipe(): Létrehozás");
            Skeleton.Return();
            newPipe.AddNeighbor(this);
            this.AddNeighbor(newPipe);
            pipelineSystem.AddComponent(newPipe);
        }
        Skeleton.Return();	
    }
    public int AddWater(int amount) { 
    	Skeleton.Call(this, "AddWater(" + amount + ")");
    	pipelineSystem.CollectWater(amount);
    	Skeleton.Return();
    	return amount; 
    }
    public int RemoveWater(int amount) {
        Skeleton.Call(this, "AddWater(" + amount + ")");
        Skeleton.Return(0);
        return 0;
    }
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
