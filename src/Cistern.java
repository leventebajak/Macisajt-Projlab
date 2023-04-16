/**
 * A városok ciszternáit megvalósító osztály.
 * Felelőssége a beérkező víz gyűjtése és a szerelők pontszámának növelése.
 * Továbbá új csöveket hozhat létre.
 */
public class Cistern extends Node {

    /**
     * Ciszterna konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    public Cistern(String name) {
        super(name);
    }

    /**
     * Ez a függvény a kör végén hívódik meg.
     * A ciszterna az összes csatlakoztatott csőből vizet szív be és a
     * beszívott víz mennyiségével növeli a szerelő csapat pontszámát.
     * Véletlenszerűen új csövet is létrehozhat.
     */
    public void Step() {
        Skeleton.Call(this, "Step()");
        final int pipeCount = Skeleton.IntegerQuestion("A ciszternához csatlakozó csövek száma:");
        pipes.clear();
        for (int i = 0; i < pipeCount; i++) {
            pipes.add(new Pipe("cistern.pipes[" + i + "]"));
            int removedWater = pipes.get(i).RemoveWater(1);
            this.AddWater(removedWater);
        }
        if (Skeleton.TrueFalseQuestion("Jöjjön létre új cső?")) {
            Pipe newPipe = new Pipe("newPipe");
            Skeleton.Create(newPipe);
            Skeleton.Return();
            newPipe.AddNeighbor(this);
            this.AddNeighbor(newPipe);
            pipelineSystem.AddComponent(newPipe);
        }
        Skeleton.Return();
    }

    /**
     * A szerelő csapat pontszámának növelése.
     *
     * @param amount a bejövő víz mennyisége
     * @return minden esetben a paraméterként kapott szám
     */
    public int AddWater(int amount) {
        Skeleton.Call(this, "AddWater(" + amount + ")");
        pipelineSystem.CollectWater(amount);
        Skeleton.Return(amount);
        return amount;
    }

    /**
     * Ennek a függvénynek a törzse üres, mert a ciszternából nem szívható víz.
     *
     * @param amount a kimenő víz mennyisége
     * @return minden esetben 0
     */
    public int RemoveWater(int amount) {
        Skeleton.Call(this, "RemoveWater(" + amount + ")");
        Skeleton.Return(0);
        return 0;
    }

    /**
     * A ciszterna fogadja a játékost.
     * Ha a belépő játékosnál nincs pumpa akkor kap egyet.
     *
     * @param player a fogadott játékos
     * @return minden esetben igaz
     */
    @Override
    public boolean Accept(Player player) {
        Skeleton.Call(this, "Accept(" + player + "): Sikeres");
        AddPlayer(player);
        player.ReceivePump();
        Skeleton.Return(true);
        return true;
    }

    /**
     * A szerelő megfogja a csőnek azt a végét, amelyik nem a ciszternához csatlakozik.
     *
     * @param pipe a cső amit megfog a játékos
     * @return a megfogási kísérlet sikeressége
     */
    @Override
    public boolean GrabPipe(Pipe pipe) {
        if (Skeleton.TrueFalseQuestion("A megfogni kívánt cső foglalt?") || !Skeleton.TrueFalseQuestion("A megfogni kívánt csőnek van szabad vége")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
            Skeleton.Return(false);
            return false;
        } else {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
            pipe.SetOccupied(true);
            Skeleton.Return(true);
            return true;
        }
    }
}
