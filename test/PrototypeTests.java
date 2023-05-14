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
        Prototype.runCommand("new cistern C");
        Prototype.runCommand("new pump P1");
        Prototype.runCommand("new pump P2");
        Prototype.runCommand("new pump P3");
        Prototype.runCommand("new spring F1");
        Prototype.runCommand("new spring F2");
        Prototype.runCommand("new pipe p1 P1 P2");
        Prototype.runCommand("new pipe p2 P2 P3");
        Prototype.runCommand("new pipe p3 P1 P3");
        Prototype.runCommand("new pipe p4 C P1");
        Prototype.runCommand("new pipe p5 P3 F1");
        Prototype.runCommand("new pipe p6 P2 F2");
        Prototype.runCommand("new saboteur S C");
        Prototype.runCommand("new plumber M P3");

        Prototype.runCommand("move S p4");
        Prototype.runCommand("move S P1");
        Prototype.runCommand("move S p1");
        Prototype.runCommand("move S P2");
        Prototype.runCommand("move S p6");
        Prototype.runCommand("playeruse S makeitsticky");
        Prototype.runCommand("move S P2");
        Prototype.runCommand("move S p1");
        Prototype.runCommand("playeruse S leak");
        Prototype.runCommand("move S P2");
        Prototype.runCommand("set P1 broken true");
        Prototype.runCommand("move M p3");
        Prototype.runCommand("move M P1");
        Prototype.runCommand("playeruse M repair");
        Prototype.runCommand("move M p1");
        Prototype.runCommand("playeruse M repair");

        assertEquals("component: P2", Prototype.stat("stat S component".split(" ")));
        assertEquals("component: p1", Prototype.stat("stat M component".split(" ")));
        assertEquals("sticky: true", Prototype.stat("stat p6 sticky".split(" ")));
        assertEquals("broken: false", Prototype.stat("stat p1 broken".split(" ")));
        assertEquals("broken: false", Prototype.stat("stat P1 broken".split(" ")));
    }
}