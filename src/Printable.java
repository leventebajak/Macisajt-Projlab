import java.io.Serializable;

/**
 * Az objektumok elnevezéséhez és szerializálásához használt absztrakt ősosztály.
 */
public abstract class Printable implements Serializable {

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
        Prototype.OBJECTS.put(name, this);
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
