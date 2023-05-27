import javax.swing.*;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Game osztály felelős a játék működéséért.
 * <p>
 * Az MVC mintában ez a Modell osztály.
 */
public class Game implements Serializable {

    public static Game Instance = null;

    public static void NewGame(ArrayList<String> plumberNames, ArrayList<String> saboteurNames) {
        Instance = new Game();

        for (String pN : plumberNames)
            Instance.players.add(new Plumber(pN));

        for (String sN : saboteurNames)
            Instance.players.add(new Saboteur(sN));

        // Pálya generálása
        Random random = new Random();

        int springs = random.nextInt(3, 6);
        int cisterns = random.nextInt(2, 5);
        int pumps = random.nextInt(5, 10);

        int padding = Node.radius * 2;
        int minX = padding;
        int minY = padding;
        int maxDeltaX = 950 - minX - padding;
        int maxDeltaY = 700 - minY - padding;

        for (int i = 0; i < springs; i++) {
            Spring spring = new Spring();
            int x = minX + random.nextInt(0, maxDeltaX / 10);
            int y = minY + random.nextInt(i * (maxDeltaY / springs), (i + 1) * (maxDeltaY / springs));
            spring.center = new Point(x, y);
            Instance.pipelineSystem.addComponent(spring);
        }

        for (int i = 0; i < pumps; i++) {
            Pump pump = new Pump();
            int x = minX + random.nextInt(maxDeltaX * 2 / 10, maxDeltaX * 8 / 10);
            int y = minY + random.nextInt(i * (maxDeltaY / pumps), (i + 1) * (maxDeltaY / pumps));
            pump.center = new Point(x, y);
            Instance.pipelineSystem.addComponent(pump);
        }

        for (int i = 0; i < cisterns; i++) {
            Cistern cistern = new Cistern();
            int x = minX + random.nextInt(maxDeltaX * 9 / 10, maxDeltaX);
            int y = minY + random.nextInt(i * (maxDeltaY / cisterns), (i + 1) * (maxDeltaY / cisterns));
            cistern.center = new Point(x, y);
            Instance.pipelineSystem.addComponent(cistern);
        }

        // Forrásokból kiinduló csövek létrehozása
        for (int i = 0; i < springs; i++) {
            Pipe pipe = new Pipe();
            var source = Component.PIPELINE_SYSTEM.components.get(random.nextInt(springs, springs + pumps - 1));
            var destination = Component.PIPELINE_SYSTEM.components.get(i);
            pipe.addNeighbor(source);
            pipe.addNeighbor(destination);
            source.addNeighbor(pipe);
            destination.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }

        // Ciszternákba vezető csövek létrehozása
        for (int i = 0; i < cisterns; i++) {
            Pipe pipe = new Pipe();
            var source = Component.PIPELINE_SYSTEM.components.get(random.nextInt(springs, springs + pumps - 1));
            var destination = Component.PIPELINE_SYSTEM.components.get(springs + pumps + i);
            pipe.addNeighbor(source);
            pipe.addNeighbor(destination);
            source.addNeighbor(pipe);
            destination.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }


        // A játékos random kezdeti komponens beállítása
        for (var player : Game.Instance.players){
            while(player.component==null){
                Component randomComponent=Game.Instance.pipelineSystem.components.get(new Random().nextInt(Game.Instance.pipelineSystem.components.size()));
                if (randomComponent instanceof Spring) continue;

                if (randomComponent instanceof Pipe){
                    if(!((Pipe) randomComponent).getOccupied()){
                        player.component=randomComponent;
                        randomComponent.players.add(player);
                        ((Pipe) randomComponent).setOccupied(true);
                    }
                    continue;
                }
                player.component=randomComponent;
                randomComponent.players.add(player);
            }
        }
        
        // TODO: csövek random pumpák között
        //  legalább (n−1)*(n−2)*2+1 db cső kell, hogy összefüggő legyen
        
        // így már összefüggő, de lehet hogy ennél több cső kéne
        for (int i = 0; i < pumps; i++) {
            Pipe pipe = new Pipe();
            var source = Instance.pipelineSystem.components.get(springs + i);
            var destination = Instance.pipelineSystem.components.get(springs + i + 1);
            pipe.addNeighbor(source);
            pipe.addNeighbor(destination);
            source.addNeighbor(pipe);
            destination.addNeighbor(pipe);
            Instance.pipelineSystem.addComponent(pipe);
        }

        for(int i = 0; i < pumps; i++){
            Pump pump = (Pump)Instance.pipelineSystem.components.get(springs+i);
            pump.redirect(pump.pipes.get(0), pump.pipes.get(1));
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
        // TODO: játék szerializálása
        try (var out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(Instance);
        } catch (IOException ignored) {
            JOptionPane.showMessageDialog(null, "A játék mentése közben hiba történt!");
        }
    }

    public static boolean LoadGame(File file) {
        // TODO: szerializált játék betöltése
        try (var in = new ObjectInputStream(new FileInputStream(file))) {
            var loaded = (Game) in.readObject();
            Instance = loaded;
            Component.PIPELINE_SYSTEM = Instance.pipelineSystem;
            return true;
        } catch (IOException | ClassNotFoundException ignored) {
            JOptionPane.showMessageDialog(null, "A fájl betöltése közben hiba történt!");
            return false;
        }
    }
}
