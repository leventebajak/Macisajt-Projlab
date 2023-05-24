import javax.swing.*;

/**
 * Ez a program a "Sivatagi vízhálózat" feladat grafikus változata.
 *
 * @author Baják Levente Imre
 * @author Csikai Valér Zsolt
 * @author Koszoru Domonkos
 * @author Le Ngoc Toan
 * @author Stróbl Dániel Alajos
 */

public abstract class Model {
    public static void main(String[] args) {
        // TODO: ablakok létrehozása
        JFrame MainMenu = View.createMainMenu();
//        MainMenu.setVisible(true);
    }

    public static void GenerateMap() {
        // TODO: a Game.Instance mapjának generálása
    }
}
