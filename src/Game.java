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
        Component.PIPELINE_SYSTEM = pipelineSystem;
    }

    /**
     * Elindítja a játékot, betölti a pályát, és elhelyezi a játékosokat véletlenszerűen a csőrendszer komponensein.
     */
    public void startGame() {
        // A grafikus verzióhoz kell majd
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
        activePlayerIndex = (activePlayerIndex + 1) % (plumbers.size() + saboteurs.size());
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
        if (args.length == 2) {
            var result = new StringBuilder(this.toString());
            result.append("\nplumbers:");
            for (Plumber p : plumbers)
                result.append(" ").append(p.name);
            result.append("\nsaboteurs:");
            for (Saboteur s : saboteurs)
                result.append(" ").append(s.name);
            result.append("\npipelineSystem: ").append(pipelineSystem.name);
            return result.toString();
        }

        if (args.length != 3)
            throw new IllegalArgumentException("Érvénytelen paraméter!");
        switch (args[2].strip().toLowerCase()) {
            case "plumbers" -> {
                var result = new StringBuilder("plumbers:");
                for (Plumber p : plumbers)
                    result.append(" ").append(p.name);
                return result.toString();
            }
            case "saboteurs" -> {
                var result = new StringBuilder("saboteurs:");
                for (Saboteur s : saboteurs)
                    result.append(" ").append(s.name);
                return result.toString();
            }
            case "pipelinesystem" -> {
                return "pipelineSystem: " + pipelineSystem.name;
            }
            default -> throw new IllegalArgumentException("A játéknak nincs ilyen nevű tulajdonsága");
        }
    }

    /**
     * Játék tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        throw new IllegalArgumentException("A játéknak nincs állítható tulajdonsága!");
    }
}
