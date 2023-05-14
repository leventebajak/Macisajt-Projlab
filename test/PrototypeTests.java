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

    @Test
    public void test8_4_1() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                new plumber Plumber1 Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Game plumbers
                stat Plumber1 component
                stat Game saboteurs
                stat Saboteur1 component
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                plumbers: Plumber1
                component: Pump1
                saboteurs: Saboteur1
                component: Pipe1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_2() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 sticky
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");

        input = """
                set Pipe1 sticky true
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 sticky
                stat Plumber1 ableToMove
                stat Plumber1 ableToMoveIn
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Plumber1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Plumber1 ableToMove
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                sticky: false
                sticky: true
                ableToMove: true
                ableToMoveIn: 0
                ableToMove: false
                """;
        assertEquals(expectedOutput, output.toString());
        var ableToMoveIn = Integer.parseInt(Prototype.stat("stat Plumber1 ableToMoveIn".split(" ")).split(" ")[1]);
        assertTrue(1 <= ableToMoveIn && ableToMoveIn <= 5);
    }

    @Test
    public void test8_4_3() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                set Pipe1 slipperyFor 5
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 slippery
                stat Plumber1 component
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Plumber1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var expectedOutput = """
                slippery: true
                component: Pump1
                """;
        assertEquals(expectedOutput, output.toString());
        var component = Prototype.stat("stat Plumber1 component".split(" "));
        assertTrue(component.equals("component: Cistern1") || component.equals("component: Pump1"));
    }

    @Test
    public void test8_4_4() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                new plumber Plumber1 Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 occupied
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Plumber1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Plumber1 component
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                occupied: true
                component: Pump1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_5() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Saboteur1 leak
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: false
                leakable: true
                broken: true
                leakable: false
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_6() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                new plumber Plumber1 Pump1
                playeruse Saboteur1 leak
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Saboteur1 Pump1
                move Plumber1 Pipe1
                playeruse Plumber1 repair
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 broken
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: true
                leakable: false
                broken: false
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_7() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                set Pipe1 broken true
                set Pipe1 leakable false
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Saboteur1 leak
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: true
                leakable: false
                broken: true
                leakable: false
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_8() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pipe1
                set Pipe1 broken false
                set Pipe1 leakable true
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Plumber1 repair
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: false
                leakable: true
                broken: false
                leakable: true
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_9() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pipe1
                set Pipe1 broken true
                set Pipe1 leakable false
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Plumber1 repair
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 broken
                stat Pipe1 leakable
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: true
                leakable: false
                broken: false
                leakable: false
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_10() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                set Pump1 broken true
                set Pump1 lifetime 0
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pump1 broken
                stat Pump1 lifetime
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Plumber1 repair
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump1 broken
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: true
                lifetime: 0
                broken: false
                """;
        assertEquals(expectedOutput, output.toString());
        var lifetime = Integer.parseInt(Prototype.stat("stat Pump1 lifetime".split(" ")).split(" ")[1]);
        assertTrue(1 <= lifetime && lifetime <= 10);
    }

    @Test
    public void test8_4_11() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pump1 broken
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var lifetime = Integer.parseInt(Prototype.stat("stat Pump1 lifetime".split(" ")).split(" ")[1]);
        assertTrue(1 <= lifetime && lifetime <= 10);
        input = """
                playeruse Plumber1 repair
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump1 broken
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: false
                broken: false
                """;
        assertEquals(expectedOutput, output.toString());
        lifetime = Integer.parseInt(Prototype.stat("stat Pump1 lifetime".split(" ")).split(" ")[1]);
        assertTrue(1 <= lifetime && lifetime <= 10);
    }

    @Test
    public void test8_4_12() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new pump Pump2
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new pipe Pipe3 Pump1 Pump2
                new plumber Plumber1 Pump1
                playeruse Plumber1 redirect Pipe1 Pipe2
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pump1 source
                stat Pump1 destination
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Plumber1 redirect Pipe2 Pipe3
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump1 source
                stat Pump1 destination
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                source: Pipe1
                destination: Pipe2
                source: Pipe2
                destination: Pipe3
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_13() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                playeruse Plumber1 grabpipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Plumber1 grabbedPipe
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                grabbedPipe: Pipe1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_14() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                new plumber Plumber2 Pipe1
                playeruse Plumber1 grabpipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Plumber1 grabbedPipe
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                grabbedPipe: null
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_15() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new pump Pump2
                new pump Pump3
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new pipe Pipe3 Pump1 Pump2
                new pipe Pipe4 Pump2 Pump3
                new plumber Plumber1 Pump1
                playeruse Plumber1 grabpipe Pipe1
                move Plumber1 Pipe3
                move Plumber1 Pump2
                move Plumber1 Pipe4
                move Plumber1 Pump3
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Plumber1 grabbedPipe
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Plumber1 placepipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump3 pipes
                stat Plumber1 grabbedPipe
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                grabbedPipe: Pipe1
                pipes: Pipe4 Pipe1
                grabbedPipe: null
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_16() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new pump Pump2
                new pump Pump3
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new pipe Pipe3 Pump1 Pump2
                new pipe Pipe4 Pump2 Pump3
                new plumber Plumber1 Pump3
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Plumber1 grabbedPipe
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Plumber1 placepipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump3 pipes
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                grabbedPipe: null
                pipes: Pipe4
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_17() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new pump Pump2
                new pump Pump3
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new pipe Pipe3 Pump1 Pump2
                new pipe Pipe4 Pump2 Pump3
                new plumber Plumber1 Pump1
                playeruse Plumber1 grabpipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pump1 pipes
                stat Plumber1 grabbedPipe
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Plumber1 Pipe3
                move Plumber1 Pump2
                move Plumber1 Pipe4
                move Plumber1 Pump3
                playeruse Plumber1 placepipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump3 pipes
                stat Plumber1 grabbedPipe
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                pipes: Pipe2 Pipe3
                grabbedPipe: Pipe1
                pipes: Pipe4 Pipe1
                grabbedPipe: null
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_18() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 slippery
                stat Pipe1 slipperyFor
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Saboteur1 makeitslippery
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 slippery
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                slippery: false
                slipperyFor: 0
                slippery: true
                """;
        assertEquals(expectedOutput, output.toString());
        var slipperyFor = Integer.parseInt(Prototype.stat("stat Pipe1 slipperyFor".split(" ")).split(" ")[1]);
        assertTrue(1 <= slipperyFor && slipperyFor <= 5);
    }

    @Test
    public void test8_4_19() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                set Pipe1 slippery true
                set Pipe1 slipperyFor 1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 slippery
                stat Pipe1 slipperyFor
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Saboteur1 makeitslippery
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 slippery
                stat Pipe1 slipperyFor
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                slippery: true
                slipperyFor: 1
                slippery: true
                slipperyFor: 1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_20() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 sticky
                stat Pipe1 stickyFor
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Saboteur1 makeitsticky
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 sticky
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                sticky: false
                stickyFor: 0
                sticky: true
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_21() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pipe1
                set Pipe1 sticky true
                set Pipe1 stickyFor 1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 sticky
                stat Pipe1 stickyFor
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                playeruse Saboteur1 makeitsticky
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 sticky
                stat Pipe1 stickyFor
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                sticky: true
                stickyFor: 1
                sticky: true
                stickyFor: 1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_22() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Cistern1
                playeruse Plumber1 receivepump Pump2
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Plumber1 grabbedPump
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Plumber1 Pipe1
                playeruse Plumber1 placepump Pump2
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump2 pipes
                stat Plumber1 grabbedPump
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                grabbedPump: Pump2
                pipes: Pipe3 Pipe1
                grabbedPump: null
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_23() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new saboteur Saboteur1 Pump1
                new plumber Plumber1 Pump1
                playeruse Plumber1 grabpipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Saboteur1 component
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                move Saboteur1 Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Saboteur1 component
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                component: Pump1
                component: Pump1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_24() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pipe1 broken true
                set Pipe1 waterlevel 1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 1
                waterLevel: 0
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_25() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                new plumber Plumber1 Pump1
                set Pipe1 broken false
                set Pipe1 waterlevel 1
                playeruse Plumber1 grabpipe Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 1
                waterLevel: 0
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_26() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pipe1 broken false
                set Pipe1 waterlevel 1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pipe1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 1
                waterLevel: 1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_27() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pump1 broken false
                set Pump1 lifetime 1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pump1 broken
                stat Pump1 lifetime
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pump1 broken
                stat Pump1 lifetime
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                broken: false
                lifetime: 1
                broken: true
                lifetime: 0
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_28() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pipe1 waterlevel 0
                set Pipe2 waterlevel 0
                set Pump1 waterlevel 1
                set Pump1 broken true
                set Pump1 lifetime 0
                set Pump1 source Pipe1
                set Pump1 destination Pipe2
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                stat Pipe2 waterlevel
                stat Pump1 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                stat Pipe2 waterlevel
                stat Pump1 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 0
                waterLevel: 0
                waterLevel: 1
                waterLevel: 0
                waterLevel: 0
                waterLevel: 0
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_29() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pipe2 waterlevel 0
                set Pump1 waterlevel 1
                set Pump1 broken false
                set Pump1 lifetime 3
                set Pump1 source Pipe1
                set Pump1 destination Pipe2
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe2 waterlevel
                stat Pump1 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe2 waterlevel
                stat Pump1 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 0
                waterLevel: 1
                waterLevel: 1
                waterLevel: 0
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_30() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new spring Spring1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pipe1 waterlevel 1
                set Pump1 waterlevel 0
                set Pump1 broken false
                set Pump1 lifetime 3
                set Pump1 source Pipe1
                set Pump1 destination Pipe2
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                stat Pump1 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Pump1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                stat Pump1 waterlevel
                stat Pipe2 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 1
                waterLevel: 0
                waterLevel: 0
                waterLevel: 0
                waterLevel: 1
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_31() {
        var input = """
                reset
                new cistern Cistern1
                new pump Pump1
                new pipe Pipe1 Cistern1 Pump1
                new pipe Pipe2 Cistern1 Pump1
                set Pipe1 waterlevel 1
                set Pipe2 waterlevel 1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                stat Pipe2 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Cistern1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                stat Pipe2 waterlevel
                stat Cistern1 pipes
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 1
                waterLevel: 1
                waterLevel: 0
                waterLevel: 0
                pipes: Pipe1 Pipe2 Pipe3
                """;
        assertEquals(expectedOutput, output.toString());
    }

    @Test
    public void test8_4_32() {
        var input = """
                reset
                new spring Spring1
                new pump Pump1
                new pipe Pipe1 Spring1 Pump1
                new pipe Pipe2 Spring1 Pump1
                set Pipe1 waterlevel 0
                set Pipe2 waterlevel 0
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        var getOutput = """
                stat Pipe1 waterlevel
                stat Pipe2 waterlevel
                """;
        var output = new StringBuilder();
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        input = """
                step Spring1
                """;
        for (var line : input.split("\n"))
            Prototype.runCommand(line);
        getOutput = """
                stat Pipe1 waterlevel
                stat Pipe2 waterlevel
                """;
        for (var line : getOutput.split("\n"))
            output.append(Prototype.stat(line.split(" "))).append("\n");
        var expectedOutput = """
                waterLevel: 0
                waterLevel: 0
                waterLevel: 1
                waterLevel: 1
                """;
        assertEquals(expectedOutput, output.toString());
        var lifetime = Integer.parseInt(Prototype.stat("stat Pump1 lifetime".split(" ")).split(" ")[1]);
        assertTrue(1 <= lifetime && lifetime <= 10);
    }
}