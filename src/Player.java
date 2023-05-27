import java.io.Serializable;

/**
 * A játékosokat megvalósító absztrakt osztály. Felelőssége a pumpák állítása és a csőrendszeren való mozgás.
 */
public abstract class Player implements Drawable, Serializable {

    protected String name;

    protected boolean moved = false;
    protected boolean actionPerformed = false;

    public abstract void drawNameAndButtons(GameWindow gameWindow);

    Player(String name) {
        this.name = name;
    }

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
     * Pumpa átállítása.
     *
     * @param source      Az a cső, amelyből a pumpa szívni fogja a vizet.
     * @param destination Az a cső, amelybe a pumpa pumpálni fogja a vizet.
     */
    public void redirect(Pipe source, Pipe destination) {
        component.redirect(source, destination);
        actionPerformed = true;
    }

    /**
     * A paraméterként kapott szomszédra való lépés megkísérlése.
     *
     * @param neighbor A szomszéd komponens, amire a játékos megkísérlési a rálépést.
     */
    public void move(Component neighbor) {
        if (!neighbor.accept(this))
            return;
        component.remove(this);
        component = neighbor;
        try {
            var pipe = (Pipe) component;
            if (pipe.slippery)
                move(pipe.getRandomNode());
        } catch (ClassCastException | NullPointerException ignored) {
        }
        moved = true;
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
        if (!ableToMove) {
            ableToMoveIn = Math.max(0, ableToMoveIn - 1);
            ableToMove = ableToMoveIn == 0;
        }
        actionPerformed = false;
        moved = false;
    }

    /**
     * A jelenlegi mező kilyukasztásának megkísérlése.
     */
    public void leak() {
        component.leak();
        actionPerformed = true;
    }

    /**
     * A jelenlegi mező ragadóssá tételének megkísérlése.
     */
    public void makeItSticky() {
        component.makeItSticky();
        actionPerformed = true;
    }

    /**
     * Beállítja a játékos {@link Player#ableToMoveIn} attribútumát egy véletlen értékre 1 és 5 között.
     */
    public void setAbleToMoveIn() {
        ableToMove = false;
        ableToMoveIn = (int) (Math.random() * 5) + 1;
    }
}
