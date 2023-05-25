import java.awt.Graphics;

/**
 * Szabotőr csapat játékosait megvalósító osztály. Felelőssége a csövek kilyukasztása.
 */
public class Saboteur extends Player {

    Saboteur(String name) {
        super(name);
    }

    @Override
    public void drawOnMap(Graphics g) {
        // TODO: játékos felrajzolása
    }

    @Override
    public boolean intersect(Point point) {
        return false; // A játékosra kattintva nem történik semmi
    }

    @Override
    public void drawNameAndButtons() {
        // TODO: szerelő nevének, és gombjainak felrakása a penelre
        View.GAME_WINDOW.setPlayerPanel(new SaboteurPanel(this));
    }

    /**
     * A jelenlegi mező csúszóssá tételének megkísérlése.
     */
    public void makeItSlippery() {
        component.makeItSlippery();
    }
}
