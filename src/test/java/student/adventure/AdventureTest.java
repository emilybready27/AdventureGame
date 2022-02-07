package student.adventure;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/*
prompt
take valid
take invalid
drop valid
drop invalid
invalid command
exit at ending room
 */

public class AdventureTest {
    private AdventureGame adventureGame;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        adventureGame = new AdventureGame("src/main/resources/westeros.json");
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void testQuitExit() {
        adventureGame.quit();
        assertEquals("Goodbye!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testExamine() {
        adventureGame.examine();
        String expected = "You're at Winterfell." + "\r\n" +
                "From here, you can go: north south east west " + "\r\n" +
                "Items visible: banner weapon weapon";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    // Go
    @Test
    public void testGoValidDirection() {
        adventureGame.go("north");
        assertEquals("castle black", adventureGame.getCurrentRoom().getName());
    }

    @Test
    public void testGoInvalidDirection() {
        adventureGame.go("northeast");
        String expected = "I can't go northeast!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGoUndefinedDirection() {
        adventureGame.go("nowhere");
        String expected = "I can't go nowhere!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGoEmptyDirection() {
        adventureGame.go("");
        String expected = "I can't go !";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGoNullDirection() {
        adventureGame.go(null);
        String expected = "I can't go null!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    // Take
    @Test
    public void testTakeValidItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.take("banner");
        Item item = new Item("banner", "Direwolf sigil of House Stark.");
        assertTrue(item.equals(adventureGame.getInventory().get(0)));
    }

}