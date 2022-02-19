package student.server;

import student.adventure.AdventureGame;
import student.adventure.Direction;
import student.adventure.Item;

import java.util.*;

public class MyAdventureService implements AdventureService {
    private Map<Integer, AdventureGame> games;
    private int currentID;
    private final String path = "src/main/resources/westeros.json";

    public MyAdventureService() {
        this.games = new HashMap<>();
        this.currentID = 0;
    }

    @Override
    public void reset() {
        games = new HashMap<>();
        currentID = 0;
    }

    @Override
    public int newGame() throws AdventureException {
        AdventureGame adventureGame = new AdventureGame(path);
        games.put(currentID, adventureGame);
        return currentID++;
    }

    @Override
    public GameStatus getGame(int id) {
        AdventureGame adventureGame = games.get(id);
        AdventureState adventureState = new AdventureState();
        Map<String, List<String>> commandOptions = getCommandOptions();
        return new GameStatus(false, id, adventureGame.onStartup(), null, null, adventureState, commandOptions);
    }

    private HashMap<String, List<String>> getCommandOptions() {
        HashMap<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("quit", new ArrayList<>());
        commandOptions.put("exit", new ArrayList<>());
        commandOptions.put("examine", new ArrayList<>());
        commandOptions.put("go", new ArrayList<>(Direction.getValidDirections()));
        commandOptions.put("take", new ArrayList<>(Item.getValidItems()));
        commandOptions.put("drop", new ArrayList<>(Item.getValidItems()));
        commandOptions.put("retrace", new ArrayList<>());
        return commandOptions;
    }

    @Override
    public boolean destroyGame(int id) {
        return false;
    }

    @Override
    public void executeCommand(int id, Command command) {
        AdventureGame adventureGame = games.get(id);
        String[] userInput = new String[]{command.getCommandName(), command.getCommandValue()};
        adventureGame.evaluate(userInput);
    }

    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
