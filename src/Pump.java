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
        this.lifetime = Skeleton.IntegerQuestion("Az új élettartam:");
        Skeleton.Return();
    }
    private void SetSource(Pipe source) {
        Skeleton.Call(this, "SetSource(" + source + ")");
        this.source = source;
        Skeleton.Return();
    }
    private void SetDestination(Pipe destination) {
        Skeleton.Call(this, "SetDestination(" + destination + ")");
        this.destination = destination;
        Skeleton.Return();
    }

    public void Step() {
    	Skeleton.Call(this, "Step()");
    	this.DecreaseLifetime();
    	
    	if (Skeleton.TrueFalseQuestion("A pumpa életteartama elérte a 0-t?")) {    		
            this.SetBroken(true);
        }
    	
    	if (!broken) {
            SetWaterLevel(Skeleton.IntegerQuestion("A pumpában lévő viz mennyisége:"));

            if (Skeleton.TrueFalseQuestion("Tartozik a pumpához cső, amiből a pumpa szívja a vizet?")) {
                source = new Pipe(name + ".source");
                AddWater(source.RemoveWater(waterLevel + 1 <= capacity ? 1 : 0));
            }
            if (Skeleton.TrueFalseQuestion("Tartozik a pumpához cső, amelybe a pumpa nyomja a vizet?")) {
                destination = new Pipe(name + ".destination");
                RemoveWater(destination.AddWater(waterLevel - 1 >= 0 ? 1 : waterLevel));
            } else {
            	pipelineSystem.LeakWater(1);
            	this.RemoveWater(1);
            }
        }
        Skeleton.Return();	
    }
    
    private void DecreaseLifetime() {
    	Skeleton.Call(this, "DecreaseLifetime()");
		lifetime -= 1;
		Skeleton.Return();
	}

	public int AddWater(int amount) {
		Skeleton.Call(this, "AddWater(" + amount + ")");
        final int added = waterLevel + amount <= capacity ? amount : 0;
        waterLevel += added;
        Skeleton.Return(added);
        return added;
	}
	
    public int RemoveWater(int amount) { 
    	Skeleton.Call(this, "RemoveWater(" + amount + ")");
        final int removed = waterLevel - amount >= 0 ? amount : waterLevel;
        waterLevel -= removed;
        Skeleton.Return(removed);
        return removed;
    }
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
    public boolean GrabPipe(Pipe pipe) {
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső foglalt?")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
            Skeleton.Return(false);
            return false;
        }

        Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső megegyezik a forrás csővel?")) {
            this.SetSource(null);
        }
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső megegyezik a cél csővel?")) {
            this.SetDestination(null);
        }
        pipe.SetOccupied(true);
        pipe.RemoveNeighbor(this);
        this.RemoveNeighbor(pipe);
        Skeleton.Return(true);
        return true;

    }
    @Override
    public boolean PlacePipe(Pipe pipe) { return false; }
}
