import java.util.Scanner;

public class Skeleton {
    private static final Scanner scanner = new Scanner(System.in);
    private static int indentLevel = 0;
    public static <T> void Call(T object, String function) {
        for (int i = 0; i < indentLevel; i++)
            System.out.print("   ");
        System.out.println("-> " + object + "." + function);
        indentLevel++;
    }
    public static void Return() {
        for (int i = 0; i < indentLevel-1; i++)
            System.out.print("   ");
        System.out.println("<-");
        indentLevel--;
    }
    public static <T> void Return(T object) {
        for (int i = 0; i < indentLevel-1; i++)
            System.out.print("   ");
        System.out.println("<- " + object);
        indentLevel--;
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
}
