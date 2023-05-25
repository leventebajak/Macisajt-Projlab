import javax.swing.*;
import java.util.ArrayList;

/**
 * A Game osztály felelős a játék működéséért.
 * <p>
 * Az MVC mintában ez a Modell osztály.
 */
public class Game {

    public static Game Instance = null;

    public static void NewGame(ArrayList<String> plumberNames, ArrayList<String> saboteurNames) {
        // TODO: új játék létrehozása a kapott játékosnevekkel és az Instance felülírása
        //  itt generálódik a pálya is
    }

    public static Player getActivePlayer() {
        return Instance.players.get(Instance.activePlayerIndex % Instance.players.size());
    }

    public static int getRound() {
        return Instance.activePlayerIndex / Instance.players.size() + 1;
    }

    /**
     * A csőrendszert reprezentálja. Tárolja a csöveket, pumpákat, ciszternákat és forrásokat.
     */
    public PipelineSystem pipelineSystem = new PipelineSystem();

    /**
     * A játékot játszó játékosok gyűjteménye.
     */
    public ArrayList<Player> players = new ArrayList<>();

    private int activePlayerIndex = 0;

    /**
     * Játék osztály konstruktora.
     */
    private Game() {
        Component.PIPELINE_SYSTEM = pipelineSystem;
    }

    /**
     * Elindítja a játékot, betölti a pályát, és elhelyezi a játékosokat véletlenszerűen a csőrendszer komponensein.
     */
    public static void startGame() {
        // TODO: inicializálás és rajzolás
    }

    /**
     * Befejezi a játékot.
     */
    public static void endGame() {
        // TODO: a nyertes csapat és a pontszámok grafikus megjelenítése
        if (Instance.pipelineSystem.getCollectedWater() > Instance.pipelineSystem.getLeakedWater())
            System.out.println("A szerelő csapat nyert!");
        else if (Instance.pipelineSystem.getCollectedWater() < Instance.pipelineSystem.getLeakedWater())
            System.out.println("A szabotőr csapat nyert!");
        else
            System.out.println("Döntetlen!");
        System.out.println("Összegyűjtött vízmennyiség: " + Instance.pipelineSystem.getCollectedWater());
        System.out.println("Kifolyt vízmennyiség: " + Instance.pipelineSystem.getLeakedWater());
    }

    /**
     * Új kör kezdődik, meghívja a komponensek step() metódusát.
     */
    public static void nextRound() {
        for (var component : Instance.pipelineSystem.components)
            component.step();
        for (var player : Instance.players)
            player.step();
    }

    /**
     * A körön belül új játékos kerül sorra.
     */
    public static void nextPlayer() {
        // TODO: következő játékos paneljének betöltése és a pálya újrarajzolása,
        //  új kör kezdése az utolsó játékos után, elegendő pontszám esetén a játék befejezése
        if (Instance.activePlayerIndex == Instance.players.size() - 1)
            nextRound();
        Instance.activePlayerIndex += 1;
    }
}
