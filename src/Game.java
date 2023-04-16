import java.util.ArrayList;

/**
* A Game osztály felelős a játék működéséért. 
* A játék indítása és befejezése, valamint a körök vezérlése ennek az osztálynak a feladata.
*/
public class Game {
	
	/**
	 * A csőrendszert reprezentálja. Tárolja a csöveket, pumpákat, ciszternákat és forrásokat.
	 */
    private static PipelineSystem pipelineSystem;
    
    /**
     * A játékot játszó szabotőr csapat játékosait tárolja.
	 */
    private static final ArrayList<Saboteur> saboteurs = new ArrayList<>();
    
    /**
	 * A játékot játszó szerelő csapat játékosait tárolja.
	 */
    private static final ArrayList<Plumber> plumbers = new ArrayList<>();

    /**
	 * Elindítja a játékot, betölti a pályát, és elhelyezi a játékosokat véletlenszerűen a csőrendszer komponensein.
	 */
    public static void StartGame() {
        Skeleton.Call("[Game]", "StartGame()");
        pipelineSystem = new PipelineSystem();
        plumbers.clear();
        final int plumberCount = Skeleton.IntegerQuestion("A szerelők száma:");
        for (int i = 0; i < plumberCount; i++) {
            Plumber newPlumber = new Plumber("plumber" + (1 + i));
            Skeleton.Create(newPlumber);
            Skeleton.Return();
            plumbers.add(newPlumber);
        }
        saboteurs.clear();
        final int saboteurCount = Skeleton.IntegerQuestion("A szabotőrök száma:");
        for (int i = 0; i < saboteurCount; i++) {
            Saboteur newSaboteur = new Saboteur("saboteur" + (1 + i));
            Skeleton.Create(newSaboteur);
            Skeleton.Return();
            saboteurs.add(newSaboteur);
        }
        Skeleton.Return();
    }
    
    /**
	 * Befejezi a játékot.
	 */
    public static void EndGame() {}
    
    /**
	 * Új kör kezdődik, meghívja a komponensek Step() metódusát.
	 */
    public static void NextRound() {}
    
    /**
	 * A körön belül új játékos kerül sorra.
	 */
    public static void NextPlayer() {}
}
