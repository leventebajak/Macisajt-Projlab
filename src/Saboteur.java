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
     * Új szabotőr létrehozása a megadott névvel és csomóponttal.
     *
     * @param args a parancs elvárt paraméterei: {@code new saboteur <szabotőr neve> <kezdő csomópont neve>}
     * @return a létrehozott szabotőr referenciája
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public static Saboteur NEW(String[] args) throws IllegalArgumentException {
        if (args.length != 4) throw new IllegalArgumentException("Érvénytelen paraméter!");
        if (Prototype.OBJECTS.containsKey(args[2])) throw new IllegalArgumentException("A név már foglalt!");
        if (!Prototype.OBJECTS.containsKey(args[3]))
            throw new IllegalArgumentException("Nem létezik komponens a megadott névvel!");
        try {
            Component component = (Component) Prototype.OBJECTS.get(args[3]);
            Saboteur saboteur = new Saboteur(args[2]);
            saboteur.component = component;
            if (!component.accept(saboteur)) {
                Prototype.OBJECTS.remove(args[2]);
                throw new IllegalArgumentException("A komponens nem tudja fogadni a szabotőrt!");
            }
            return saboteur;
        } catch (ClassCastException ignored) {
            throw new IllegalArgumentException("Nem létezik komponens a megadott névvel!");
        }
    }

    /**
     * Szabotőr tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
        // TODO: stat saboteur
        return null;
    }

    /**
     * Szabotőr tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        // TODO: set saboteur
    }

    /**
     * Szabotőr paraméterként kapott nevű képességének használata.
     *
     * @param args a parancs elvárt paraméterei: {@code playeruse <játékos neve> <képesség neve>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void playerUse(String[] args) throws IllegalArgumentException {
        // TODO: playeruse saboteur
    }
}
