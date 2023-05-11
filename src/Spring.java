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
     * @param args a parancs elvárt paraméterei: {@code new spring [hegyi forrás neve]}
     * @return a létrehozott hegyi forrás referenciája
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public static Spring NEW(String[] args) throws IllegalArgumentException {
        if (args.length == 3) {
            if (Prototype.OBJECTS.containsKey(args[2])) throw new IllegalArgumentException("A név már foglalt!");
            return new Spring(args[2]);
        }
        if (args.length == 2) {
            int i = 1;
            while (Prototype.OBJECTS.containsKey("spring" + i)) i++;
            return new Spring("spring" + i);
        }
        throw new IllegalArgumentException("Érvénytelen paraméter!");
    }

    /**
     * Hegyi forrás tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
    	String attr = new String();
    	args[2] = args[2].strip().toLowerCase();
    	switch (args[2]) {
        case "broken" ->  { return "broken: false"; }
        case "leakable" ->  { return "leakable: true"; }
        case "slippery" ->  { return "slippery: false"; }
        case "sticky" ->  { return "sticky: false"; }
        case "pipes" ->  { 
        	attr.concat("pipes:");
        	for(Pipe p : pipes)
        		attr.concat(" "+ p.name);
        	}        	
        case "players" ->  { 
        	attr.concat("players:");
        	for(Player p : players)
        		attr.concat(" "+ p.name);
        	}
        default -> { 
        	throw new IllegalArgumentException("A hegyi forrásnak nincs ilyen nevű tulajdonsága"); 
        	}
    	}
    	return attr;
    }

    /**
     * Hegyi forrás tulajdonságai beállításának megkísérlése.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        throw new IllegalArgumentException("A hegyi forrásnak nincs állítható tulajdonsága!");
    }
}
