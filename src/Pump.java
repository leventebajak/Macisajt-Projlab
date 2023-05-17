/**
 * Pumpa komponens megvalósítása.
 * Felelőssége a csövek összekötése, és egy kijelölt
 * csőből egy másik kijelölt csőbe víz pumpálása.
 */
public class Pump extends Node {
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
     *
     * @param name: A pumpa neve
     */
    public Pump(String name) {
        super(name);
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

    /**
     * Új pumpa létrehozása a megadott névvel és csomópontokkal.
     *
     * @param args a parancs elvárt paraméterei: {@code new pump [pumpa neve]}
     * @return a létrehozott pumpa referenciája
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    public static Pump NEW(String[] args) throws IllegalArgumentException {
        if (args.length == 3) {
            if (Prototype.OBJECTS.containsKey(args[2])) throw new IllegalArgumentException("A név már foglalt!");
            return new Pump(args[2]);
        }
        if (args.length == 2) {
            int i = 1;
            while (Prototype.OBJECTS.containsKey("Pump" + i)) i++;
            return new Pump("Pump" + i);
        }
        throw new IllegalArgumentException("Érvénytelen paraméter!");
    }

    /**
     * Pumpa tulajdonságainak lekérdezése.
     *
     * @param args a parancs elvárt paraméterei: {@code stat <objektum neve> [tulajdonság neve]}
     * @return a lekérdezett tulajdonság értéke
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public String stat(String[] args) throws IllegalArgumentException {
        if (args.length == 2) {
            var result = new StringBuilder(this.toString());
            result.append("\npipes:");
            for (Pipe p : pipes)
                result.append(" ").append(p.name);
            result.append("\nplayers:");
            for (Player p : players)
                result.append(" ").append(p.name);
            result.append("\nbroken: ").append(broken);
            result.append("\ncapacity: ").append(CAPACITY);
            result.append("\nwaterLevel: ").append(waterLevel);
            result.append("\nlifetime: ").append(lifetime);
            result.append("\nsource: ").append((source == null) ? "null" : source.name);
            result.append("\ndestination: ").append((destination == null) ? "null" : destination.name);
            return result.toString();
        }

        if (args.length != 3) throw new IllegalArgumentException("Érvénytelen paraméter!");
        return switch (args[2].strip().toLowerCase()) {
            case "pipes" -> {
                var result = new StringBuilder("pipes:");
                for (Pipe p : pipes)
                    result.append(" ").append(p.name);
                yield result.toString();
            }
            case "players" -> {
                var result = new StringBuilder("players:");
                for (Player p : players)
                    result.append(" ").append(p.name);
                yield result.toString();
            }
            case "broken" -> "broken: " + broken;
            case "capacity" -> "capacity: " + CAPACITY;
            case "waterlevel" -> "waterLevel: " + waterLevel;
            case "lifetime" -> "lifetime: " + lifetime;
            case "source" -> "source: " + ((source == null) ? "null" : source.name);
            case "destination" -> "destination: " + ((destination == null) ? "null" : destination.name);
            default -> throw new IllegalArgumentException("A ciszternának nincs ilyen nevű tulajdonsága!");
        };
    }

    /**
     * Pumpa tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        if (args.length != 4) throw new IllegalArgumentException("Hiányzó paraméter!");

        args[3] = args[3].strip();
        switch (args[2].strip().toLowerCase()) {
            case "broken" -> {
                switch (args[3]) {
                    case "true" -> broken = true;
                    case "false" -> broken = false;
                    default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "waterlevel" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    if (!(0 <= value && value <= CAPACITY)) throw new IllegalArgumentException();
                    waterLevel = value;
                } catch (IllegalArgumentException ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "lifetime" -> {
                try {
                    int value = Integer.parseInt(args[3]);
                    if (!(0 <= value && value <= 10)) throw new IllegalArgumentException();
                    lifetime = value;
                } catch (IllegalArgumentException ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "source" -> {
                try {
                    if (args[3].equals("null")) {
                        setSource(null);
                        return;
                    }
                    if (!Prototype.OBJECTS.containsKey(args[3])) throw new IllegalArgumentException();
                    setSource((Pipe) Prototype.OBJECTS.get(args[3]));
                } catch (ClassCastException | IllegalArgumentException ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "destination" -> {
                try {
                    if (args[3].equals("null")) {
                        setDestination(null);
                        return;
                    }
                    if (!Prototype.OBJECTS.containsKey(args[3])) throw new IllegalArgumentException();
                    setDestination((Pipe) Prototype.OBJECTS.get(args[3]));
                } catch (ClassCastException | IllegalArgumentException ignored) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            default -> throw new IllegalArgumentException("Érvénytelen a megadott érték!");
        }
    }
}