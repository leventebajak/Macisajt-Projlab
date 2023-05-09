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
        // TODO: new saboteur
        return null;
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
