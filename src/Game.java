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
        // TODO: end game
    }

    /**
     * Új kör kezdődik, meghívja a komponensek step() metódusát.
     */
    public void nextRound() {
        // TODO: next round
    }

    /**
     * A körön belül új játékos kerül sorra.
     */
    public void nextPlayer() {
        // TODO: next player
    }
}
