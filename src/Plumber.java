/**
 * Szerelő csapat játékosait reprezentáló osztály. Felelőssége a csövek megjavítása és új pumpa lerakása.
 */
public class Plumber extends Player {

    /**
     * Az a cső, amit a szerelő felvett.
     */
    private Pipe grabbedPipe;

    /**
     * Ez az attribútum a szerelőnél lévő pumpa referenciája.
     */
    private Pump grabbedPump;

    /**
     * Szerelő konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    Plumber(String name) {
        super(name);
    }

    /**
     * Pumpa átállítása, de csak akkor, ha a szerelő nem mozgat csövet.
     *
     * @param source      Az a cső, amelyből a pumpa szívja a vizet.
     * @param destination Az a cső, amelybe a pumpa pumpálja a vizet.
     */
    @Override
    public void redirect(Pipe source, Pipe destination) {
        if (grabbedPipe != null) return;
        component.redirect(source, destination);
    }

    /**
     * A jelenlegi mező megjavításának megkísérlése.
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet.
     */
    public void repair() {
        if (grabbedPipe != null) return;
        component.repair();
    }

    /**
     * A szerelőnek való pumpa adás megkísérlése.
     */
    @Override
    public void receivePump() {
        if (grabbedPump != null) return;
        int i = 1;
        while (Prototype.OBJECTS.containsKey("pump" + i)) i++;
        grabbedPump = new Pump("pump" + i);
        Prototype.OBJECTS.put(grabbedPump.name, grabbedPump);
    }

    /**
     * A játékosnál található pumpa lerakásának megkísérlése.
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet, és van pumpa a szerelőnél.
     */
    public void placePump() {
        if (grabbedPipe != null || grabbedPump == null) return;
        if (component.placePump(grabbedPump)) grabbedPump = null;
    }

    /**
     * Egy megadott cső megfogásának megkísérlése.
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet.
     */
    public void grabPipe(Pipe pipe) {
        if (grabbedPipe != null) return;
        if (component.grabPipe(pipe)) grabbedPipe = pipe;
    }

    /**
     * A játékos által megfogott cső lerakásának megkísérlése.
     */
    public void placePipe() {
        if (grabbedPipe == null) return;
        if (component.placePipe(grabbedPipe)) grabbedPipe = null;
    }

    /**
     * A jelenlegi mező kilyukasztásának megkísérlése, ha a játékosnál nincs cső.
     */
    @Override
    public void leak() {
        if (grabbedPipe != null) return;
        super.leak();
    }

    /**
     * A jelenlegi mező ragadóssá tételének megkísérlése, ha a játékosnál nincs cső.
     */
    @Override
    public void makeItSticky() {
        if (grabbedPipe != null) return;
        super.makeItSticky();
    }

    /**
     * Új szerelő létrehozása a megadott névvel és csomóponttal.
     *
     * @param args a parancs elvárt paraméterei: {@code new plumber <szerelő neve> <kezdő komponens neve>}
     * @return a létrehozott szerelő referenciája
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public static Plumber NEW(String[] args) throws IllegalArgumentException {
        if (args.length != 4) throw new IllegalArgumentException("Érvénytelen paraméter!");
        if (Prototype.OBJECTS.containsKey(args[2])) throw new IllegalArgumentException("A név már foglalt!");
        if (!Prototype.OBJECTS.containsKey(args[3]))
            throw new IllegalArgumentException("Nem létezik komponens a megadott névvel!");
        try {
            Component component = (Component) Prototype.OBJECTS.get(args[3]);
            Plumber plumber = new Plumber(args[2]);
            plumber.component = component;
            if (!component.accept(plumber)) {
                Prototype.OBJECTS.remove(args[2]);
                throw new IllegalArgumentException("A komponens nem tudja fogadni a szerelőt!");
            }
            return plumber;
        } catch (ClassCastException ignored) {
            throw new IllegalArgumentException("Nem létezik komponens a megadott névvel!");
        }
    }

    /**
     * Szerelő tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
        args[2] = args[2].strip().toLowerCase();
        switch (args[2]) {
            case "abletomove" -> {
                return "ableToMove: " + ableToMove;
            }
            case "abletomovein" -> {
                return "ableToMoveIn: " + ableToMoveIn;
            }
            case "component" -> {
                return "component: " + component.name;
            }
            case "grabbedpipe" -> {
                if (grabbedPipe == null) return "grabbedPipe: null";
                else return "grabbedPipe: " + grabbedPipe.name;
            }
            case "grabbedpump" -> {
                if (grabbedPump == null) return "grabbedPump: null";
                else return "grabbedPump: " + grabbedPump.name;
            }
            default -> {
                throw new IllegalArgumentException("A szerelőnek nincs ilyen nevű tulajdonsága");
            }
        }
    }

    /**
     * Szerelő tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        if (args.length != 4) {
            throw new IllegalArgumentException("Érvénytelen a megadott érték!");
        }
        args[2] = args[2].strip().toLowerCase();
        args[3] = args[3].strip().toLowerCase();
        boolean changed = false;

        switch (args[2]) {
            case "abletomove" -> {
                switch (args[3]) {
                    case "true" -> {
                        ableToMove = true;
                    }
                    case "false" -> {
                        ableToMove = false;
                    }
                    default -> {
                        throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                    }
                }
            }
            case "abletomovein" -> {
                try {
                    int abletomoveinvalue = Integer.parseInt(args[3]);
                    if (abletomoveinvalue < 0) throw new NumberFormatException();
                    ableToMoveIn = abletomoveinvalue;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "component" -> {
                try {
                    if (Prototype.OBJECTS.containsKey(args[3])) {
                        component = (Component) Prototype.OBJECTS.get(args[3]);
                        changed = true;
                    }
                } catch (ClassCastException e) {
                    changed = false;
                }
                if (!changed) throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }

            case "grabbedpipe" -> {
                try {
                    if (Prototype.OBJECTS.containsKey(args[3])) {
                        grabbedPipe = (Pipe) Prototype.OBJECTS.get(args[3]);
                        changed = true;
                    }
                    if (args[3].equals("null")) {
                        component = null;
                        changed = true;
                    }
                } catch (ClassCastException e) {
                    changed = false;
                }
                if (!changed) throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }
            case "grabbedpump" -> {
                try {
                    if (Prototype.OBJECTS.containsKey(args[3])) {
                        grabbedPump = (Pump) Prototype.OBJECTS.get(args[3]);
                        changed = true;
                    }
                    if (args[3].equals("null")) {
                        component = null;
                        changed = true;
                    }
                } catch (ClassCastException e) {
                    changed = false;
                }
                if (!changed) throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }

            default -> {
                throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }

        }
    }

    /**
     * Szerelő paraméterként kapott nevű képességének használata.
     *
     * @param args a parancs elvárt paraméterei: {@code playeruse <játékos neve> <képesség neve>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void playerUse(String[] args) throws IllegalArgumentException {
        if (args.length < 3) throw new IllegalArgumentException("Hiányzó paraméter!");
        switch (args[2]) {
            case "redirect" -> {
                if (args.length < 5) throw new IllegalArgumentException("Hiányzó paraméter!");
                try {
                    redirect((Pipe) Prototype.OBJECTS.get(args[3]), (Pipe) Prototype.OBJECTS.get(args[4]));
                } catch (ClassCastException ignored) {
                    throw new IllegalArgumentException("Az objektum nem cső!");
                }
            }
            case "leak" -> leak();
            case "makeItSticky" -> makeItSticky();
            case "repair" -> repair();
            case "placePump" -> placePump();
            case "grabPipe" -> {
                if (args.length < 4) throw new IllegalArgumentException("Hiányzó paraméter!");
                try {
                    grabPipe((Pipe) Prototype.OBJECTS.get(args[3]));
                } catch (ClassCastException ignored) {
                    throw new IllegalArgumentException("Az objektum nem cső!");
                }
            }
            case "placePipe" -> placePipe();
            default -> throw new IllegalArgumentException("Érvénytelen paraméter!");
        }
    }
}