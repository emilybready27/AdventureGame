/*
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

* A class that tests the handling of user input from the console.

public class UserInputTest {
    @Test
    public void testLowerCaseCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("examine".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"examine"}, userInput);
    }

    @Test
    public void testUpperCaseCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("EXAMINE".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"examine"}, userInput);
    }

    @Test
    public void testMixedCaseCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("ExAmInE".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"examine"}, userInput);
    }

    @Test
    public void testExtraSpacesCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("  examine  ".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"examine"}, userInput);
    }

    @Test
    public void testLowerCaseCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("go south".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"go", "south"}, userInput);
    }

    @Test
    public void testUpperCaseCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("GO SOUTH".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"go", "south"}, userInput);
    }

    @Test
    public void testMixedCaseCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("gO sOuTh".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"go", "south"}, userInput);
    }

    @Test
    public void testExtraSpacesCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("  go     south  ".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{"go", "south"}, userInput);
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        System.setIn(in);
        String[] userInput = Main.getUserInput();
        assertArrayEquals(new String[]{}, userInput);
    }
}
*/
