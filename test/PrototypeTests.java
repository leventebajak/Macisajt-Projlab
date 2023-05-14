import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrototypeTests {

    @Before
    public void setUp() {
        Prototype.runCommand("reset");
    }

    @AfterClass
    public static void tearDown() {
        Prototype.runCommand("reset");
    }

    // TODO: tests
    @Test
    public void testExample() {
        var input = """
                new cistern C
                new pump P1
                new pump P2
                new pump P3
                new spring F1
                new spring F2
                new pipe p1 P1 P2
                new pipe p2 P2 P3
                new pipe p3 P1 P3
                new pipe p4 C P1
                new pipe p5 P3 F1
                new pipe p6 P2 F2
                new saboteur S C
                new plumber M P3
                move S p4
                move S P1
                move S p1
                move S P2
                move S p6
                playeruse S makeitsticky
                move S P2
                move S p1
                playeruse S leak
                move S P2
                set P1 broken true
                move M p3
                move M P1
                playeruse M repair
                move M p1
                playeruse M repair
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat S component
                stat M component
                stat p6 sticky
                stat p1 broken
                stat P1 broken
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                component: P2
                component: p1
                sticky: true
                broken: false
                broken: false
                """;
        assertEquals(expectedOutput, output.toString());
    }
}