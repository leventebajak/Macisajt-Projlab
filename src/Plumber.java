/**
 * Szerelő csapat játékosait reprezentáló osztály. Felelőssége a csövek megjavítása és új pumpa lerakása.
 */
public class Plumber extends Player {
	
	/**
	 * Az a cső, amit a szerelő felvett.
	 */
    private Pipe grabbedPipe;
    
    /**
     * Ez az attribútum a szerelőnél lévő pumpa referenciája.
     */
    private Pump grabbedPump;

    /**
     * Szerelő konstruktor.
     *
     * @param name a kiíráskor használt név
     */
    Plumber(String name) { super(name); }

    /**
     * Inicializáló függvény, ami a szerelő által felvett pumpát inicializálja.
     * 
     * @param pump Az a pumpa, amire inicilizálva lesz a szerelő által felvett pumpa.
     */
    public void InitializeGrabbedPump(Pump pump) { grabbedPump = pump; }
    
    /**
     * Inicializáló függvény, ami a szerelő által felvett csövet inicializálja.
     * 
     * @param pipe Az a cső, amire inicilizálva lesz a szerelő által felvett cső.
     */
    public void InitializeGrabbedPipe(Pipe pipe) { grabbedPipe = pipe; }
    
    /**
     * Setter függvény, ami a szerelő által felvett pumpát állítja be.
     * 
     * @param pump Az a pumpa, amire be lesz állítva a szerelő által felvett pumpa.
     */
    public void SetGrabbedPump(Pump pump) {
        Skeleton.Call(this, "SetGrabbedPump(" + pump + ")");
        this.grabbedPump = pump;
        Skeleton.Return();
    }
    
    /**
     * Setter függvény, ami a szerelő által felvett csövet inicializálja.
     * 
     * @param pipe Az a cső, amire be lesz állítva a szerelő által felvett cső.
     */
    public void SetGrabbedPipe(Pipe pipe) {
        Skeleton.Call(this, "SetGrabbedPipe(" + pipe + ")");
        this.grabbedPipe = pipe;
        Skeleton.Return();
    }

    /**
     * Pumpa átállítása, de csak akkor, ha a szerelő nem mozgat csövet.
     * 
     * @param source Az a cső, amelyből a pumpa szívja a vizet.
     * @param destination Az a cső, amelybe a pumpa pumpálja a vizet.
     */
    @Override
    public void Redirect(Pipe source, Pipe destination) {
        if (Skeleton.TrueFalseQuestion("Van cső a szerelőnél?")){
            Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikertelen");
        } else {
            Skeleton.Call(this, "Redirect("+ source + ", " + destination + "): Sikeres");
            component.Redirect(source, destination);
        }
        Skeleton.Return();
    }
    
    /**
     * A jelenlegi mező megjavításának megkísérlése. 
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet.
     */
    public void Repair() {
        if (Skeleton.TrueFalseQuestion("Van cső a szerelőnél?")) {
            Skeleton.Call(this, "Repair(): Sikertelen");
        } else {
            Skeleton.Call(this, "Repair(): Sikeres");
            component.Repair();
        }
        Skeleton.Return();
    }
    
    /**
     * A szerelőnek való pumpa adás megkísérlése.
     */
    @Override
    public void ReceivePump() {
        if (Skeleton.TrueFalseQuestion("Van pumpa a szerelőnél?")) {
            Skeleton.Call(this, "ReceivePump(): Sikertelen");
        } else {
            Skeleton.Call(this, "ReceivePump(): Sikeres");
            Pump newPump = new Pump("newPump");
            Skeleton.Create(newPump);
            Skeleton.Return();
            SetGrabbedPump(newPump);
        }
        Skeleton.Return();
    }
    
    /**
     * A játékosnál található pumpa lerakásának megkísérlése. 
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet, és van pumpa a szerelőnél.
     */
    public void PlacePump() {
        if (Skeleton.TrueFalseQuestion("Van cső a szerelőnél?") || !Skeleton.TrueFalseQuestion("Van pumpa a szerelőnél?")){
            Skeleton.Call(this, "PlacePump(): Sikertelen");
        } else {
            Skeleton.Call(this, "PlacePump(): Sikeres");
            if(component.PlacePump(grabbedPump))
                SetGrabbedPump(null);
        }
        Skeleton.Return();
    }
    
    /**
     * Egy megadott cső megfogásának megkísérlése. 
     * Csak akkor hajtható végre, ha a szerelő éppen nem mozgat csövet.
     */
    public void GrabPipe(Pipe pipe) {
        if (Skeleton.TrueFalseQuestion("Van cső a szerelőnél?")) {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikertelen");
        } else {
            Skeleton.Call(this, "GrabPipe(" + pipe + "): Sikeres");
            if (component.GrabPipe(pipe))
                SetGrabbedPipe(pipe);
        }
        Skeleton.Return();
    }
    
    /**
     * A játékos által megfogott cső lerakásának megkísérlése.
     */
    public void PlacePipe() {
        if (Skeleton.TrueFalseQuestion("Van cső a szerelőnél?")) {
            Skeleton.Call(this, "PlacePipe(): Sikertelen");
        } else {
            Skeleton.Call(this, "PlacePipe(): Sikeres");
            if(component.PlacePipe(grabbedPipe)){
               this.SetGrabbedPipe(null);
           }
        }
        Skeleton.Return();
    }
}
