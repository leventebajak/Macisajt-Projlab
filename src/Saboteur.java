/**
 * Szabotőr csapat játékosait megvalósító osztály. Felelőssége a csövek kilyukasztása.
 */
public class Saboteur extends Player {

    /**
     * Szabotőr konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Saboteur(String name) {
        super(name);
    }

    /**
     * A jelenlegi mező csúszóssá tételének megkísérlése.
     */
    public void makeItSlippery() {
        component.makeItSlippery();
    }

    /**
     * Új szabotőr létrehozása a megadott névvel és csomópontokkal.
     *
     * @param args
     * @return
     */
    public static Saboteur NEW(String[] args) {
        // TODO: new saboteur
        return null;
    }

    /**
     * Szabotőr tulajdonságainak lekérdezése.
     *
     * @param args
     * @return
     */
    public String stat(String[] args) {
        // TODO: stat saboteur
        return null;
    }

    /**
     * Szabotőr tulajdonságainak beállítása.
     *
     * @param args
     */
    public void set(String[] args) {
        // TODO: set saboteur
    }

    /**
     * Szabotőr paraméterként kapott nevű képességének használata.
     *
     * @param args
     */
    public void playerUse(String[] args) {
        // TODO: playeruse saboteur
    }
}
