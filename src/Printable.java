public abstract class Printable {
    protected String name;

    Printable(String name) { this.name = name; }

    @Override
    public String toString() { return '[' + name + ':' + getClass().getSimpleName() + ']'; }
}
