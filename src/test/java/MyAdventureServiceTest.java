import org.junit.Before;
import org.junit.Test;
import student.adventure.AdventureGame;
import student.adventure.Item;
import student.server.*;

import javax.ws.rs.core.Response;
import java.util.*;

import static javax.swing.UIManager.put;
import static org.junit.Assert.assertEquals;

/** A class that tests the functions of MyAdventureService. */

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
        GameStatus expected = new GameStatus(
                false,
                0,
                "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                        "From here, you can go: north south east west \r\n" +
                        "Items visible: banner weapon weapon ",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                service.getCommandOptions(new AdventureGame(PATH, false))
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandQuit() {
        service.executeCommand(0,  new Command("quit", ""));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Goodbye!",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                new HashMap<String, List<String>>() {{put("restart", new ArrayList<>(Arrays.asList("")));}}
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandExamine() {
        service.executeCommand(0, new Command("examine", ""));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                        "From here, you can go: north south east west \r\n" +
                        "Items visible: banner weapon weapon ",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                service.getCommandOptions(new AdventureGame(PATH, false))
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandGo() {
        service.executeCommand(0, new Command("go", "north"));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Castle Black\r\n" + "You're at Castle Black.\r\n" +
                        "From here, you can go: north south \r\n" +
                        "Items visible: weapon weapon ",
                "https://static0.srcdn.com/wordpress/wp-content/uploads/2020/07/Castle-Black.jpeg?q=50&fit=crop&w=960&h=500&dpr=1.5",
                null,
                new AdventureState(),
                service.getCommandOptions(service.getGames().get(0))
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandTake() {
        service.executeCommand(0, new Command("take",
                "banner: Direwolf sigil of House Stark."));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                        "From here, you can go: north south east west \r\n" +
                        "Items visible: banner weapon weapon ",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                service.getCommandOptions(service.getGames().get(0))
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandDrop() {
        service.executeCommand(0, new Command("take",
                "banner: Direwolf sigil of House Stark."));
        service.executeCommand(0, new Command("drop",
                "banner: Direwolf sigil of House Stark."));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                        "From here, you can go: north south east west \r\n" +
                        "Items visible: banner weapon weapon ",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                service.getCommandOptions(service.getGames().get(0))
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandRetrace() {
        service.executeCommand(0, new Command("retrace", ""));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Path to Winterfell\r\n" + "Winterfell",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                service.getCommandOptions(new AdventureGame(PATH, false))
        );
        assert(expected.equals(actual));
    }

    @Test
    public void testExecuteCommandRestart() {
        service.executeCommand(0, new Command("quit", ""));
        service.executeCommand(0, new Command("restart", ""));
        GameStatus actual = service.getGame(0);
        GameStatus expected = new GameStatus(
                false,
                0,
                "Winterfell\r\n" + "You're at Winterfell.\r\n" +
                        "From here, you can go: north south east west \r\n" +
                        "Items visible: banner weapon weapon ",
                "https://watchersonthewall.com/wp-content/uploads/2017/11/Winterfell-white-raven.jpg",
                null,
                new AdventureState(),
                service.getCommandOptions(new AdventureGame(PATH, false))
        );
        assert(expected.equals(actual));
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

    @Test
    public void testMultipleGamesIndependent() {
        try {
            service.newGame();
        } catch (Exception e) {
            // nothing
        }
        service.executeCommand(0, new Command("go", "south"));
        service.executeCommand(1, new Command("go", "west"));
        AdventureGame game = service.getGames().get(0);
        String actual = game.getCurrentRoom().getName();
        String expected = "Moat Cailin";
        assert(expected.equals(actual));
    }

    @Test
    public void testMultipleGamesOneQuit() {
        try {
            service.newGame();
        } catch (Exception e) {
            // nothing
        }
        service.executeCommand(0, new Command("quit", ""));
        service.executeCommand(1, new Command("go", "south"));
        AdventureGame game = service.getGames().get(1);
        String actual = game.getCurrentRoom().getName();
        String expected = "Moat Cailin";
        assert(expected.equals(actual));
    }

    @Test
    public void testMultipleGamesOneDestroy() {
        try {
            service.newGame();
        } catch (Exception e) {
            // nothing
        }
        service.destroyGame(0);
        service.executeCommand(1, new Command("go", "south"));
        AdventureGame game = service.getGames().get(1);
        String actual = game.getCurrentRoom().getName();
        String expected = "Moat Cailin";
        assert (expected.equals(actual));
    }
}
