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
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * View absztrakt osztály, ami a modell grafikus megjelenítéséért felel
 */
public abstract class View {

    /**
     * A grafikus felhasználói felület háttérszíne
     */
    public static Color PRIMARY_COLOR = new Color(160, 82, 45);

    /**
     * A grafikus felhasználói felület előtérszíne
     */
    public static Color SECONDARY_COLOR = new Color(255, 228, 181);

    /**
     * Az alkalmzás fő kerete
     */
    public static JFrame FRAME = new JFrame("Sivatagi vízhálózat");

    /**
     * Főmenü ablak
     */
    public static MainMenuWindow MAIN_MENU_WINDOW = new MainMenuWindow();

    /**
     * Játék ablak
     */
    public static GameWindow GAME_WINDOW = null;

    /**
     * Az alkalmazás belépési pontja
     *
     * @param args a parancssori argumentumok
     */
    public static void main(String[] args) {
        FRAME.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(MAIN_MENU_WINDOW);
        FRAME.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (Game.Instance != null) Game.SaveGame(GameWindow.autosave);
            }
        });
        FRAME.setResizable(false);
        FRAME.setVisible(true);
    }

    /**
     * Kirajzolja a csőrendszer elemeit és a játékosokat a pályán
     *
     * @param g A rajzoláshoz használt grafikai objektum
     */
    public static void drawAll(Graphics g) {
        if (Game.Instance == null)
            return;

        // Nem szép, de legalább a csomópontok a csövek fölé kerülnek
        for (var drawable : Game.Instance.pipelineSystem.components)
            if (drawable instanceof Pipe)
                drawable.drawOnMap(g);
        for (var drawable : Game.Instance.pipelineSystem.components)
            if (!(drawable instanceof Pipe))
                drawable.drawOnMap(g);

        for (var drawable : Game.Instance.players) {
            //System.out.println(drawable.component);
            drawable.drawOnMap(g);
        }
    }

    /**
     * Beállítja a fő keret tartalmi paneljét
     *
     * @param panel a fő keret új tartalmi panelja
     */
    public static void setContentPane(JPanel panel) {
        FRAME.setContentPane(panel);
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
        refresh();
    }

    /**
     * Frissíti a fő keretet
     */
    public static void refresh() {
        FRAME.invalidate();
        FRAME.validate();
        FRAME.repaint();
    }
}
