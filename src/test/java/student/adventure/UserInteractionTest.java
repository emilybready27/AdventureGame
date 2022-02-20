package student.adventure;

import org.junit.Test;
import student.adventure.UserInteraction;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/** A class that tests the handling of user input from the console. */

public class UserInteractionTest {
    @Test
    public void testGetUserInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("examine".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        assertEquals("examine", userInput);
    }
    
    @Test
    public void testLowerCaseCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("examine".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"examine", ""}, userInputs);
    }

    @Test
    public void testUpperCaseCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("EXAMINE".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"examine", ""}, userInputs);
    }

    @Test
    public void testMixedCaseCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("ExAmInE".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"examine", ""}, userInputs);
    }

    @Test
    public void testExtraSpacesCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("  examine  ".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"examine", ""}, userInputs);
    }

    @Test
    public void testLowerCaseCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("go south".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"go", "south"}, userInputs);
    }

    @Test
    public void testUpperCaseCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("GO SOUTH".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"go", "south"}, userInputs);
    }

    @Test
    public void testMixedCaseCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("gO sOuTh".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"go", "south"}, userInputs);
    }

    @Test
    public void testExtraSpacesCommandAndArgument() {
        ByteArrayInputStream in = new ByteArrayInputStream("  go     south  ".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{"go", "south"}, userInputs);
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyCommand() {
        ByteArrayInputStream in = new ByteArrayInputStream("".getBytes());
        System.setIn(in);
        String userInput = UserInteraction.getUserInput();
        String[] userInputs = UserInteraction.parseUserInput(userInput);
        assertArrayEquals(new String[]{}, userInputs);
    }
}
