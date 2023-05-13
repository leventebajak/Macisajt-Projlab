/**
 * Szabotőr csapat játékosait megvalósító osztály. Felelőssége a csövek kilyukasztása.
 */
public class Saboteur extends Player {

    /**
     * Szabotőr konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Saboteur(String name) {
        super(name);
    }

    /**
     * A jelenlegi mező csúszóssá tételének megkísérlése.
     */
    public void makeItSlippery() {
        component.makeItSlippery();
    }

    /**
     * Új szabotőr létrehozása a megadott névvel és csomóponttal.
     *
     * @param args a parancs elvárt paraméterei: {@code new saboteur <szabotőr neve> <kezdő csomópont neve>}
     * @return a létrehozott szabotőr referenciája
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public static Saboteur NEW(String[] args) throws IllegalArgumentException {
        if (args.length != 4) throw new IllegalArgumentException("Érvénytelen paraméter!");
        if (Prototype.OBJECTS.containsKey(args[2])) throw new IllegalArgumentException("A név már foglalt!");
        if (!Prototype.OBJECTS.containsKey(args[3]))
            throw new IllegalArgumentException("Nem létezik komponens a megadott névvel!");
        try {
            Component component = (Component) Prototype.OBJECTS.get(args[3]);
            Saboteur saboteur = new Saboteur(args[2]);
            saboteur.component = component;
            if (!component.accept(saboteur)) {
                Prototype.OBJECTS.remove(args[2]);
                throw new IllegalArgumentException("A komponens nem tudja fogadni a szabotőrt!");
            }
            return saboteur;
        } catch (ClassCastException ignored) {
            throw new IllegalArgumentException("Nem létezik komponens a megadott névvel!");
        }
    }

    /**
     * Szabotőr tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
    	args[2] = args[2].strip().toLowerCase();
    	switch (args[2]) {
        case "abletomove" ->  { return "ableToMove: " + ableToMove; }
        case "abletomovein" ->  { return "ableToMoveIn: " + ableToMoveIn; }
        case "component" ->  { return "component: " + component.name; }
        default -> { 
        	throw new IllegalArgumentException("A szabotőrnek nincs ilyen nevű tulajdonsága"); 
        	}
    	}
    }

    /**
     * Szabotőr tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        if(args.length!=4){
            throw new IllegalArgumentException("Érvénytelen a megadott érték!");
        }
        args[2] = args[2].strip().toLowerCase();
        args[3] = args[3].strip().toLowerCase();
        boolean changed=false;

        switch (args[2]) {
            case "abletomove" -> {
                switch (args[3]){
                    case "true" ->  { ableToMove=true; }
                    case "false" ->  { ableToMove=false; }
                    default -> {throw new IllegalArgumentException("Érvénytelen a megadott érték!");}
                }
            }
            case "abletomovein" -> {
                try {
                    int abletomoveinvalue=Integer.parseInt(args[3]);
                    if(abletomoveinvalue<0) throw new NumberFormatException();
                    ableToMoveIn=abletomoveinvalue;
                } catch (NumberFormatException e) {throw new IllegalArgumentException("Érvénytelen a megadott érték!");}
            }
            case "component" -> {
                try {
                    if (Prototype.OBJECTS.containsKey(args[3])) {
                        component=(Component)Prototype.OBJECTS.get(args[3]);
                        changed=true;
                    }
                } catch (ClassCastException  e){changed=false;}
                if(!changed) throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }
            default -> {
                throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }
        }
    }

    /**
     * Szabotőr paraméterként kapott nevű képességének használata.
     *
     * @param args a parancs elvárt paraméterei: {@code playeruse <játékos neve> <képesség neve>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void playerUse(String[] args) throws IllegalArgumentException {
        switch (args[2]){
            case "redirect" -> redirect((Pipe)Prototype.OBJECTS.get(args[3]), (Pipe)Prototype.OBJECTS.get(args[4]));
            case "leak" -> leak();
            case "makeItSticky" -> makeItSticky();
            case "makeItSlippery" -> makeItSlippery();
        }
    }
}
