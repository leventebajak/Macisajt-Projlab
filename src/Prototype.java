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
        if(args.length == 1) {
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
        else {
            switch (args[1]){
                case "help" -> System.out.println("""
                        HELP       Súgó az elérhető parancsokhoz és azok használatához
                            használata:
                                help [parancs neve]
                            
                            példák:
                                help
                                help move
                        """);
                case "new" -> System.out.println("""
                        NEW        Új objektum létrehozása
                            használata:
                                new {cistern|pipe|pump|spring|plumber|saboteur} <objektum neve> [<paraméterek> ...]
                            
                            lehetőségek:
                                new cistern <ciszterna neve>
                                new pipe <cső neve> <egyik csomópont neve> [másik csomópont neve]
                                new pump <pumpa neve>
                                new spring <forrás neve>
                                new plumber <szerelő neve> <kezdő komponens neve>
                                new saboteur <szabotőr neve> <kezdő csomópont neve>
                            
                            példák:
                                new cistern Cistern1
                                new pipe Pipe1 Pump1
                                new pipe Pipe1 Pump1 Pump2
                                new plumber Plumber1 Pipe1""");
                case "move" -> System.out.println("""
                        MOVE       Játékosok mozgatása
                            használata:
                                move <játékos neve> <komponens neve>
                            
                            példák:
                                move Plumber1 Pump1
                                move Saboteur1 Cistern1""");
                case "playeruse" -> System.out.println("""
                        PLAYERUSE  Játékosok képességeinek használata
                            használata:
                                playeruse <játékos neve> <képesség neve>
                            
                            példák:
                                playeruse Plumber1 repair
                                playeruse Plumber1 grabPipe Pipe1""");
                case "stat" -> System.out.println("""
                        STAT       Objektumok tulajdonságainak lekérdezése
                            használata:
                                stat <objektum neve> [tulajdonság neve]
                            
                            példák:
                                stat Pipe1 //ha van ilyen is
                                stat Pump1 broken
                                stat Plumber1 abletomove""");
                case "set" -> System.out.println("""
                        SET        Objektumok tulajdonságainak beállítása
                            használata:
                                set <objektum neve> <tulajdonság neve> <új érték>
                            
                            példák:
                                set Pump1 broken true
                                set Pipe1 waterlevel 1""");
                case "step" -> System.out.println("""
                        STEP       Objektum kör végén végrehajtandó feladatának végrehajtása
                            használata:
                                step <objektum neve>
                                
                            példák:
                                step Pump1
                                step Plumber1""");
                case "save" -> System.out.println("""
                        SAVE       Játék szerializásással való fájlba mentése
                            használata:
                                save <fájlnév>
                            példák:
                                save savegame""");
                case "load" -> System.out.println("""
                        LOAD       Szerializált játék fájlból való betöltése
                            használata:
                                load <fájlnév>
                            példák:
                                load savegame""");
                case "endGame" -> System.out.println("""
                        ENDGAME    Játék befejezése, a pontszámok és a nyertes csapat kiírása
                            használata:
                                endgame""");
                case "reset" -> System.out.println("""
                        RESET      A prototípus visszaállítása a kezdeti állapotba
                            használata:
                                reset""");
                case "objects" -> System.out.println("""
                        OBJECTS    A prototípus objektumainak kiírása
                            használata:
                                objects""");
                case "exit" -> System.out.println("""
                        EXIT       Kilépés a programból
                            használata:
                                exit""");
            }
        }

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
    	Object playerobject = OBJECTS.get(args[1]);
        if (playerobject == null) { 
        	System.out.println("Nincs ilyen nevű játékos objektum!"); return;
        }

        try {
            ((Player) playerobject).movePlayer(args);
        } catch (ClassCastException ignored) {
        } catch (IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        }
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
        if(args.length==1){
            help(new String[]{"help", "save"});
        } else {
            String file = args[1];
            File f = new File(file);
            if(!f.exists()){
                try {
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                    out.writeObject(GAME);
                    out.close();
                } catch (IOException ignored) {
                    System.out.println("Hiba történt a játék mentése közben!");
                }
            } else {
                System.out.println("A megadott fájlnév foglalt!");
            }
        }
    }

    /**
     * Szerializált játék fájlból való betöltését megvalósító parancs.
     *
     * @param args a parancs elvárt paraméterei: {@code load <fájlnév>}
     */
    public static void load(String[] args) {
        // TODO: valamiért nem tölti be a dolgokat
        if(args.length==1){
            help(new String[]{"help", "load"});
        } else {
            String file = args[1];
            File f = new File(file);
            if(f.exists() && !f.isDirectory()){
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    Game newGame = (Game) in.readObject();
                    in.close();
                    GAME = newGame;
                } catch (IOException | ClassNotFoundException ignored) {
                    System.out.println("Hiba történt a fájl betöltése közben!");
                }
            } else {
                System.out.println("A megadott fájl nem létezik!");
            }
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