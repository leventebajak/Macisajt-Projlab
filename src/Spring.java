/** 
 * A hegyi forrásokat valósítja meg. Felelőssége a víz pumpálása minden szomszédos csőbe minden kör elején.
 */
public class Spring extends Node {
	
	/**
     * Hegyi forrás konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Spring(String name) {
        super(name);
    }

    /**
     * Víz pumpálása minden szomszédos csőbe.
     */
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
    
    /**
     * Ennek a függvénynek a törzse üres, mert a hegyi forrásokba nem pumpálható víz. 
     * 
     * @param amount a bejövő víz mennyisége
     * @return minden esetben 0
     */
    public int AddWater(int amount) { 
    	Skeleton.Call(this, "AddWater(" + amount + ")");
        Skeleton.Return(0);
        return 0;
    }
    
    /**
     * Víz szívása a hegyi forrásokból.
     * 
     * @param amount a kimenő víz mennyisége
     * @return minden esetben a paraméterként kapott szám
     */
    public int RemoveWater(int amount) { 
    	Skeleton.Call(this, "RemoveWater(" + amount + ")");
    	Skeleton.Return(amount);
    	return amount; 
    }
    
    /**
     * Játékos fogadása a mezőre. A játékosok nem tudnak hegyi forrásra lépni.
     * 
     * @param player a fogadott játékos
     * @return minden esetben hamis
     */
    @Override
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + "): Sikertelen");
        Skeleton.Return(false);
        return false;
    }
}
