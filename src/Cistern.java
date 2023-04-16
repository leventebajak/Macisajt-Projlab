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
        
        Pipe newPipe = new Pipe("newPipe");
        newPipe.AddNeighbor(this);
        this.AddNeighbor(newPipe);
        this.pipelineSystem.AddComponent(newPipe);
        
        Skeleton.Return();	
    }
    public int AddWater(int amount) { 
    	Skeleton.Call(this, "AddWater(" + amount + ")");
    	this.pipelineSystem.CollectWater(amount);
    	Skeleton.Return();
    	return amount; 
    }
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
