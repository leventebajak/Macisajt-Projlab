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
        while (true) {
            try {
                runCommand(SCANNER.nextLine());
            } catch (RuntimeException ignored) {
                break;
            }
        }
    }

    /**
     * Súgó a prototípusban használható parancsokhoz és azok használatához.
     *
     * @param args a parancs elvárt paraméterei: {@code help [command]}
     */
    public static void help(String[] args) {
        // TODO: súgó a parancsok használatához is
        System.out.println("""
                HELP       Súgó az elérhető parancsokhoz és azok használatához
                NEW        Új objektum létrehozása
                MOVE       Játékosok mozgatása
                PLAYERUSE  Játékosok képességeinek használata
                STAT       Objektumok tulajdonságainak lekérdezése
                SET        Objektumok tulajdonságainak beállítása
                STEP       Objektum kör végén végrehajtandó feladatának végrehajtása
                SAVE       Játék szerializásással való fájlba mentése
                LOAD       Szerializált játék fájlból való betöltése
                ENDGAME    Játék befejezése, a pontszámok és a nyertes csapat kiírása
                RESET      A prototípus visszaállítása a kezdeti állapotba
                OBJECTS    A prototípus objektumainak kiírása
                EXIT       Kilépés a programból""");
    }

    /**
     * Kapott parancs végrehajtása.
     *
     * @param command a végrehajtandó parancs
     * @throws RuntimeException {@code exit} parancs érkezett
     */
    public static void runCommand(String command) throws RuntimeException {
        String[] args = command.split(" ");
        args[0] = args[0].strip().toLowerCase();
        switch (args[0]) {
            case "help" -> help(args);
            case "new" -> NEW(args);
            case "move" -> move(args);
            case "playeruse" -> playerUse(args);
            case "set" -> set(args);
            case "step" -> step(args);
            case "save" -> save(args);
            case "load" -> load(args);
            case "endgame" -> endGame();
            case "reset" -> reset();
            case "objects" -> objects();
            case "stat" -> {
                try {
                    System.out.println(stat(args));
                } catch (IndexOutOfBoundsException ignored) {
                    help(new String[]{"help", "stat"});
                }
            }
            case "exit" -> throw new RuntimeException("'exit' parancs érkezett");
            default -> System.out.println("Az elérhető parancsokhoz használja a 'help' parancsot!");
        }
    }

    /**
     * Új objektumok létrehozását megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei:
     *             {@code new {cistern|pipe|pump|spring|plumber|saboteur} <objektum neve> [<paraméterek> ...]}
     */
    public static void NEW(String[] args) {
        try {
            args[1] = args[1].strip().toLowerCase();
            switch (args[1]) {
                case "cistern" -> GAME.pipelineSystem.addComponent(Cistern.NEW(args));
                case "pipe" -> GAME.pipelineSystem.addComponent(Pipe.NEW(args));
                case "pump" -> GAME.pipelineSystem.addComponent(Pump.NEW(args));
                case "spring" -> GAME.pipelineSystem.addComponent(Spring.NEW(args));
                case "plumber" -> GAME.plumbers.add(Plumber.NEW(args));
                case "saboteur" -> GAME.saboteurs.add(Saboteur.NEW(args));
                default -> help(new String[]{"help", "new"});
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (IndexOutOfBoundsException ignored) {
            help(new String[]{"help", "new"});
        }
    }

    /**
     * Játékosok mozgatását megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code move <játékos neve> <komponens neve>}
     */
    public static void move(String[] args) {
        // TODO: move command
    }

    /**
     * Játékosok képességeinek használatát megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code playeruse <játékos neve> <képesség neve>}
     */
    public static void playerUse(String[] args) {
        // TODO: playeruse command
    }

    /**
     * Objektumok tulajdonságainak lekérdezését megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return A lekérdezett tulajdonság vagy hibaüzenet
     */
    public static String stat(String[] args) {
        Object object = OBJECTS.get(args[1]);
        if (object == null) return "Nincs ilyen nevű objektum!";

        try {
            return ((Printable) object).stat(args);
        } catch (ClassCastException ignored) {
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return "Az objektum típusát nem lehet meghatározni!";
    }

    /**
     * Objektumok tulajdonságainak beállítását megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     */
    public static void set(String[] args) {
        // TODO: set command
    }

    /**
     * Objektum kör végén végrehajtandó feladatának végrehajtását megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code step <objektum neve>}
     */
    public static void step(String[] args) {
        // TODO: step command
    }

    /**
     * Játék szerializásással való fájlba mentését megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code save <fájlnév>}
     */
    public static void save(String[] args) {
        // TODO: save command should check the filename
        String file = args[1];
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(GAME);
            out.close();
        } catch (IOException ignored) {
            System.out.println("Hiba történt a játék mentése közben!");
        }
    }

    /**
     * Szerializált játék fájlból való betöltését megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code load <fájlnév>}
     */
    public static void load(String[] args) {
        // TODO: load command should check the filename
        String file = args[1];
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            Game newGame = (Game) in.readObject();
            in.close();
            GAME = newGame;
        } catch (IOException | ClassNotFoundException ignored) {
            System.out.println("Hiba történt a fájl betöltése közben!");
        }
    }

    /**
     * Játék befejezését végrehajtó parancs. Kiírja a pontszámokat és a nyertes csapatot.
     */
    public static void endGame() {
        GAME.endGame();
    }

    /**
     * A prototípus visszaállítása a kezdeti állapotba.
     */
    public static void reset() {
        OBJECTS.clear();
        GAME = new Game("game");
    }

    /**
     * A prototípus objektumainak kiírása.
     */
    public static void objects() {
        for (var object : OBJECTS.values())
            System.out.println(object);
    }
}