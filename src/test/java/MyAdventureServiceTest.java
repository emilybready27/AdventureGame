import org.junit.Before;
import org.junit.Test;
import student.adventure.AdventureGame;
import student.server.*;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class MyAdventureServiceTest {
    private MyAdventureService service;
    private final String PATH = "src/main/resources/westeros.json";

    @Before
    public void setUp() {
        service = new MyAdventureService();
        try {
            service.newGame();
        } catch (Exception e) {
            // nothing
        }
    }

    @Test
    public void testNewGame() {
        AdventureGame expected = new AdventureGame(PATH, false);
        assert(expected.equals(service.getGames().get(0)));
    }

    @Test
    public void testGetCommandOptions() {
        AdventureGame game = service.getGames().get(0);
        HashMap<String, List<String>> actual = service.getCommandOptions(game.getCurrentRoom().getDirections(),
                game.getCurrentRoom().getItems(), game.getInventory());
        List<String> empty = new ArrayList<>(Arrays.asList(""));
        HashMap<String, List<String>> expected = new HashMap<>();
        expected.put("drop", empty);
        expected.put("take", new ArrayList<>(Arrays.asList("banner: Direwolf sigil of House Stark.",
                "weapon: Ice Valyrian steel sword of House Stark.",
                "weapon: Needle sword of House Stark.")));
        expected.put("examine", empty);
        expected.put("retrace", empty);
        expected.put("go", new ArrayList<>(Arrays.asList("north: Castle Black", "south: Moat Cailin",
                "east: Dreadfort", "west: Torrhen's Square")));
        expected.put("quit", empty);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetGame() {
        GameStatus actual = service.getGame(0);
        AdventureGame game = new AdventureGame(PATH, false);
        String examine = "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                "From here, you can go: north south east west \r\n" +
                "Items visible: banner weapon weapon ";
        Map<String, List<String>> commandOptions = service.getCommandOptions(game.getCurrentRoom().getDirections(),
                game.getCurrentRoom().getItems(), game.getInventory());
        GameStatus expected = new GameStatus(false, 0, examine,
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null, new AdventureState(), commandOptions);
        assert(expected.equals(actual));
    }

    @Test
    public void testGetGameRestart() {
        service.reset();
        assert(service.getGames().size() == 0);
    }

    @Test
    public void testExecuteCommand() {

    }

    @Test
    public void testDestroyGame() {

    }

    @Test
    public void testReset() {

    }
}
