public class Pump extends Node {
    /**
     * A pumpa állandó víz kapacítása ami 1 értéket vesz fel
     */
    private final int capacity = 1;

    /**
     * A pumpában tárolt víz mennyísége
     */
    private int waterLevel;

    /**
     * A pumpa élettartalma
     */
    private int lifetime;

    /**
     * A pumpa forrás csőve
     */
    private Pipe source;

    /**
     * A cél forrás csőve
     */
    private Pipe destination;

    /**
     * A pumpa konstruktora.
     *
     * @param component: A pumpa neve
     */
    public Pump(String name) { super(name); }

    /**
     * A pumpában levő víz mennyiségének beállítása
     *
     * @param waterLevel A kezdeti víz mennyiségének beállítása
     */
    private void InitializeWaterLevel(int waterLevel) { this.waterLevel = waterLevel; }

    /**
     * A pumpa élettartalmának beállítása
     */
    private void SetLifetime() {
        Skeleton.Call(this, "SetLifetime()");
        lifetime = Skeleton.IntegerQuestion("Az új élettartam:");
        Skeleton.Return();
    }

    /**
     * A pumpa forrás csővének beállítása
     *
     * @param source Az új forrás cső ahonnan kapni fogja a vizet a pumpa
     */
    private void SetSource(Pipe source) {
        Skeleton.Call(this, "SetSource(" + source + ")");
        this.source = source;
        Skeleton.Return();
    }

    /**
     * A pumpa cél csővének beállítása
     *
     * @param source Az új cél cső ahova adni fogja a vizet
     */
    private void SetDestination(Pipe destination) {
        Skeleton.Call(this, "SetDestination(" + destination + ")");
        this.destination = destination;
        Skeleton.Return();
    }

    /**
     * A pumpa léptetése
     * A pumpa vizet kap egy csőtől és vizet továbbít egy szomszédos csőnek
     * Ha lejár az élettartalma a pumpának akkor elromlik
     */
    public void Step() {
        Skeleton.Call(this, "Step()");
        DecreaseLifetime();

        if (Skeleton.TrueFalseQuestion("A pumpa életteartama elérte a 0-t?"))
            SetBroken(true);

        if (!broken) {
            InitializeWaterLevel(Skeleton.IntegerQuestion("A pumpában lévő viz mennyisége:"));

            if (Skeleton.TrueFalseQuestion("Tartozik a pumpához cső, amiből a pumpa szívja a vizet?")) {
                source = new Pipe(name + ".source");
                AddWater(source.RemoveWater(waterLevel + 1 <= capacity ? 1 : 0));
            }
            if (Skeleton.TrueFalseQuestion("Tartozik a pumpához cső, amelybe a pumpa nyomja a vizet?")) {
                destination = new Pipe(name + ".destination");
                RemoveWater(destination.AddWater(waterLevel - 1 >= 0 ? 1 : waterLevel));
            } else {
                pipelineSystem.LeakWater(1);
                RemoveWater(1);
            }
        }
        Skeleton.Return();
    }

    /**
     * A pumpa életerejének a csökkentése 1 egységgel
     */
    private void DecreaseLifetime() {
    	Skeleton.Call(this, "DecreaseLifetime()");
		lifetime -= 1;
		Skeleton.Return();
	}

    /**
     * A pumpához egy adott mennyiségű víz hozzáadása
     *
     * @param amount A hozzáadandó víz mennyisége
     * @return A hozzáadott víz mennyisége
     */
	public int AddWater(int amount) {
		Skeleton.Call(this, "AddWater(" + amount + ")");
        final int added = waterLevel + amount <= capacity ? amount : 0;
        waterLevel += added;
        Skeleton.Return(added);
        return added;
	}

    /**
     * A pumpából egy adott mennyiségű víz eltávolítása
     *
     * @param amount A eltávolítandó víz mennyisége
     * @return Az eltávolított víz mennyisége
     */
    public int RemoveWater(int amount) { 
    	Skeleton.Call(this, "RemoveWater(" + amount + ")");
        final int removed = waterLevel - amount >= 0 ? amount : waterLevel;
        waterLevel -= removed;
        Skeleton.Return(removed);
        return removed;
    }

    /**
     * A cső megjavítása amit a szerelő tudja meghívni
     */
    @Override
    public void Repair() {
        Skeleton.Call(this, "Repair(): Sikeres");
        SetBroken(false);
        SetLifetime();
        Skeleton.Return();
    }

    /**
     * A pumpa irányának átállítása
     *
     * @param source Az új forrás cső
     * @param destination Az új cél cső
     */
    @Override
    public void Redirect(Pipe source, Pipe destination) {
        Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikeres");
        SetSource(source);
        SetDestination(destination);
        Skeleton.Return();
    }

    /**
     * Egy cső megfogása az adott pumpánál
     *
     * @param pipe A megfogandó cső
     * @return Ha sikerült megfogni akkor igaz egyébként hamis
     */
    @Override
    public boolean GrabPipe(Pipe pipe) {
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső foglalt?")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
            Skeleton.Return(false);
            return false;
        }

        Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső megegyezik a forrás csővel?")) {
            SetSource(null);
        }
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső megegyezik a cél csővel?")) {
            SetDestination(null);
        }
        pipe.SetOccupied(true);
        pipe.RemoveNeighbor(this);
        RemoveNeighbor(pipe);
        Skeleton.Return(true);
        return true;

    }

    /**
     * Egy cső lerakása az adott pumpánál
     *
     * @param pipe A lerakandó cső
     * @return Ha sikerült lerakni akkor igaz egyébként hamis
     */
    @Override
    public boolean PlacePipe(Pipe pipe) {
        Skeleton.Call(this, "PlacePipe(" + pipe + "): Sikeres");
        pipe.AddNeighbor(this);
        AddNeighbor(pipe);
        pipe.SetOccupied(false);
        Skeleton.Return(true);
        return true;
    }
}
