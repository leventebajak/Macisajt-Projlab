import java.util.ArrayList;

/**
 * A csővezeték hálózat csomópontjainak absztrakt ősosztálya.
 * Felelőssége a csövekkel való kapcsolat megvalósítása.
 */
public abstract class Node extends Component {

    /**
     * A csomópont grafikus megjelenítésére szolgáló kör sugara
     */
    public static final int radius = 20;

    /**
     * A csomóponthoz kapcsolódó csövek.
     */
    protected final ArrayList<Pipe> pipes = new ArrayList<>();

    /**
     * Játékos fogadása a csomópontra. A visszatérési értékkel válaszol, hogy tudja-e fogadni a játékost.
     *
     * @param player A játékos akit fogani kell
     * @return Ha sikerült fogadni akkor igaz, egyébként hamis
     */
    public boolean accept(Player player) {
        try {
            if (player.component == null || pipes.contains((Pipe) player.component)) {
                players.add(player);
                return true;
            }
            System.out.println("A csomópontra csak szomszédos csőről lehet lépni!");
            return false;
        } catch (ClassCastException ignored) {
            System.out.println("A csomópontra csak csőről lehet lépni!");
            return false;
        }
    }


    /**
     * Szomszédos cső beállítása.
     *
     * @param component Az új szomszédos cső
     */
    public void addNeighbor(Component component) {
        try {
            this.pipes.add((Pipe) component);
        } catch (ClassCastException ignored) {
            System.out.println("A komponens nem egy cső!");
        }
    }

    /**
     * Egy szomszédos cső törlése.
     *
     * @param component A törlendő szomszédos cső
     */
    public void removeNeighbor(Component component) {
        try {
            this.pipes.remove((Pipe) component);
        } catch (ClassCastException ignored) {
            System.out.println("A komponens nem egy cső!");
        }
    }
}
