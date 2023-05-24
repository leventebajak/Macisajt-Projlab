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
import java.util.ArrayList;

public abstract class View {

    public static Color MOCCASIN = new Color(255, 228, 181);
    public static Color SIENNA = new Color(160, 82, 45);
    public static JFrame FRAME = new JFrame("Sivatagi vízhálózat");
    public static MainMenuWindow MAIN_MENU_WINDOW = new MainMenuWindow();

    public static void main(String[] args) {
        FRAME.setPreferredSize(new Dimension(1080, 720));
        FRAME.setContentPane(MAIN_MENU_WINDOW);
        FRAME.pack();
        FRAME.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        FRAME.setLocationRelativeTo(null);
        FRAME.setResizable(false);
        FRAME.setVisible(true);
    }

    public static ArrayList<Drawable> drawables = new ArrayList<>();

    public static void drawAll() {
        // TODO: drawables összes elemének kirajzolása
    }
}
