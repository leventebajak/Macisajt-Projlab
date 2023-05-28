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
        // A játékos megjelenítése a komponens fölött
        int x = component.center.x ;
        int y = component.center.y+component.players.indexOf(this)*-30  ;
        if(component instanceof Node) y-=20;

        if(Game.getActivePlayer() == this)
        	g.setColor(Color.CYAN);
        else
        	g.setColor(Color.BLACK);

        var trianglePoints = new Polygon(new int[]{x, x - 20, x + 20}, new int[]{y, y - 30, y - 30}, 3);

        g.fillPolygon(trianglePoints);
        g.setColor(OUTLINE_COLOR);
        g.drawPolygon(trianglePoints);
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
