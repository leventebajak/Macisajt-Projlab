import java.io.Serializable;
import java.util.ArrayList;

/**
 * A csőrendszert megvalósító osztály.
 * Tárolja a csőrendszer komponenseit, és számon tartja, hogy
 * mennyi víz gyűlt össze, illetve hogy mennyi víz szivárgott ki.
 */
public class PipelineSystem implements Serializable {

    /**
     * A pumpák, ciszternák és források által pumpált/szívott maximális vízmennyiség a körök végén.
     */
    public final int flowRate = 1;

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
}
