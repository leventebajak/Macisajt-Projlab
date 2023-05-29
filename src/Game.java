import javax.swing.*;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A Game osztály felelős a játék működéséért.
 * <p>
 * Az MVC mintában ez a Modell osztály.
 */
public class Game implements Serializable {

    private static final Random random = new Random();
    public static Game Instance = null;

    public static void NewGame(ArrayList<String> plumberNames, ArrayList<String> saboteurNames) {
        Instance = new Game();

        for (String pN : plumberNames)
            Instance.players.add(new Plumber(pN));

        for (String sN : saboteurNames)
            Instance.players.add(new Saboteur(sN));

        // Pálya generálása
        int springCount = random.nextInt(3, 6);
        int cisternCount = random.nextInt(2, 5);
        int pumpCount = random.nextInt(5, 10);


        int padding = Node.radius * 2;
        int maxDeltaX = 950 - 2 * padding;
        int maxDeltaY = 700 - 2 * padding;

        for (int i = 0; i < springCount; i++) {
            Spring spring = new Spring();
            int x = padding + random.nextInt(0, maxDeltaX / 10);
            int y = padding + random.nextInt(i * (maxDeltaY / springCount), (i + 1) * (maxDeltaY / springCount));
            spring.center = new Point(x, y);
            Instance.pipelineSystem.addComponent(spring);
        }

        var pumps = new ArrayList<Pump>();
        for (int i = 0; i < pumpCount; i++) {
            Pump pump = new Pump();
            int x = padding + random.nextInt(maxDeltaX * 2 / 10, maxDeltaX * 8 / 10);
            int y = padding + random.nextInt(i * (maxDeltaY / pumpCount), (i + 1) * (maxDeltaY / pumpCount));
            pump.center = new Point(x, y);
            Instance.pipelineSystem.addComponent(pump);
            pumps.add(pump);
        }

        for (int i = 0; i < cisternCount; i++) {
            Cistern cistern = new Cistern();
            int x = padding + random.nextInt(maxDeltaX * 9 / 10, maxDeltaX);
            int y = padding + random.nextInt(i * (maxDeltaY / cisternCount), (i + 1) * (maxDeltaY / cisternCount));
            cistern.center = new Point(x, y);
            Instance.pipelineSystem.addComponent(cistern);
        }

        // Forrásokból kiinduló csövek létrehozása
        for (int i = 0; i < springCount; i++) {
            Pipe pipe = new Pipe();
            var source = Component.PIPELINE_SYSTEM.components.get(random.nextInt(springCount, springCount + pumpCount - 1));
            var destination = Component.PIPELINE_SYSTEM.components.get(i);
            for(var pump : pumps) {
                if(Point.distance(destination.center.x, destination.center.y, pump.center.x, pump.center.y) < Point.distance(destination.center.x, destination.center.y, source.center.x, source.center.y)){
                    source = pump;
                }
            }
            pipe.addNeighbor(source);
            pipe.addNeighbor(destination);
            source.addNeighbor(pipe);
            destination.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }

        // Ciszternákba vezető csövek létrehozása
        for (int i = 0; i < cisternCount; i++) {
            Pipe pipe = new Pipe();
            var source = Component.PIPELINE_SYSTEM.components.get(random.nextInt(springCount, springCount + pumpCount - 1));
            var destination = Component.PIPELINE_SYSTEM.components.get(springCount + pumpCount + i);
            for(var pump : pumps) {
                if(Point.distance(destination.center.x, destination.center.y, pump.center.x, pump.center.y) < Point.distance(destination.center.x, destination.center.y, source.center.x, source.center.y)){
                    source = pump;
                }
            }
            pipe.addNeighbor(source);
            pipe.addNeighbor(destination);
            source.addNeighbor(pipe);
            destination.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }

        // A játékosok random kezdeti komponensének beállítása
        for (var player : Game.Instance.players) {
            while (player.component == null) {
                Component randomComponent = Game.Instance.pipelineSystem.components.get(random.nextInt(Game.Instance.pipelineSystem.components.size()));
                if (randomComponent instanceof Spring) continue;

                if (randomComponent instanceof Pipe) {
                    if (((Pipe) randomComponent).getOccupied())
                        continue;
                    player.component = randomComponent;
                    randomComponent.players.add(player);
                    ((Pipe) randomComponent).setOccupied(true);
                }
                player.component = randomComponent;
                randomComponent.players.add(player);
            }
        }

        var connections = new HashMap<Node, ArrayList<Node>>();
        for (var pump : pumps)
            connections.put(pump, new ArrayList<>());

        // Legalább (n−1)*(n−2)/2+1 db cső kell, hogy biztosan összefüggő legyen (n = a pumpák száma)
        for (int i = 0; i < (pumpCount - 1) * (pumpCount - 2) / 2 + 1; i++) {
            Pipe pipe = new Pipe();
            Pump firstNeighbor = pumps.get(random.nextInt(pumps.size()));
            while(connections.get(firstNeighbor).size()==pumps.size()-1){
                firstNeighbor = pumps.get(random.nextInt(pumps.size()));
            }
            Pump secondNeighbor = pumps.get(random.nextInt(pumps.size()));
            while(firstNeighbor == secondNeighbor || connections.get(firstNeighbor).contains(secondNeighbor)){
                secondNeighbor = pumps.get(random.nextInt(pumps.size()));
            }
            for(var pump : pumps) {
                if(pump == firstNeighbor || connections.get(pump).contains(firstNeighbor)) continue;
                if(Point.distance(firstNeighbor.center.x, firstNeighbor.center.y, pump.center.x, pump.center.y) < Point.distance(firstNeighbor.center.x, firstNeighbor.center.y, secondNeighbor.center.x, secondNeighbor.center.y)){
                    secondNeighbor = pump;
                }
            }
            pipe.addNeighbor(firstNeighbor);
            pipe.addNeighbor(secondNeighbor);
            firstNeighbor.addNeighbor(pipe);
            secondNeighbor.addNeighbor(pipe);
            connections.get(firstNeighbor).add(secondNeighbor);
            connections.get(secondNeighbor).add(firstNeighbor);
            Instance.pipelineSystem.addComponent(pipe);
        }

        // A pumpák source és destination beállítása
        for (var pump : pumps){
            var source = pump.pipes.get(random.nextInt(pump.pipes.size()));
            var destination = pump.pipes.get(random.nextInt(pump.pipes.size()));
            pump.redirect(source, destination);
        }

    }

    public static Player getActivePlayer() {
        return Instance.players.get(Instance.activePlayerIndex % Instance.players.size());
    }

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
        // TODO: elegendő pontszám esetén a játék befejezése
        if (Instance.activePlayerIndex % Instance.players.size() == Instance.players.size() - 1)
            nextRound();
        Instance.activePlayerIndex += 1;
    }

    public static void SaveGame(File file) {
        try (var out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(Instance);
        } catch (IOException ignored) {
            JOptionPane.showMessageDialog(null, "A játék mentése közben hiba történt!");
        }
    }

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
