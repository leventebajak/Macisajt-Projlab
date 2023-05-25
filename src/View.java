/**
 * Ez a program a "Sivatagi vízhálózat" feladat grafikus változata.
 *
 * @author Baják Levente Imre
 * @author Csikai Valér Zsolt
 * @author Koszoru Domonkos
 * @author Le Ngoc Toan
 * @author Stróbl Dániel Alajos
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.Line2D;

public abstract class View {
    public static Color PRIMARY_COLOR = new Color(160, 82, 45);
    public static Color SECONDARY_COLOR = new Color(255, 228, 181);
    public static JFrame FRAME = new JFrame("Sivatagi vízhálózat");
    public static MainMenuWindow MAIN_MENU_WINDOW = new MainMenuWindow();

    public static GameWindow GAME_WINDOW = null;

    public static void main(String[] args) {
        FRAME.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setContentPane(MAIN_MENU_WINDOW);
        FRAME.setResizable(false);
        FRAME.setVisible(true);
    }

    public static void drawAll(Graphics g) {
        // Demó:
//        g.setColor(Color.gray);
//        Graphics2D g2 = (Graphics2D) g;
//        g2.setStroke(new BasicStroke(15));
//        g2.draw(new Line2D.Float(150, 200, 70, 300));
//        g.setColor(Color.red);
//        g.fillOval(130, 180, 40,40);

        if (Game.Instance == null)
            return;

        // Nem szép, de legalább a csomópontok a csövek fölé kerülnek
        for (var drawable : Game.Instance.pipelineSystem.components)
            if (drawable instanceof Pipe)
                drawable.drawOnMap(g);
        for (var drawable : Game.Instance.pipelineSystem.components)
            if (!(drawable instanceof Pipe))
                drawable.drawOnMap(g);
        for (var drawable : Game.Instance.players)
            drawable.drawOnMap(g);
    }

    public static void setContentPane(JPanel panel) {
        FRAME.setContentPane(panel);
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
        refresh();
    }

    public static void refresh() {
        FRAME.invalidate();
        FRAME.validate();
        FRAME.repaint();
    }
}
