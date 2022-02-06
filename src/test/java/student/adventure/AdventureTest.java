package student.adventure;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/*
prompt
quit
exit
examine
go valid
go invalid
take valid
take invalid
drop valid
drop invalid
invalid command
 */

public class AdventureTest {
    private AdventureGame adventureGame;

    @Before
    public void setUp() throws IOException {
        adventureGame = new AdventureGame("src/main/resources/westeros.json");
    }


}