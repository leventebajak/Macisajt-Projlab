import java.util.ArrayList;

/**
 * A csőrendszert megvalósító osztály.
 * Tárolja a csőrendszer komponenseit, és számon tartja, hogy
 * mennyi víz gyűlt össze, illetve hogy mennyi víz szivárgott ki.
 */
public class PipelineSystem extends Printable {

    /**
     * A pumpák, ciszternák és források által pumpált/szívott maximális vízmennyiség a körök végén.
     */
    public final int flowRate = 1;

    /**
     * A játék megnyeréséhez szükséges pontszám.
     */
    public final int targetAmount = 100;

    /**
     * A begyűjött víz összmennyísége, kezdetben 0 értékű
     */
    private int collectedWater = 0;

    /**
     * A kifolyt víz összmennyísége, kezdetben 0 értékű
     */
    private int leakedWater = 0;

    /**
     * A csővezetékrendszer komponens tárolója
     */
    public final ArrayList<Component> components = new ArrayList<>();

    /**
     * A csővezetékrendszer konstruktora.
     *
     * @param name A csővezetékrendszer neve
     */
    PipelineSystem(String name) {
        super(name);
    }

    /**
     * Az összegyűjtött vízmennyiség lekérdezése.
     *
     * @return az összegyűjtött vízmennyiség
     */
    public int getCollectedWater() {
        return collectedWater;
    }

    /**
     * A kifolyt vízmennyiség lekérdezése.
     *
     * @return a kifolyt vízmennyiség
     */
    public int getLeakedWater() {
        return leakedWater;
    }

    /**
     * A begyűjtött víz hozzáadása a csővezetékrendszer begyűjtött víz számlálójához.
     *
     * @param amount A begyűjtött víz mennyisége
     */
    public void collectWater(int amount) {
        collectedWater += amount;
    }

    /**
     * A kifolyt víz hozzáadása a csővezetékrendszer kifolyt víz számlálójához.
     *
     * @param amount A kifolyt víz mennyisége
     */
    public void leakWater(int amount) {
        leakedWater += amount;
    }

    /**
     * Egy komponens felvétele a csővezetékrendszerbe.
     *
     * @param component A hozzáadni kívánt komponens
     */
    public void addComponent(Component component) {
        components.add(component);
    }

    /**
     * Csőrendszer tulajdonságainak lekérdezése.
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
        case "collectedwater" -> { return "collectedWater: " + collectedWater; }
        case "leakwater" ->  { return "LeakWater: " + leakedWater; }
        case "components" ->  { 
        	attr.concat("components:");
        	for(Component c : components)
        		attr.concat(" "+ c.name);
        	}
        default -> { 
        	throw new IllegalArgumentException("A csőrendszernek nincs ilyen nevű tulajdonsága"); 
        	}
    	}
    	return attr;
    }

    /**
     * Csőrendszer tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        // TODO: set PipeLine
    }
}
