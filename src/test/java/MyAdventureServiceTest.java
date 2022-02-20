import org.junit.Before;
import org.junit.Test;
import student.adventure.AdventureGame;
import student.adventure.Item;
import student.server.*;

import java.util.*;

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
    public void testGetGame() {
        GameStatus actual = service.getGame(0);
        AdventureGame game = new AdventureGame(PATH, false);
        String examine = "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                "From here, you can go: north south east west \r\n" +
                "Items visible: banner weapon weapon ";
        Map<String, List<String>> commandOptions = service.getCommandOptions(game);
        GameStatus expected = new GameStatus(false, 0, examine,
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null, new AdventureState(), commandOptions);
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandQuit() {
        service.executeCommand(0,  new Command("quit", ""));
        GameStatus actual = service.getGame(0);
        AdventureGame game = new AdventureGame(PATH, false);
        String examine = "Goodbye!";
        HashMap<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("restart", new ArrayList<>(Arrays.asList("")));
        GameStatus expected = new GameStatus(false, 0, examine,
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null, new AdventureState(), commandOptions);
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandGo() {
        service.executeCommand(0, new Command("go", "south"));
        AdventureGame game = service.getGames().get(0);
        String actual = game.getCurrentRoom().getName();
        String expected = "Moat Cailin";
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandTake() {
        service.executeCommand(0, new Command("take",
                "banner: Direwolf sigil of House Stark."));
        AdventureGame game = service.getGames().get(0);
        Item actual = game.getInventory().get(0);
        Item expected = new Item("banner", "Direwolf sigil of House Stark.");
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandDrop() {
        service.executeCommand(0, new Command("take",
                "banner: Direwolf sigil of House Stark."));
        service.executeCommand(0, new Command("drop",
                "banner: Direwolf sigil of House Stark."));
        AdventureGame game = service.getGames().get(0);
        assert(game.getInventory().size() == 0);
    }

    @Test
    public void testDestroyGame() {
        service.destroyGame(0);
        assert(service.getGames().size() == 0);
    }

    @Test
    public void testReset() {
        service.reset();
        assert(service.getGames().size() == 0);
    }
}
