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
        lifetime = (int)(Math.random() * 10) + 1;
    }

    /**
     * A pumpa forrás csővének beállítása.
     *
     * @param source Az új forrás cső ahonnan kapni fogja a vizet a pumpa
     */
    private void setSource(Pipe source) {
        // TODO: check whether the source pipe is a neighbor
        this.source = source;
    }

    /**
     * A pumpa cél csővének beállítása.
     *
     * @param destination Az új cél cső ahova adni fogja a vizet
     */
    private void setDestination(Pipe destination) {
        // TODO: check whether the destination pipe is a neighbor
        this.destination = destination;
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
        //if (broken) PIPELINE_SYSTEM.leakWater(removeWater(PIPELINE_SYSTEM.flowRate)); //TODO
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
        // TODO: check whether the source and destination are neighbors
        setSource(source);
        setDestination(destination);
    }

    /**
     * Egy cső felvétele az adott pumpánál.
     *
     * @param pipe A felvenni kívánt cső
     * @return Ha sikerült megfogni akkor igaz egyébként hamis
     */
    @Override
    public boolean grabPipe(Pipe pipe) {
        // TODO: check whether the pipe is a neighbor
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
     * @return Minden esetben igaz
     */
    @Override
    public boolean placePipe(Pipe pipe) {
        // TODO: check whether the pipe is already a neighbor
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
            if (Prototype.OBJECTS.containsKey(args[2]))
                throw new IllegalArgumentException("A név már foglalt!");
            return new Pump(args[2]);
        }
        if (args.length == 2) {
            int i = 1;
            while (Prototype.OBJECTS.containsKey("pump" + i))
                i++;
            return new Pump("pump" + i);
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
        String attr = new String();
        args[2] = args[2].strip().toLowerCase();
        switch (args[2]) {
        case "broken" -> { return "broken: " + broken; }
        case "capacity" ->  { return "capacity: " + CAPACITY; }
        case "waterlevel" ->  { return "waterLevel: " + waterLevel; }  
        case "lifetime" ->  { return "lifetime: " + lifetime; }  
        case "source" ->  { return "source: " + ((source==null) ? "null" : source.name); }
        case "destination" ->  { return "destination: " + ((destination==null) ? "null" : destination.name); }
        case "pipes" ->  {
            attr = attr + "pipes:";
            for(Pipe p : pipes)
                attr = attr + " "+ p.name;
        }
        case "players" ->  {
            attr = attr + "players:";
            for(Player p : players)
                attr = attr + " "+ p.name;
        }
        default -> {
            throw new IllegalArgumentException("A pumpának nincs ilyen nevű tulajdonsága");}
        }
        return attr;
    }

    /**
     * Pumpa tulajdonságainak beállítása.
     *
     * @param args a parancs elvárt paraméterei: {@code set <objektum neve> <tulajdonság neve> <új érték>}
     * @throws IllegalArgumentException érvénytelen paraméter
     */
    @Override
    public void set(String[] args) throws IllegalArgumentException {
        if(args.length<4){
            throw new IllegalArgumentException("Érvénytelen a megadott érték!");
        }
        args[2] = args[2].strip().toLowerCase();
        args[3] = args[3].strip().toLowerCase();

        switch (args[2]) {
            case "broken" -> {
                switch (args[3]){
                    case "true" ->  { broken=true; }
                    case "false" ->  { broken=false; }
                    default -> {throw new IllegalArgumentException("Érvénytelen a megadott érték!");}
                }
            }
            case "waterlevel" -> {
                try {
                    int watervalue=Integer.parseInt(args[3]);
                    if(!(0<=watervalue && watervalue<=CAPACITY)) throw new NumberFormatException();
                    waterLevel=watervalue;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Érvénytelen a megadott érték!");
                }
            }
            case "lifetime" -> {
                try {
                    int lifevalue=Integer.parseInt(args[3]);
                    if(lifevalue<0) throw new NumberFormatException();
                    lifetime=lifevalue;
                } catch (NumberFormatException e) {throw new IllegalArgumentException("Érvénytelen a megadott érték!");}
            }
            case "source" -> {
                boolean changed=false;
                for(Pipe p : pipes) if(p.name.toLowerCase().equals(args[3])){ source=p;changed=true;}
                if(args[3].equals("null")){ source=null;changed=true;}
                if(!changed) throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }
            case "destination" -> {
                boolean changed=false;
                for(Pipe p : pipes) if(p.name.toLowerCase().equals(args[3])){ destination=p;changed=true;}
                if(args[3].equals("null")){ destination=null;changed=true;}
                if(!changed) throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }
            default -> {
                throw new IllegalArgumentException("Érvénytelen a megadott érték!");
            }

        }


    }
}