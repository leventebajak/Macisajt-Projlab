import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

/**
 * Ez a Prototípus program a "Sivatagi vízhálózat" feladat könnyen tesztelhető, konzolos változata.
 *
 * @author Baják Levente Imre
 * @author Csikai Valér Zsolt
 * @author Koszoru Domonkos
 * @author Le Ngoc Toan
 * @author Stróbl Dániel Alajos
 */
public abstract class Prototype {

    /**
     * Objektum referenciák név alapú lekérdezéséhez használt asszociatív tömb.
     */
    public static HashMap<String, Object> OBJECTS = new HashMap<>();

    /**
     * A játék, melyet a prototípus irányít.
     */
    private static Game GAME = new Game("game");

    /**
     * A konzolról való olvasáshoz használt szkenner.
     */
    private static final Scanner SCANNER = new Scanner(System.in);

    /**
     * Main metódus ami elindítja a szekvencia választást.
     */
    public static void main(String[] args) {
        System.out.println("Sivatagi vízhálózat Prototípus");
        System.out.println("Csapat: 23 - macisajt");
    }

    /**
     * Kapott parancs végrehajtása.
     *
     * @param command
     */
    public static void runCommand(String command) {
        // TODO: command interpreter
    }

    /**
     * Új objektumok létrehozását megvalósító parancs.
     *
     * @param args
     */
    public static void NEW(String[] args) {
        // TODO: new command
    }

    /**
     * Játékosok mozgatását megvalósító parancs.
     *
     * @param args
     */
    public static void move(String[] args) {
        // TODO: move command
    }

    /**
     * Játékosok képességeinek használatát megvalósító parancs.
     *
     * @param args
     */
    public static void playerUse(String[] args) {
        // TODO: playeruse command
    }

    /**
     * Objektumok tulajdonságainak lekérdezését megvalósító parancs.
     *
     * @param args
     */
    public static void stat(String[] args) {
        // TODO: stat command
    }

    /**
     * Objektumok tulajdonságainak beállítását megvalósító parancs.
     *
     * @param args
     */
    public static void set(String[] args) {
        // TODO: set command
    }

    /**
     * Objektum kör végén végrehajtandó feladatának végrehajtását megvalósító parancs.
     *
     * @param args
     */
    public static void step(String[] args) {
        // TODO: step command
    }

    /**
     * Játék szerializásással való fájlba mentését megvalósító parancs.
     *
     * @param args
     */
    public static void save(String[] args) {
        // TODO: save command
        String file = args[1];
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(GAME);
            out.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Szerializált játék fájlból való betöltését megvalósító parancs.
     *
     * @param args
     */
    public static void load(String[] args) {
        // TODO: load command
        String file = args[1];
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Game newGame = (Game) in.readObject();
            in.close();
            GAME = newGame;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Játék befejezését végrehajtó parancs. Kiírja a pontszámokat és a nyertes csapatot.
     */
    public static void endGame() {
        // TODO: endgame command
    }

    /**
     * A prototípus visszaállítása a kezdeti állapotba.
     */
    public static void reset() {
        // TODO: reset command
        GAME = new Game("game");
    }
}