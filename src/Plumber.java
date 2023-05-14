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
        while (Prototype.OBJECTS.containsKey("Pump" + i)) i++;
        grabbedPump = new Pump("Pump" + i);
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
            if (component.accept(plumber))
                plumber.component = component;
            else {
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
        if (args.length == 2) {
            return this +
                    "\nableToMove: " + ableToMove +
                    "\nableToMoveIn: " + ableToMoveIn +
                    "\ncomponent: " + component.name +
                    "\ngrabbedPipe: " + (grabbedPipe == null ? "null" : grabbedPipe.name) +
                    "\ngrabbedPump: " + (grabbedPump == null ? "null" : grabbedPump.name);
        }
        if (args.length != 3) throw new IllegalArgumentException("Érvénytelen paraméter!");
        return switch (args[2].strip().toLowerCase()) {
            case "abletomove" -> "ableToMove: " + ableToMove;
            case "abletomovein" -> "ableToMoveIn: " + ableToMoveIn;
            case "component" -> "component: " + component.name;
            case "grabbedpipe" -> "grabbedPipe: " + (grabbedPipe == null ? "null" : grabbedPipe.name);
            case "grabbedpump" -> "grabbedPump: " + (grabbedPump == null ? "null" : grabbedPump.name);
            default -> throw new IllegalArgumentException("A szerelőnek nincs ilyen nevű tulajdonsága!");
        };
    }

    /**
     * Szerelő tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        if (args.length != 4)
            throw new IllegalArgumentException("Hiányzó paraméter!");
        args[3] = args[3].strip();
        switch (args[2].strip().toLowerCase()) {
            case "abletomove" -> {
                switch (args[3]) {
                    case "true" -> ableToMove = true;
                    case "false" -> ableToMove = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "abletomovein" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    assert 0 <= value && value <= 5;
                    ableToMoveIn = value;
                    ableToMove = ableToMoveIn == 0;
                } catch (NumberFormatException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "component" -> {
                try {
                    assert Prototype.OBJECTS.containsKey(args[3]);
                    var newComponent = (Component) Prototype.OBJECTS.get(args[3]);
                    assert !(newComponent instanceof Spring);
                    assert !(newComponent instanceof Pipe && ((Pipe) newComponent).getOccupied());
                    component = newComponent;
                } catch (ClassCastException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }

            case "grabbedpipe" -> {
                try {
                    assert Prototype.OBJECTS.containsKey(args[3]);
                    var newGrabbedPipe = (Pipe) Prototype.OBJECTS.get(args[3]);
                    assert !newGrabbedPipe.getOccupied();
                    grabbedPipe = newGrabbedPipe;
                } catch (ClassCastException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "grabbedpump" -> {
                try {
                    assert Prototype.OBJECTS.containsKey(args[3]);
                    grabbedPump = (Pump) Prototype.OBJECTS.get(args[3]);
                } catch (ClassCastException | AssertionError ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
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
        switch (args[2].strip().toLowerCase()) {
            case "redirect" -> {
                if (args.length < 5) throw new IllegalArgumentException("Hiányzó paraméter!");
                try {
                    redirect((Pipe) Prototype.OBJECTS.get(args[3]), (Pipe) Prototype.OBJECTS.get(args[4]));
                } catch (ClassCastException ignored) {
                    throw new IllegalArgumentException("Az objektum nem cső!");
                }
            }
            case "leak" -> leak();
            case "makeitsticky" -> makeItSticky();
            case "repair" -> repair();
            case "placepump" -> placePump();
            case "grabpipe" -> {
                if (args.length < 4) throw new IllegalArgumentException("Hiányzó paraméter!");
                try {
                    grabPipe((Pipe) Prototype.OBJECTS.get(args[3]));
                } catch (ClassCastException ignored) {
                    throw new IllegalArgumentException("Az objektum nem cső!");
                }
            }
            case "placepipe" -> placePipe();
            case "receivepump" -> receivePump();
            default -> throw new IllegalArgumentException("Érvénytelen paraméter!");
        }
    }
}