import java.util.InputMismatchException;
import java.util.Scanner;

public class Skeleton {
    private static final Scanner scanner = new Scanner(System.in);
    private static int indentLevel = 0;

    public static void main(String[] args) {
        System.out.println("Sivatagi vízhálózat Skeleton");
        System.out.println("Csapat: 23 - macisajt");
        ChooseSequence();
    }

    public static <T> void Call(T object, String function) {
        for (int i = 0; i < indentLevel; i++)
            System.out.print("   ");
        System.out.println("-> " + object + "." + function);
        indentLevel++;
    }
    public static void Return() {
        indentLevel--;
        for (int i = 0; i < indentLevel; i++)
            System.out.print("   ");
        System.out.println("<-");
    }
    public static <T> void Return(T object) {
        indentLevel--;
        for (int i = 0; i < indentLevel; i++)
            System.out.print("   ");
        System.out.println("<- " + object);
    }

    public static boolean TrueFalseQuestion(String question) {
        while (true) {
            System.out.print("? " + question + " I/N: ");
            switch (scanner.nextLine().toUpperCase()) {
                case "I" -> { return true; }
                case "N" -> { return false; }
            }
        }
    }
    public static int IntegerQuestion(String question) {
        while (true) {
            System.out.print("? " + question + " ");
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) { scanner.nextLine(); }
        }
    }

    public static void ChooseSequence() {
        System.out.println("""
                            Szekvenciák:
                            1.  Játék indítása
                            2.  Hegyi forrás léptetése
                            3.  Ciszterna léptetése
                            4.  Pumpa léptetése
                            5.  Cső léptetése
                            6.  Szabotőr csőre lép
                            7.  Szabotőr ciszternára lép
                            8.  Szabotőr pumpára lép
                            9.  Szabotőr hegyi forrásra lép
                            10. Szabotőr átállítja a pumpát
                            11. Szabotőr kilyukasztja a csövet
                            12. Szerelő csőre lép
                            13. Szerelő ciszternára lép
                            14. Szerelő pumpára lép
                            15. Szerelő hegyi forrásra lép
                            16. Szerelő átállítja a pumpát
                            17. Szerelő megjavítja a csövet
                            18. Szerelő megjavítja a pumpát
                            19. Szerelő lerakja a pumpát egy csőre
                            20. Szerelő megfog egy csövet egy ciszternánál
                            21. Szerelő megfog egy csövet egy pumpánál
                            22. Szerelő lerakja a csövet egy pumpánál""");
        while (true) {
            System.out.print("? Válasszon egy szekvenciát (Kilépés: 0): ");
            switch (scanner.nextLine()) {
                case "1" -> Sequence1_StartGame();
                case "2" -> Sequence2_SpringSteps();
                case "3" -> Sequence3_CisternSteps();
                case "4" -> Sequence4_PumpSteps();
                case "6" -> Sequence6_SaboteurMovesToaPipe();
                case "7" -> Sequence7_SaboteurMovesToaCistern();
                case "8" -> Sequence8_SaboteurMovesToaPump();
                case "9" -> Sequence9_SaboteurMovesToaSpring();
                case "10" -> Sequence10_SaboteurRedirectsPump();
                case "11" -> Sequence11_SaboteurLeaksPipe();
                case "12" -> Sequence12_PlumberMovesToaPipe();
                case "13" -> Sequence13_PlumberMovesToaCistern();
                case "14" -> Sequence14_PlumberMovesToaPump();
                case "15" -> Sequence15_PlumberMovesToaSpring();
                case "16" -> Sequence16_PlumberRedirectsPump();
                case "17" -> Sequence17_PlumberRepairsPipe();
                case "18" -> Sequence18_PlumberRepairsPump();
                case "20" -> Sequence20_PlumberGrabsPipeAtCistern();
                case "21" -> Sequence21_PlumberGrabsaPipeAtaPump();
                case "0" -> System.exit(0);
            }
        }
    }

    public static void Sequence1_StartGame(){
        System.out.println("1.  Játék indítása:");
        Game.StartGame();
    }
    public static void Sequence2_SpringSteps(){
        System.out.println("2.  Hegyi forrás léptetése:");
        new Spring("spring").Step();
    }
    public static void Sequence3_CisternSteps() {
    	System.out.println("3.  Ciszterna léptetése:");
    	PipelineSystem pipelinesystem = new PipelineSystem("Component.pipelineSystem");
        Component.SetPipelineSystem(pipelinesystem);
        Cistern cistern = new Cistern("cistern");
        cistern.Step();
    }
    public static void Sequence4_PumpSteps() {
    	System.out.println("4.  Pumpa léptetése:");
    	PipelineSystem pipelinesystem = new PipelineSystem("Component.pipelineSystem");
        Component.SetPipelineSystem(pipelinesystem);
        Pump pump = new Pump("pump");
        pump.Step();
    }
    
    public static void Sequence6_SaboteurMovesToaPipe(){
        System.out.println("6. Szabotőr csőre lép:");
        Saboteur s = new Saboteur("s");
        Pump pump = new Pump("s.component");
        Pipe pipe = new Pipe("pipe");
        s.SetComponent(pump);
        pump.SetPlayers(s);
        s.Move(pipe);
    }
    public static void Sequence7_SaboteurMovesToaCistern(){
        System.out.println("7. Szabotőr ciszternára lép:");
        Saboteur s = new Saboteur("s");
        Pipe pipe = new Pipe("s.component");
        Cistern cistern = new Cistern("cistern");
        s.SetComponent(pipe);
        pipe.SetPlayers(s);
        s.Move(cistern);
    }
    public static void Sequence8_SaboteurMovesToaPump(){
        System.out.println("8. Szabotőr pumpára lép:");
        Saboteur s = new Saboteur("s");
        Pipe pipe = new Pipe("s.component");
        Pump pump = new Pump("pump");
        s.SetComponent(pipe);
        pipe.SetPlayers(s);
        s.Move(pump);
    }
    public static void Sequence9_SaboteurMovesToaSpring(){
        System.out.println("9. Szabotőr hegyi forrásra lép:");
        Saboteur s = new Saboteur("s");
        Pipe pipe = new Pipe("s.component");
        Spring spring = new Spring("spring");
        s.SetComponent(pipe);
        pipe.SetPlayers(s);
        s.Move(spring);
    }
    public static void Sequence10_SaboteurRedirectsPump(){
        System.out.println("10. Szabotőr átállítja a pumpát:");
        Saboteur s = new Saboteur("s");
        Pump pump = new Pump("s.component");
        Pipe source = new Pipe("source");
        Pipe destination = new Pipe("destination");
        s.SetComponent(pump);
        pump.SetPlayers(s);
        s.Redirect(source, destination);
    }
    public static void Sequence11_SaboteurLeaksPipe() {
        System.out.println("11. Szabotőr kilyukasztja a csövet:");
        Saboteur s = new Saboteur("s");
        s.SetComponent(new Pipe("s.component"));
        s.Leak();
    }
    public static void Sequence12_PlumberMovesToaPipe(){
        System.out.println("12. Szerelő csőre lép");
        Plumber p = new Plumber("p");
        Pump pump = new Pump("p.component");
        Pipe pipe = new Pipe("pipe");
        p.SetComponent(pump);
        pump.SetPlayers(p);
        p.Move(pipe);
    }
    public static void Sequence13_PlumberMovesToaCistern() {
        System.out.println("13. Szerelő ciszternára lép");
        Plumber p = new Plumber("p");
        Pipe pipe = new Pipe("p.component");
        Cistern cistern = new Cistern("cistern");
        p.SetComponent(pipe);
        pipe.SetPlayers(p);
        p.Move(cistern);
    }
    public static void Sequence14_PlumberMovesToaPump() {
        System.out.println("14. Szerelő pumpára lép");
        Plumber p = new Plumber("p");
        Pipe pipe = new Pipe("p.component");
        Pump pump = new Pump("pump");
        p.SetComponent(pipe);
        pipe.SetPlayers(p);
        p.Move(pump);
    }
    public static void Sequence15_PlumberMovesToaSpring() {
        System.out.println("15. Szerelő hegyi forrásra lép");
        Plumber p = new Plumber("p");
        Pipe pipe = new Pipe("p.component");
        Spring spring = new Spring("spring");
        p.SetComponent(pipe);
        pipe.SetPlayers(p);
        p.Move(spring);
    }
    public static void Sequence16_PlumberRedirectsPump() {
        System.out.println("16. Szerelő átállítja a pumpát:");
        Plumber p = new Plumber("p");
        Pump pump = new Pump("p.component");
        Pipe source = new Pipe("source");
        Pipe destination = new Pipe("destination");
        p.SetComponent(pump);
        pump.SetPlayers(p);
        p.Redirect(source, destination);
    }
    public static void Sequence17_PlumberRepairsPipe() {
        System.out.println("17. Szerelő megjavítja a csövet:");
        Plumber p = new Plumber("p");
        Pipe pipe = new Pipe("p.component");
        p.SetComponent(pipe);
        pipe.SetPlayers(p);
        p.Repair();
    }
    public static void Sequence18_PlumberRepairsPump() {
        System.out.println("18. Szerelő megjavítja a pumpát:");
        Plumber p = new Plumber("p");
        Pump pump = new Pump("p.component");
        p.SetComponent(pump);
        pump.SetPlayers(p);
        p.Repair();
    }

    public static void Sequence20_PlumberGrabsPipeAtCistern() {
        System.out.println("20. Szerelő megfog egy csövet egy ciszternánál:");
        Plumber plumber = new Plumber("plumber");
        plumber.SetComponent(new Cistern("plumber.component"));
        Pipe pipe=new Pipe("pipe");
        plumber.GrabPipe(pipe);
    }
    public static void Sequence21_PlumberGrabsaPipeAtaPump() {
        System.out.println("21. Szerelő megfog egy csövet egy pumpánál:");
        Plumber plumber = new Plumber("plumber");
        Pump pump = new Pump("plumber.component");
        Pipe pipe = new Pipe("pipe");
        plumber.SetComponent(pump);
        plumber.GrabPipe(pipe);
    }
}