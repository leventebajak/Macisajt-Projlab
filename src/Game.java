import javax.swing.*;
import java.util.ArrayList;

/**
 * A Game osztály felelős a játék működéséért.
 * A játék indítása és befejezése, valamint a körök vezérlése ennek az osztálynak a feladata.
 */
public class Game {

    public static JPanel MAP;

    public static Game Instance = null;

    public static void NewGame(ArrayList<String> saboteurNames, ArrayList<String> plumberNames)
    {
        // TODO: új játék létrehozása a kapott játékosnevekkel és az Instance felülírása
    }

    /**
     * A csőrendszert reprezentálja. Tárolja a csöveket, pumpákat, ciszternákat és forrásokat.
     */
    public PipelineSystem pipelineSystem = new PipelineSystem();

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
        for (var plumber : Instance.plumbers)
            plumber.step();
        for (var saboteur : Instance.saboteurs)
            saboteur.step();
    }

    /**
     * A körön belül új játékos kerül sorra.
     */
    public static void nextPlayer() {
        // TODO: következő játékos paneljének betöltése és a pálya újrarajzolása,
        //  új kör kezdése az utolsó játékos után, elegendő pontszám esetén a játék befejezése
        Instance.activePlayerIndex = (Instance.activePlayerIndex + 1) % (Instance.plumbers.size() + Instance.saboteurs.size());
    }
}
