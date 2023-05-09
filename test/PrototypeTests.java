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
        assertTrue(true);
    }
}