import org.junit.Before;
import org.junit.Test;
import student.adventure.AdventureGame;

import java.io.ByteArrayInputStream;

public class UserInputTest {
    private AdventureGame adventureGame;

    @Before
    public void setUp() {
        adventureGame = new AdventureGame("src/main/resources/westeros.json");
    }

//    @Test
//    public void testLowercase() {
//        ByteArrayInputStream in = new ByteArrayInputStream("go south".getBytes());
//        System.setIn(in);
//        Main.getUserInput()
//    }

    @Test
    public void testUppercase() {

    }

    @Test
    public void testMixedCase() {

    }

    @Test
    public void test() {

    }
}
