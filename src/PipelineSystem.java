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
     * A begyűjtött víz hozzáadása a csővezetékrendszer begyűjtött víz számlálójához.
     *
     * @param amount A begyűjtött víz mennyisége
     */
    public void collectWater(int amount) {
        collectedWater += amount;
    }

    /**
     * A kifolyt víz hozzáadása a csővezetékrendszer kifolyt víz számlálójához
     *
     * @param amount A kifolyt víz mennyisége
     */
    public void leakWater(int amount) {
        leakedWater += amount;
    }

    /**
     * Egy komponens felvétele a csővezetékrendszerhez
     *
     * @param component A hozzáadni kívánt komponens
     */
    public void addComponent(Component component) {
        components.add(component);
    }

    /**
     * Csőrendszer tulajdonságainak lekérdezése.
     *
     * @param args
     * @return
     */
    public String stat(String[] args) {
        // TODO: stat pipeLine
        return null;
    }

    /**
     * Csőrendszer tulajdonságainak beállítása.
     *
     * @param args
     */
    public void set(String[] args) {
        // TODO: set PipeLine
    }
}
