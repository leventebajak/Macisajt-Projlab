import java.awt.Graphics;

/**
 * Pumpa komponens megvalósítása.
 * Felelőssége a csövek összekötése, és egy kijelölt
 * csőből egy másik kijelölt csőbe víz pumpálása.
 */
public class Pump extends Node {

    @Override
    public void drawOnMap(Graphics g) {
        // TODO: Pumpa felrajzolása a View.MAP-ra a center attribútum használatával
        //  (még ki kell találni hogyan jelöljük a forrás- és célcsöveket, esetleg '-' '+'-al?)
    }

    @Override
    public boolean intersect(Point point) {
        // TODO: metszet eldöntése
        return false;
    }

    /**
     * A pumpa töröttsége.
     */
    private boolean broken = false;

    /**
     * A pumpa állandó víz kapacítása ami 1 értéket vesz fel
     */
    private static final int CAPACITY = 1;

    /**
     * A pumpában tárolt víz mennyísége
     */
    private int waterLevel = 0;

    /**
     * A pumpa élettartalma
     */
    private int lifetime;

    /**
     * A pumpa forráscsöve
     */
    private Pipe source = null;

    /**
     * A pumpa célcsöve
     */
    private Pipe destination = null;

    /**
     * A pumpa konstruktora.
     */
    public Pump() {
        setLifetime();
    }

    /**
     * A pumpa élettartalmának beállítása.
     */
    private void setLifetime() {
        lifetime = (int) (Math.random() * 10) + 1;
    }

    /**
     * A pumpa forrás csővének beállítása.
     *
     * @param source Az új forrás cső ahonnan kapni fogja a vizet a pumpa
     */
    private void setSource(Pipe source) {
        if (source == null || pipes.contains(source))
            this.source = source;
        else System.out.println("A cső nem szomszédos a pumpával!");
    }

    /**
     * A pumpa cél csővének beállítása.
     *
     * @param destination Az új cél cső ahova adni fogja a vizet
     */
    private void setDestination(Pipe destination) {
        if (destination == null || pipes.contains(destination))
            this.destination = destination;
        else System.out.println("A cső nem szomszédos a pumpával!");
    }

    /**
     * A pumpa léptetése.
     * A pumpa vizet kap egy csőtől és vizet továbbít egy szomszédos csőnek.
     * Ha lejár az élettartalma a pumpának akkor elromlik.
     */
    public void step() {
        if (!broken) {
            decreaseLifetime();
            broken = lifetime == 0;
        }
        if (broken) PIPELINE_SYSTEM.leakWater(removeWater(PIPELINE_SYSTEM.flowRate));
        if (source != null) addWater(source.removeWater(Math.min(CAPACITY - waterLevel, PIPELINE_SYSTEM.flowRate)));
        if (destination != null) removeWater(destination.addWater(Math.min(waterLevel, PIPELINE_SYSTEM.flowRate)));
    }

    /**
     * A pumpa életerejének a csökkentése 1 egységgel.
     */
    private void decreaseLifetime() {
        lifetime = Math.max(0, lifetime - 1);
    }

    /**
     * A pumpához egy adott mennyiségű víz hozzáadása.
     *
     * @param amount A hozzáadandó víz mennyisége
     * @return A hozzáadott víz mennyisége
     */
    public int addWater(int amount) {
        amount = Math.min(amount, PIPELINE_SYSTEM.flowRate);
        final int added = waterLevel + amount <= CAPACITY ? amount : 0;
        waterLevel += added;
        return added;
    }

    /**
     * A pumpából egy adott mennyiségű víz eltávolítása.
     *
     * @param amount A eltávolítandó víz mennyisége
     * @return Az eltávolított víz mennyisége
     */
    public int removeWater(int amount) {
        amount = Math.min(amount, PIPELINE_SYSTEM.flowRate);
        final int removed = waterLevel - amount >= 0 ? amount : waterLevel;
        waterLevel -= removed;
        return removed;
    }

    /**
     * A cső megjavítása.
     */
    @Override
    public void repair() {
        if (broken) {
            broken = false;
            setLifetime();
        }
    }

    /**
     * A pumpa átirányítása.
     *
     * @param source      Az új forráscső
     * @param destination Az új célcső
     */
    @Override
    public void redirect(Pipe source, Pipe destination) {
        setSource(source);
        setDestination(destination);
    }

    /**
     * Egy cső felvétele az adott pumpánál.
     *
     * @param pipe A felvenni kívánt cső
     * @return Ha sikerült megfogni a csövet akkor igaz, egyébként hamis
     */
    @Override
    public boolean grabPipe(Pipe pipe) {
        if (!pipes.contains(pipe))
            return false;

        if (pipe.getOccupied()) return false;

        if (pipe == source) setSource(null);
        if (pipe == destination) setDestination(null);
        pipe.removeNeighbor(this);
        removeNeighbor(pipe);
        return true;
    }

    /**
     * Egy cső lerakása az adott pumpánál.
     *
     * @param pipe A lerakandó cső
     * @return Ha sikerült lerakni a csövet akkor igaz, egyébként hamis
     */
    @Override
    public boolean placePipe(Pipe pipe) {
        if (pipes.contains(pipe))
            return false;

        pipe.addNeighbor(this);
        addNeighbor(pipe);
        pipe.setOccupied(false);
        return true;
    }
}