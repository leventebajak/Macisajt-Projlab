import java.awt.*;

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
        // A játékos megjelenítése a komponens fölött
        int x = component.center.x ;
        int y = component.center.y+component.players.indexOf(this)*-30  ;
        if(component instanceof Node) y-=20;

        g.setColor(Color.BLACK);

        // A háromszög koordinátája
        int[] xPoints = {0+x, -20+x, 20+x};
        int[] yPoints = {0+y, -30+y, -30+y};

        // Draw the triangle
        g.fillPolygon(xPoints, yPoints, 3);
        g.setColor(Color.GRAY);
        g.drawPolygon(xPoints, yPoints, 3);
    }

    @Override
    public boolean intersect(Point point) {
        return false; // A játékosra kattintva nem történik semmi
    }

    @Override
    public void drawNameAndButtons(GameWindow gameWindow) {
        gameWindow.setPlayerPanel(new SaboteurPanel(this));
    }

    /**
     * A jelenlegi mező csúszóssá tételének megkísérlése.
     */
    public void makeItSlippery() {
        component.makeItSlippery();
        actionPerformed = true;
    }
}
