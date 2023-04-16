/**
 * Az objektumok kiírásához halsznált absztrakt ősosztály.
 */
public abstract class Printable {
    /**
     * Az objektum neve
     */
    protected String name;

    /**
     * Kiírható objektumok konstruktora.
     *
     * @param name a kiírandó név
     */
    Printable(String name) {
        this.name = name;
    }

    /**
     * Felüldefiniált toString metódus, ami az objektum osztályát is kiírja.
     *
     * @return a kiírandó String
     */
    @Override
    public String toString() {
        return '[' + name + ':' + getClass().getSimpleName() + ']';
    }
}
