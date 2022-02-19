package student.adventure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** A class that tests the behavior of the AdventureGame. */

// Tests with byte array input streams inspired by
// https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input/6416179#6416179
// Tests using output stream captor inspired by
// https://www.baeldung.com/java-testing-system-out-println
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

    // Go
    @Test
    public void testGoValidDirection() {
        adventureGame.go("north");
        assertEquals("Castle Black", adventureGame.getCurrentRoom().getName());
    }

    @Test
    public void testGoInvalidDirection() {
        String actual = adventureGame.go("Northeast");
        String expected = "I can't go Northeast!";
        assertEquals(expected, actual);
    }

    @Test
    public void testGoUndefinedDirection() {
        String actual = adventureGame.go("");
        String expected = "I can't go !";
        assertEquals(expected, actual);
    }

    @Test
    public void testGoNullDirection() {
        String actual = adventureGame.go(null);
        String expected = "I can't go null!";
        assertEquals(expected, actual);
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

    @Test
    public void testTakeInvalidItem() {
        String actual = adventureGame.take("tool");
        String expected = "There is no item tool in the room!";
        assertEquals(expected, actual);
    }

    @Test
    public void testTakeUndefinedItem() {
        String actual = adventureGame.take("");
        String expected = "There is no item  in the room!";
        assertEquals(expected, actual);
    }

    @Test
    public void testTakeNullItem() {
        String actual = adventureGame.take(null);
        String expected = "There is no item null in the room!";
        assertEquals(expected, actual);
    }

    @Test
    public void testTakeInvalidItemSelection() {
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        String actual = adventureGame.take("banner");
        String expected = "Invalid number: aborting action.";
        assertEquals(expected, actual);
    }

    // Drop
    @Test
    public void testDropValidItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.take("banner");
        in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.drop("banner");
        Item item = new Item("banner", "Direwolf sigil of House Stark.");
        assertTrue(adventureGame.getInventory().isEmpty());
    }

    @Test
    public void testDropInvalidItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        String actual = adventureGame.drop("tool");
        String expected = "You don't have tool!";
        assertEquals(expected, actual);
    }

    @Test
    public void testDropUndefinedItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        String actual = adventureGame.drop("");
        String expected = "You don't have !";
        assertEquals(expected, actual);
    }

    @Test
    public void testDropNullItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        String actual = adventureGame.drop(null);
        String expected = "You don't have null!";
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidItemSelection() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.take("banner");
        in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        String actual = adventureGame.drop("banner");
        String expected = "Invalid number: aborting action.";
        assertEquals(expected, actual);
    }

    // Other
    @Test
    public void testQuitExit() {
        String actual = adventureGame.quit("");
        String expected = "Goodbye!";
        assertEquals(expected, actual);
    }

    @Test
    public void testExamine() {
        String actual = adventureGame.examine("");
        String expected = "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                "From here, you can go: north south east west \r\n" +
                "Items visible: banner weapon weapon ";
        assertEquals(expected, actual);
    }

    @Test
    public void testRetrace() {
        adventureGame.go("south");
        adventureGame.go("south");
        String actual = adventureGame.retrace("");
        String expected = "Path to Twins\r\n" + "Winterfell -> Moat Cailin -> Twins";
        assertEquals(expected, actual);
    }

    @Test
    public void testQuitAtEndingRoom() {
        adventureGame.getLayout().setEndingRoom("castle black");
        String actual = adventureGame.go("north");
        String expected = "You're at castle black! You win!";
        assertEquals(expected, actual);
    }

    @Test
    public void testInvalidCommand() {
        String actual = adventureGame.invalidCommand(new String[]{"Winter's", "coming"});
        String expected = "I don't understand Winter's coming!";
        assertEquals(expected, actual);
    }
}
