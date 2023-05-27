/**
 * Ez a program a "Sivatagi vízhálózat" feladat grafikus változata.
 *
 * @author Baják Levente Imre
 * @author Csikai Valér Zsolt
 * @author Koszoru Domonkos
 * @author Le Ngoc Toan
 * @author Stróbl Dániel Alajos
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public abstract class View {
    public static Color PRIMARY_COLOR = new Color(160, 82, 45);
    public static Color SECONDARY_COLOR = new Color(255, 228, 181);
    public static JFrame FRAME = new JFrame("Sivatagi vízhálózat");
    public static MainMenuWindow MAIN_MENU_WINDOW = new MainMenuWindow();

    public static GameWindow GAME_WINDOW = null;

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






        for (var drawable : Game.Instance.players){
            //System.out.println(drawable.component);
            drawable.drawOnMap(g);
        }


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
