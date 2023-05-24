import javax.swing.*;
import java.util.ArrayList;

public abstract class View {
    public static ArrayList<Drawable> drawables = new ArrayList<>();

    public static JFrame createMainMenu() {
        JFrame MainMenu = new JFrame();
        // TODO: főmenü elkészítése
        return MainMenu;
    }

    public static JFrame createNewGameWindow() {
        JFrame NewGameWindow = new JFrame();
        // TODO: új játék ablak elkészítése
        return NewGameWindow;
    }

    public static JFrame createGameWindow(Game game) {
        JFrame GameWindow = new JFrame();
        // TODO: játék ablak elkészítése
        return GameWindow;
    }

    public static void drawAll() {
        // TODO: drawables összes elemének kirajzolása
    }
}
