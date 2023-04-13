public class Main {
    public static void main(String[] args) {
        Saboteur s = new Saboteur("s");
        s.SetComponent(new Pipe("s.component"));
        s.Leak();

        System.out.println(Skeleton.TrueFalseQuestion("Jól vagy?"));
    }
}