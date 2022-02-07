package student.adventure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// Tests with input streams inspired by
// https://stackoverflow.com/questions/6415728/junit-testing-with-simulated-user-input/6416179#6416179
// Tests with asserts for standard out inspired by
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
        adventureGame.go("Northeast");
        String expected = "I can't go Northeast!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testGoUndefinedDirection() {
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

    @Test
    public void testTakeInvalidItem() {
        adventureGame.take("tool");
        String expected = "There is no item tool in the room!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testTakeUndefinedItem() {
        adventureGame.take("");
        String expected = "There is no item  in the room!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testTakeNullItem() {
        adventureGame.take(null);
        String expected = "There is no item null in the room!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testTakeInvalidItemSelection() {
        ByteArrayInputStream in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        adventureGame.take("banner");
        String expected = "Input the number for which banner to choose.\r\n" +
                "0: Direwolf sigil of House Stark.\r\n" +
                "> Invalid number: aborting action.";
        assertEquals(expected, outputStreamCaptor.toString().trim());
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
        adventureGame.drop("tool");
        String expected = "You don't have tool!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testDropUndefinedItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.drop("");
        String expected = "You don't have !";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testDropNullItem() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.drop(null);
        String expected = "You don't have null!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testInvalidItemSelection() {
        ByteArrayInputStream in = new ByteArrayInputStream("0".getBytes());
        System.setIn(in);
        adventureGame.take("banner");
        in = new ByteArrayInputStream("5".getBytes());
        System.setIn(in);
        adventureGame.drop("banner");
        String expected = "Input the number for which banner to choose.\r\n" +
                "0: Direwolf sigil of House Stark.\r\n" +
                "> Input the number for which banner to choose.\r\n" +
                "0: Direwolf sigil of House Stark.\r\n" +
                "> Invalid number: aborting action.";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    // Other
    @Test
    public void testQuitExit() {
        adventureGame.quit();
        assertEquals("Goodbye!", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testExamine() {
        adventureGame.examine();
        String expected = "You're at Winterfell.\r\n" +
                "From here, you can go: north south east west \r\n" +
                "Items visible: banner weapon weapon";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testQuitAtEndingRoom() {
        adventureGame.getLayout().setEndingRoom("castle black");
        adventureGame.go("north");
        String expected = "You're at castle black! You win!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }

    @Test
    public void testInvalidCommand() {
        adventureGame.invalidCommand(new String[]{"Winter's", "coming"});
        String expected = "I don't understand Winter's coming!";
        assertEquals(expected, outputStreamCaptor.toString().trim());
    }
}