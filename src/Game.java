import java.util.ArrayList;

public class Game {
    private static PipelineSystem pipelineSystem;
    private static final ArrayList<Saboteur> saboteurs = new ArrayList<>();
    private static final ArrayList<Plumber> plumbers = new ArrayList<>();

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
    public static void EndGame() {}
    public static void NextRound() {}
    public static void NextPlayer() {}
}
