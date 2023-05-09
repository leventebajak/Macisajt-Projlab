import java.util.ArrayList;

/**
 * A Game osztály felelős a játék működéséért.
 * A játék indítása és befejezése, valamint a körök vezérlése ennek az osztálynak a feladata.
 */
public class Game extends Printable {

    /**
     * A csőrendszert reprezentálja. Tárolja a csöveket, pumpákat, ciszternákat és forrásokat.
     */
    public PipelineSystem pipelineSystem = new PipelineSystem("pipelineSystem");

    /**
     * A játékot játszó szabotőr csapat játékosait tárolja.
     */
    public ArrayList<Saboteur> saboteurs = new ArrayList<>();

    /**
     * A játékot játszó szerelő csapat játékosait tárolja.
     */
    public ArrayList<Plumber> plumbers = new ArrayList<>();

    private int activePlayerIndex = 0;

    /**
     * Játék osztály konstruktora.
     *
     * @param name a kiírandó név
     */
    Game(String name) {
        super(name);
    }

    /**
     * Elindítja a játékot, betölti a pályát, és elhelyezi a játékosokat véletlenszerűen a csőrendszer komponensein.
     */
    public void startGame() {
        // TODO: start game
    }

    /**
     * Befejezi a játékot.
     */
    public void endGame() {
        if (pipelineSystem.getCollectedWater() > pipelineSystem.getLeakedWater())
            System.out.println("A szerelő csapat nyert!");
        else if (pipelineSystem.getCollectedWater() < pipelineSystem.getLeakedWater())
            System.out.println("A szabotőr csapat nyert!");
        else
            System.out.println("Döntetlen!");
        System.out.println("Összegyűjtött vízmennyiség: " + pipelineSystem.getCollectedWater());
        System.out.println("Kifolyt vízmennyiség: " + pipelineSystem.getLeakedWater());
    }

    /**
     * Új kör kezdődik, meghívja a komponensek step() metódusát.
     */
    public void nextRound() {
        for (var component : pipelineSystem.components)
            component.step();
        for (var plumber : plumbers)
            plumber.step();
        for (var saboteur : saboteurs)
            saboteur.step();
    }

    /**
     * A körön belül új játékos kerül sorra.
     */
    public void nextPlayer() {
        // TODO: next player
        activePlayerIndex += 1;
        if (activePlayerIndex >= plumbers.size() + saboteurs.size())
            activePlayerIndex = 0;
    }

    /**
     * Játék tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
        // TODO: stat game
        return null;
    }

    /**
     * Játék tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        // TODO: set game
    }
}
