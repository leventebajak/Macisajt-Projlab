/**
 * A játékosokat megvalósító absztrakt osztály. Felelőssége a pumpák állítása és a csőrendszeren való mozgás.
 */
public abstract class Player extends Printable {

    /**
     * A komponens, amin a játékos áll.
     */
    protected Component component;

    /**
     * Azt jellemzi, hogy képes-e a játékos mozogni.
     */
    protected boolean ableToMove = true;

    /**
     * Azt jellemzi, hogy hány körnek kell eltelnie ahhoz, hogy a játékos újra tudjon mozogni.
     */
    protected int ableToMoveIn = 0;

    /**
     * Játékos konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    Player(String name) {
        super(name);
    }

    /**
     * Pumpa átállítása.
     *
     * @param source      Az a cső, amelyből a pumpa szívni fogja a vizet.
     * @param destination Az a cső, amelybe a pumpa pumpálni fogja a vizet.
     */
    public void redirect(Pipe source, Pipe destination) {
        component.redirect(source, destination);
    }

    /**
     * A paraméterként kapott szomszédra való lépés megkísérlése.
     *
     * @param neighbor A szomszéd komponens, amire a játékos megkísérlési a rálépést.
     */
    public void move(Component neighbor) {
        if (neighbor.accept(this)) {
            component.remove(this);
            component = neighbor;
        }
    }

    /**
     * A játékosnak való pumpa adás megkísérlése.
     * Ez a függvény csak a szerelő játékosnál értelmezett.
     */
    public void receivePump() {
    }

    /**
     * Kör végén hívandó függvény, ami csökkenti az {@link Player#ableToMoveIn} attribútum értékét.
     */
    public void step() {
        if(!ableToMove){
            ableToMoveIn = Math.max(0, ableToMoveIn - 1);
            ableToMove = ableToMoveIn == 0;
        }
    }

    /**
     * A jelenlegi mező kilyukasztásának megkísérlése.
     */
    public void leak() {
        component.leak();
    }

    /**
     * A jelenlegi mező ragadóssá tételének megkísérlése.
     */
    public void makeItSticky() {
        component.makeItSticky();
    }

    /**
     * Beállítja a játékos {@link Player#ableToMoveIn} attribútumát egy véletlen értékre 1 és 5 között.
     */
    public void setAbleToMoveIn() {
        ableToMoveIn = (int)(Math.random() * 5) + 1;
    }

    /**
     * Játékos mozgatása a paraméterként kapott mezőre.
     *
     * @param args a parancs elvárt paraméterei: {@code move <játékos neve> <komponens neve>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public void movePlayer(String[] args) throws IllegalArgumentException {
        if (args.length != 3) throw new IllegalArgumentException("Érvénytelen paraméter!");
        
        Object componentobject = Prototype.OBJECTS.get(args[2]);
        if (componentobject == null) 
        	throw new IllegalArgumentException("Nincs ilyen nevű komponens objektum!");
        
        if(ableToMove) {
        	try {
        		if (((Component) componentobject).accept(this)) {
        			component.remove(this);
        			component = ((Component) componentobject);
        			} 
        		} catch (ClassCastException ignored) {}
        }
    }

    /**
     * Játékos paraméterként kapott nevű képességének használata.
     *
     * @param args a parancs elvárt paraméterei: {@code playeruse <játékos neve> <képesség neve>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public abstract void playerUse(String[] args) throws IllegalArgumentException;
}
