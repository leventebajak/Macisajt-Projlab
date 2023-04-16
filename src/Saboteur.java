/**
 * Szabotőr csapat játékosait megvalósító osztály. Felelőssége a csövek kilyukasztása.
 */
public class Saboteur extends Player {
	
	/**
     * Szabotőr konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Saboteur(String name) { super(name); }

    /**
     * A jelenlegi mező kilyukasztásának megkísérlése. 
     */
    public void Leak() {
        Skeleton.Call(this, "Leak()");
        component.Leak();
        Skeleton.Return();
    }
}
