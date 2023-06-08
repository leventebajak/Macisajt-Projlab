import javax.swing.*;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * A Game osztály felelős a játék működéséért.
 * <p>
 * Az MVC mintában ez a Modell osztály.
 */
public class Game implements Serializable {

    private static final Random random = new Random();

    /**
     * A játék megnyeréséhez szükséges pontszám
     */
    private static final int goal = 25;

    /**
     * Az aktuális játék példánya
     */
    public static Game Instance = null;

    /**
     * Új játék létrehozása a megadott szerelők és szabotőrök neveivel
     *
     * @param plumberNames  a szerelők nevei
     * @param saboteurNames a szabotőrök nevei
     */
    public static void NewGame(ArrayList<String> plumberNames, ArrayList<String> saboteurNames) {
        Instance = new Game();

        for (String pN : plumberNames)
            Instance.players.add(new Plumber(pN));

        for (String sN : saboteurNames)
            Instance.players.add(new Saboteur(sN));

        // Pálya generálása
        int springCount = random.nextInt(3, 6);
        int cisternCount = random.nextInt(2, 5);
        int pumpCount = random.nextInt(5, 8);

        int padding = Node.radius * 2;
        int maxDeltaX = 950 - 2 * padding;
        int maxDeltaY = 700 - 2 * padding;

        var springs = new ArrayList<Spring>();
        for (int i = 0; i < springCount; i++) {
            Spring spring = new Spring();
            int x = padding + random.nextInt(0, maxDeltaX / 10);
            int y = padding + random.nextInt(i * (maxDeltaY / springCount), (i + 1) * (maxDeltaY / springCount));
            spring.center = new Point(x, y);
            boolean valid = springs.size() == 0;
            while (!valid) {
                int n = 0;
                for (var s : springs) {
                    if (isfar(s.center, spring.center)) n++;
                }
                if (n == springs.size()) {
                    valid = true;
                } else {
                    spring.center.x = padding + random.nextInt(0, maxDeltaX / 10);
                    spring.center.y = padding + random.nextInt(i * (maxDeltaY / springCount), (i + 1) * (maxDeltaY / springCount));
                }
            }
            Instance.pipelineSystem.addComponent(spring);
            springs.add(spring);
        }

        var pumps = new ArrayList<Pump>();
        for (int i = 0; i < pumpCount; i++) {
            Pump pump = new Pump();
            int x = padding + random.nextInt(maxDeltaX * 2 / 10, maxDeltaX * 8 / 10);
            int y = padding + random.nextInt(i * (maxDeltaY / pumpCount), (i + 1) * (maxDeltaY / pumpCount));
            pump.center = new Point(x, y);
            boolean valid = pumps.size() == 0;
            while (!valid) {
                int n = 0;
                for (var p : pumps) {
                    if (isfar(p.center, pump.center)) n++;
                }
                if (n == pumps.size()) {
                    valid = true;
                } else {
                    pump.center.x = padding + random.nextInt(maxDeltaX * 2 / 10, maxDeltaX * 8 / 10);
                    pump.center.y = padding + random.nextInt(i * (maxDeltaY / pumpCount), (i + 1) * (maxDeltaY / pumpCount));
                }
            }
            Instance.pipelineSystem.addComponent(pump);
            pumps.add(pump);
        }

        var cisterns = new ArrayList<Cistern>();
        for (int i = 0; i < cisternCount; i++) {
            Cistern cistern = new Cistern();
            int x = padding + random.nextInt(maxDeltaX * 9 / 10, maxDeltaX);
            int y = padding + random.nextInt(i * (maxDeltaY / cisternCount), (i + 1) * (maxDeltaY / cisternCount));
            cistern.center = new Point(x, y);
            boolean valid = cisterns.size() == 0;
            while (!valid) {
                int n = 0;
                for (var c : cisterns) {
                    if (isfar(c.center, cistern.center)) n++;
                }
                if (n == cisterns.size()) {
                    valid = true;
                } else {
                    cistern.center.x = padding + random.nextInt(maxDeltaX * 9 / 10, maxDeltaX);
                    cistern.center.y = padding + random.nextInt(i * (maxDeltaY / cisternCount), (i + 1) * (maxDeltaY / cisternCount));
                }
            }
            Instance.pipelineSystem.addComponent(cistern);
            cisterns.add(cistern);
        }

        // A játékosok elhelyezése
        for (var player : Game.Instance.players) {
            while (player.component == null) {
                Component component = Game.Instance.pipelineSystem.components.get(random.nextInt(Game.Instance.pipelineSystem.components.size()));
                player.setComponent(component);
            }
        }

        // Forrásokból kiinduló csövek létrehozása
        for (int i = 0; i < springCount; i++) {
            var spring = springs.get(i);
            Pump neighbor = pumps.get(0);
            for (var pump : pumps)
                if (Point.distance(spring.center.x, spring.center.y, pump.center.x, pump.center.y) < Point.distance(spring.center.x, spring.center.y, neighbor.center.x, neighbor.center.y))
                    neighbor = pump;
            Pipe pipe = new Pipe();
            pipe.addNeighbor(spring);
            pipe.addNeighbor(neighbor);
            spring.addNeighbor(pipe);
            neighbor.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }

        // Ciszternákba vezető csövek létrehozása
        for (int i = 0; i < cisternCount; i++) {
            var cistern = cisterns.get(i);
            Pump neighbor = pumps.get(0);
            for (var pump : pumps)
                if (Point.distance(cistern.center.x, cistern.center.y, pump.center.x, pump.center.y) < Point.distance(cistern.center.x, cistern.center.y, neighbor.center.x, neighbor.center.y))
                    neighbor = pump;
            Pipe pipe = new Pipe();
            pipe.addNeighbor(cistern);
            pipe.addNeighbor(neighbor);
            cistern.addNeighbor(pipe);
            neighbor.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }

        var connections = new HashMap<Node, HashSet<Node>>();
        for (var pump : pumps) {
            connections.put(pump, new HashSet<>());
            connections.get(pump).add(pump);
        }

        // Legalább (n−1)*(n−2)/2+1 db cső kell a pumpák között, hogy biztosan összefüggő legyen (n = a pumpák száma)
        for (int i = 0; i < (pumpCount - 1) * (pumpCount - 2) / 2 + 1; i++) {
            Pump firstNeighbor = pumps.get(random.nextInt(pumps.size()));
            while (connections.get(firstNeighbor).size() == pumps.size())
                firstNeighbor = pumps.get(random.nextInt(pumps.size()));
            Pump secondNeighbor = pumps.get(random.nextInt(pumps.size()));
            while (connections.get(firstNeighbor).contains(secondNeighbor))
                secondNeighbor = pumps.get(random.nextInt(pumps.size()));
            for (var pump : pumps) {
                if (connections.get(pump).contains(firstNeighbor))
                    continue;
                if (Point.distance(firstNeighbor.center.x, firstNeighbor.center.y, pump.center.x, pump.center.y) < Point.distance(firstNeighbor.center.x, firstNeighbor.center.y, secondNeighbor.center.x, secondNeighbor.center.y))
                    secondNeighbor = pump;
            }
            Pipe pipe = new Pipe();
            pipe.addNeighbor(firstNeighbor);
            pipe.addNeighbor(secondNeighbor);
            firstNeighbor.addNeighbor(pipe);
            secondNeighbor.addNeighbor(pipe);
            connections.get(firstNeighbor).add(secondNeighbor);
            connections.get(secondNeighbor).add(firstNeighbor);
            Instance.pipelineSystem.addComponent(pipe);
        }

        // A pumpák forrás- és célcsövének beállítása
        for (var pump : pumps) {
            var source = pump.pipes.get(random.nextInt(pump.pipes.size()));
            var destination = pump.pipes.get(random.nextInt(pump.pipes.size()));
            pump.redirect(source, destination);
        }

    }

    /**
     * Igazat ad vissza, ha a két pont távolabb van egymástól, mint a pontok alapértelmezett sugarának kétszerese + 5 px
     */
    public static boolean isfar(Point basepoint, Point newpoint) {
        double distance = Math.sqrt(Math.pow(newpoint.x - basepoint.x, 2) + Math.pow(newpoint.y - basepoint.y, 2));
        return !(Math.abs(distance) <= Node.radius * 2 + 5);
    }

    /**
     * A soron levő játékos lekérdezése
     *
     * @return a soron levő játékos
     */
    public static Player getActivePlayer() {
        return Instance.players.get(Instance.activePlayerIndex % Instance.players.size());
    }

    /**
     * A jelenlegi kör számának lekérdezése
     *
     * @return a jelenlegi kör száma
     */
    public static int getRound() {
        return (Instance.activePlayerIndex + 1) / Instance.players.size() + 1;
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
     * Befejezi a játékot.
     */
    public static void endGame() {
        GameOverWindow.Winner winner;
        if (Instance.pipelineSystem.getCollectedWater() > Instance.pipelineSystem.getLeakedWater())
            winner = GameOverWindow.Winner.PLUMBERS;
        else if (Instance.pipelineSystem.getCollectedWater() < Instance.pipelineSystem.getLeakedWater())
            winner = GameOverWindow.Winner.SABOTEURS;
        else
            winner = GameOverWindow.Winner.DRAW;
        View.setContentPane(new GameOverWindow(winner));
        View.FRAME.setJMenuBar(null);
    }

    /**
     * Új kör kezdődik, meghívja a komponensek step() metódusát.
     */
    public static void nextRound() {
        for (int i = 0; i < Instance.pipelineSystem.components.size(); i++)
            Instance.pipelineSystem.components.get(i).step();
        for (var player : Instance.players)
            player.step();
        View.GAME_WINDOW.refresh();
    }

    /**
     * A körön belül új játékos kerül sorra.
     */
    public static void nextPlayer() {
        if (Instance.pipelineSystem.getCollectedWater() >= goal || Instance.pipelineSystem.getLeakedWater() >= goal)
            endGame();
        if (Instance.activePlayerIndex % Instance.players.size() == Instance.players.size() - 1)
            nextRound();
        Instance.activePlayerIndex += 1;
    }

    /**
     * Játék fájlba való mentése
     *
     * @param file a fájl, amibe mentjük a játékot
     */
    public static void SaveGame(File file) {
        try (var out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(Instance);
        } catch (IOException ignored) {
            JOptionPane.showMessageDialog(null, "A játék mentése közben hiba történt!");
        }
    }

    /**
     * Játék fájlból való betöltése
     *
     * @param file a fájl, amiből betöltjük a játékot
     * @return true, ha a betöltés sikeres volt, különben false
     */
    public static boolean LoadGame(File file) {
        try (var in = new ObjectInputStream(new FileInputStream(file))) {
            Instance = (Game) in.readObject();
            Component.PIPELINE_SYSTEM = Instance.pipelineSystem;
            return true;
        } catch (IOException | ClassNotFoundException ignored) {
            JOptionPane.showMessageDialog(null, "A fájl betöltése közben hiba történt!");
            return false;
        }
    }
}
