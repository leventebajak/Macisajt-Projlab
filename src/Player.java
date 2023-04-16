/**
 * A játékosokat megvalósító absztrakt osztály. Felelőssége a pumpák állítása és a csőrendszeren való mozgás.
 */
public abstract class Player extends Printable {

    /**
     * A komponens, amin a játékos áll.
     */
    protected Component component;

    /**
     * Játékos konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    Player(String name) {
        super(name);
    }

    /**
     * Inicializáló függvény, ami inicializálja a játékos komponensét.
     *
     * @param component Az a komponens, amire inicilizálva lesz a játékos komponense.
     */
    public void InitializeComponent(Component component) {
        this.component = component;
    }

    /**
     * Pumpa átállítása.
     *
     * @param source Az a cső, amelyből a pumpa szívni fogja a vizet.
     * @param destination Az a cső, amelybe a pumpa pumpálni fogja a vizet.
     */
    public void Redirect(Pipe source, Pipe destination) {
        Skeleton.Call(this, "Redirect(" + source + ", " + destination + ")");
        component.Redirect(source, destination);
        Skeleton.Return();
    }

    /**
     * A paraméterként kapott szomszédra való lépés megkísérlése.
     *
     * @param neighbor A szomszéd komponens, amire a játékos megkísérlési a rálépést.
     */
    public void Move(Component neighbor) {
        Skeleton.Call(this, "Move(" + neighbor + ")");
        if (neighbor.Accept(this)) {
            component.Remove(this);
            component = neighbor;
        }
        Skeleton.Return();
    }

    /**
     * A játékosnak való pumpa adás megkísérlése.
     * Ez a függvény csak a szerelő játékosnál értelmezett.
     */
    public void ReceivePump() {
        Skeleton.Call(this, "ReceivePump(): Sikertelen");
        Skeleton.Return();
    }
}
