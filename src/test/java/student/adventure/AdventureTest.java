package student.adventure;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AdventureTest {
    private AdventureGame adventureGame;

    @Before
    public void setUp() throws IOException {
        adventureGame = new AdventureGame("src/main/resources/westeros.json");
    }

    @Test
    public void sanityCheck() throws IOException {
        adventureGame.getLayout().printLayout();
    }
}