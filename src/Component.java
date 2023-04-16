import java.util.ArrayList;
import java.util.Arrays;

/**
 * A csővezeték rendszer elemeinek az absztrakt őse. 
 * Az elemeken hívható valamennyi metódust tartalmazza. Az elemek alapértelmezett működését írja le. 
 * Az alapértelmezettől eltérő működés a leszármazott osztályokban felül lesz definiálva.
*/
public abstract class Component  extends Printable {
	
	/**
	 * A komponenst tartalmazó csőrendszer.
	 */
    protected static PipelineSystem pipelineSystem;
    
    /**
     * A komponens töröttsége.
     */
    protected boolean broken;
    
    /**
     * A komponensen tartózkodó játékosok.
     */
    protected final ArrayList<Player> players = new ArrayList<>();

    /**
     * Komponens konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Component(String name) {
        super(name);
    }

    /**
     * Inicializáló függvény, ami inicializálja a komponensen tartózkodó játékosokat.
     * 
     * @param players Kezdetben a komponensen tartózkodó játékosok.
     */
    public void InitializePlayers(Player... players) {
        this.players.addAll(Arrays.asList(players));
    }

    /**
     * Beállítja a komponens töröttségét.
     * 
     * @param broken komponens töröttségét jellemzi
     */
    public void SetBroken(boolean broken) {
        Skeleton.Call(this, "SetBroken(" + broken + ")");
        this.broken = broken;
        Skeleton.Return();
    }

    /**
     * Beállítja a komponenst tartalmazó csőrendszert.
     * 
     * @param pipelineSystem a komponenst tartalmazó csőrendszert
     */
    public static void SetPipelineSystem(PipelineSystem pipelineSystem) {
        Component.pipelineSystem = pipelineSystem;
    }

    /**
     * A kör végén végrehajtandó feladatokat megvalósító függvény.
     */
    public abstract void Step();

    /**
     * Szomszédos komponens beállítása.
     * 
     * @param component Az új szomszédos komponens
     */
    public abstract void AddNeighbor(Component component);

    /**
     * Egy szomszédos komponens törlése.
     * 
     * @param component A törlendő szomszédos komponens
     */
    public abstract void RemoveNeighbor(Component component);

    /**
     * Absztrakt metódus a víz pumpálás megvalósításához.
     * 
     * @param amount A hozzáadandó víz mennyisége
     * @return A hozzáadott víz mennyisége
     */
    public abstract int AddWater(int amount);

    /**
     * Absztrakt metódus a víz szívás megvalósításához.
     * 
     * @param amount A eltávolítandó víz mennyisége
     * @return Az eltávolított víz mennyisége
     */
    public abstract int RemoveWater(int amount);

    /**
     * Játékos fogadása a komponensre. A visszatérési értékkel válaszol, hogy tudja-e fogadni a játékost.
     * 
     * @param player A hozzáadandó játékos
     * @return Ha sikerült hozzáadni akkor igaz, egyébként hamis
     */
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + ")");
        AddPlayer(player);
        Skeleton.Return(true);
        return true;
    }

    /**
     * Játékos eltávolítása a komponensről.
     * 
     * @param player A eltávolítandó játékos
     * @return Ha sikerült eltávolítani akkor igaz, egyébként hamis
     */
    public void Remove(Player player) {
        Skeleton.Call(this, "Remove(" + player + ")");
        players.remove(player);
        Skeleton.Return();
    }

    /**
     * A komponens megjavításának megkísérlése.
     */
    public void Repair() {
        Skeleton.Call(this, "Repair(): Sikertelen");
        Skeleton.Return();
    }

    /**
     * A komponens kilyukasztásának megkísérlése. A függvény a törzse üres, mert csak csövön értelmezett.
     */
    public void Leak() {
        Skeleton.Call(this, "Leak(): Sikertelen");
        Skeleton.Return();
    }

    /**
     * A komponens átirányításának megkísérlése. A függvény a törzse üres, mert csak pumpán értelmezett.
     * 
     * @param source Az új forrás cső
     * @param destination Az új cél cső
     */

    public void Redirect(Pipe source, Pipe destination) {
        Skeleton.Call(this, "Redirect(" + source + ", " + destination + "): Sikertelen");
        Skeleton.Return();
    }

    /**
     * Pumpa elhelyezésének megkísérlése. Csak csövön értelmezett. 
     *  
     * @param pump A lerakni kívánt pumpa
     */
    public boolean PlacePump(Pump pump) {
        Skeleton.Call(this, "PlacePump(" + pump + ")");
        Skeleton.Return(false);
        return false;
    }

    /**
     * Cső megragadásának megkísérlése. A visszatérési értékkel közli, hogy sikeres volt-e a művelet.
     * 
     * @param pipe A megfogandó cső
     */
    public boolean GrabPipe(Pipe pipe) {
        Skeleton.Call(this, "GrabPipe(" + pipe + ")");
        Skeleton.Return(false);
        return false;
    }

    /**
     *  Cső elengedése/elhelyezése. A visszatérési értékkel közli, hogy sikeres volt-e a művelet.
     *  
     *  @param pipe A lerakandó cső
     */
    public boolean PlacePipe(Pipe pipe) {
        Skeleton.Call(this, "PlacePipe(" + pipe + ")");
        Skeleton.Return(false);
        return false;
    }

    /**
     * Játékos felvétele a players listára. Az Accept metódus használja.
     * 
     * @param player A játékos, akit fel akarunk venni a players listára
     */
    protected void AddPlayer(Player player) {
        Skeleton.Call(this, "AddPlayer(" + player + ")");
        players.add(player);
        Skeleton.Return();
    }
}
