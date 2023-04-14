import java.util.InputMismatchException;
import java.util.Scanner;

public class Skeleton {
    private static final Scanner scanner = new Scanner(System.in);
    private static int indentLevel = 0;

    public static void main(String[] args) {
        System.out.println("Sivatagi vízhálózat Skeleton");
        System.out.println("Csapat: 23 - macisajt");
        ChooseSequence();
//        System.out.println(TrueFalseQuestion("Jól vagy?"));
//        System.out.println(IntegerQuestion("Találj ki egy egész számot:"));
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
                return scanner.nextInt();
            } catch (InputMismatchException e) { scanner.nextLine(); }
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
                case "11" -> Sequence11_SaboteurLeaksPipe();
                case "0" -> System.exit(0);
            }
        }
    }

    public static void Sequence11_SaboteurLeaksPipe() {
        System.out.println("11. Szabotőr kilyukasztja a csövet:");
        Saboteur s = new Saboteur("s");
        s.SetComponent(new Pipe("s.component"));
        s.Leak();
    }
}
