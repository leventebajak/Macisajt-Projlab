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
    public void step() {
        for (var pipe : pipes)
            pipe.addWater(PIPELINE_SYSTEM.flowRate);
    }

    /**
     * Ennek a függvénynek a törzse üres, mert a hegyi forrásokba nem pumpálható víz.
     *
     * @param amount a bejövő víz mennyisége
     * @return minden esetben 0
     */
    public int addWater(int amount) {
        return 0;
    }

    /**
     * Víz szívása a hegyi forrásból.
     *
     * @param amount a kimenő víz mennyisége
     * @return minden esetben a paraméterként kapott szám
     */
    public int removeWater(int amount) {
        return Math.min(amount, PIPELINE_SYSTEM.flowRate);
    }

    /**
     * Játékos fogadása a mezőre. A játékosok nem tudnak hegyi forrásra lépni.
     *
     * @param player a fogadott játékos
     * @return minden esetben hamis
     */
    @Override
    public boolean accept(Player player) {
        return false;
    }

    /**
     * Új hegyi forrás létrehozása a megadott névvel és csomópontokkal.
     *
     * @param args
     * @return
     */
    public static Spring NEW(String[] args) {
        // TODO: new spring
        return null;
    }

    /**
     * Hegyi forrás tulajdonságainak lekérdezése.
     *
     * @param args
     * @return
     */
    public String stat(String[] args) {
        // TODO: stat spring
        return null;
    }
}
