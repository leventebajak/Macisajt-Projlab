import java.util.ArrayList;
import java.util.Arrays;

public class Pipe extends Component {
    /**
     * A csőhöz kapcsolódó csövek.
     */
    private final ArrayList<Node> nodes = new ArrayList<>();

    /**
     * Egy olyan érték amely megmutatja hogy a csövön van-e játékos
     * Ha van játékos akkor igaz egyébként hamis
     */
    private boolean occupied = false;

    /**
     * A cső állandó víz kapacítása ami 1 értéket vesz fel
     */
    private final int capacity = 1;

    /**
     * A csőben tárolt víz mennyísége, kezdetben 0 értéket vesz fel
     */
    private int waterLevel = 0;

    /**
     * A cső konstruktora.
     *
     * @param component: A cső neve
     */
    public Pipe(String name) { super(name); }

    /**
     * A csőben levő víz mennyiségének beállítása
     *
     * @param waterLevel A kezdeti víz mennyiségének beállítása
     */
    private void InitializeWaterLevel(int waterLevel) { this.waterLevel = waterLevel; }
    public void InitializePlayers(Player... players){
        super.InitializePlayers(players);
        if (players.length > 0)
            occupied = true;
    }

    /**
     * Inicializáló függvény, ami inicializálja a csövekhez tartozó csomópontokat.
     *
     * @param nodes A cső kezdeti csomópontjai.
     */
    public void InitializeNodes(Node... nodes){ this.nodes.addAll(Arrays.asList(nodes)); }
    public void SetOccupied(boolean occupied) {
        Skeleton.Call(this, "SetOccupied(" + occupied + ")");
        this.occupied = occupied;
        Skeleton.Return();
    }

    /**
     * Léptető függvény, a csővet lépteti.
     * Ha lyukas akkor kifolyik belőle a víz
     */
    public void Step() {
    	Skeleton.Call(this, "Step()");
    	if (Skeleton.TrueFalseQuestion("Törött a cső, vagy nincs bekötve az egyik végpontja?")) {
            pipelineSystem.LeakWater(1);
        }
    	Skeleton.Return();
    }

    /**
     * A cső egy szomszédjának hozzáadása a csomópontok (nodes) listába
     *
     * @param component A cső egy hozzáadandó szomszédja
     */
    public void AddNeighbor(Component component) {
        Skeleton.Call(this, "AddNeighbor(" + component + ")");
        nodes.add((Node) component);
        Skeleton.Return();
    }

    /**
     * A cső egy szomszédjának eltávolítása a csomópontok (nodes) listából
     *
     * @param component A cső egy eltávolítandó szomszédja
     */
    public void RemoveNeighbor(Component component) {
        Skeleton.Call(this, "RemoveNeighbor(" + component + ")");
        nodes.remove((Node) component);
        Skeleton.Return();
    }

    /**
     * A csőhöz egy adott mennyiségű víz hozzáadása
     *
     * @param amount A hozzáadandó víz mennyisége
     * @return A hozzáadott víz mennyisége
     */
    public int AddWater(int amount) {
        Skeleton.Call(this, "AddWater(" + amount + ")");
        InitializeWaterLevel(Skeleton.IntegerQuestion("A csőben lévő viz mennyisége:"));
        final int added = waterLevel + amount <= capacity ? amount : 0;
        waterLevel += added;
        Skeleton.Return(added);
        return added;
    }

    /**
     * A csőből egy adott mennyiségű víz eltávolítása
     *
     * @param amount A eltávolítandó víz mennyisége
     * @return Az eltávolított víz mennyisége
     */
    public int RemoveWater(int amount) {
        Skeleton.Call(this, "RemoveWater(" + amount + ")");
        InitializeWaterLevel(Skeleton.IntegerQuestion("A csőben lévő viz mennyisége:"));
        final int removed = waterLevel - amount >= 0 ? amount : waterLevel;
        waterLevel -= removed;
        Skeleton.Return(removed);
        return removed;
    }

    /**
     * A csövön tartózkodó játékos hozzáadása a csövön tartózkodó játékosok listájába
     *
     * @param player A hozzáadandó játékos
     * @return Ha sikerült hozzáadni akkor igaz, egyébként hamis
     */
    @Override
    public boolean Accept(Player player) {
        if(Skeleton.TrueFalseQuestion("Foglalt a cső?")){
            Skeleton.Call(this, "Accept(" + player + "): Sikertelen" );
            Skeleton.Return(false);
            return false;
        } else {
            Skeleton.Call(this, "Accept(" + player + "): Sikeres");
            SetOccupied(true);
            AddPlayer(player);
            Skeleton.Return(true);
            return true;
        }
    }

    /**
     * A csövön tartózkodó játékos eltávolítása a csövön tartózkodó játékosok listájából
     *
     * @param player A eltávolítandó játékos
     * @return Ha sikerült eltávolítani akkor igaz, egyébként hamis
     */
    @Override
    public void Remove(Player player) {
        Skeleton.Call(this, "Remove(" + player + ")");
        players.remove(player);
        SetOccupied(false);
        Skeleton.Return();
    }

    /**
     * A cső megjavítása amit a szerelő tudja meghívni
     */
    @Override
    public void Repair() {
        Skeleton.Call(this, "Repair()");
        SetBroken(false);
        Skeleton.Return();
    }

    /**
     * A cső kilyukasztása amit a szabotőr tudja meghívni
     */
    @Override
    public void Leak() {
        Skeleton.Call(this, "Leak()");
        SetBroken(true);
        Skeleton.Return();
    }

    /**
     * A csőre lerakni kívánt pumpa lerakása amit a szerelő tudja meghívni
     *
     * @param pump Az lerakni kívánt pumpa
     * @return Minden esetben igaz
     */
    @Override
    public boolean PlacePump(Pump pump) {
        Skeleton.Call(this, "PlacePump(" + pump + ")");
        Pipe newPipe = new Pipe("newPipe");
        Skeleton.Create(newPipe);
        Skeleton.Return();
        pipelineSystem.AddComponent(newPipe);
        newPipe.AddNeighbor(nodes.get(0));
        RemoveNeighbor(nodes.get(0));
        newPipe.AddNeighbor(pump);
        pump.AddNeighbor(newPipe);
        pump.AddNeighbor(this);
        AddNeighbor(pump);
        Skeleton.Return();
        return true;
    }
}
