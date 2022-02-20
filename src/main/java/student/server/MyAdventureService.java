package student.server;

import student.adventure.AdventureGame;
import student.adventure.Direction;
import student.adventure.Item;

import java.util.*;

public class MyAdventureService implements AdventureService {
    private Map<Integer, AdventureGame> games;
    private int currentID;
    private final String PATH = "src/main/resources/westeros.json";

    public MyAdventureService() {
        this.games = new HashMap<>();
        this.currentID = 0;
    }

    public Map<Integer, AdventureGame> getGames() {
        return games;
    }

    @Override
    public void reset() {
        games.clear();
        currentID = 0;
    }

    @Override
    public int newGame() throws AdventureException {
        try {
            AdventureGame adventureGame = new AdventureGame(PATH, false);
            games.put(currentID, adventureGame);
        } catch (Exception e) {
            throw new AdventureException("Error building AdventureGame.");
        }
        return currentID++;
    }

    @Override
    public GameStatus getGame(int id) {
        AdventureGame game = games.get(id);

        if (game.hasQuit()) {
            HashMap<String, List<String>> restartCommand = new HashMap<>();
            restartCommand.put("restart", new ArrayList<>(Arrays.asList("")));
            return new GameStatus(false, id, game.getMessage(), game.getCurrentRoom().getImage(),
                    null, new AdventureState(), restartCommand);
        } else {
            Map<String, List<String>> commandOptions = getCommandOptions(game);
            return new GameStatus(false, id, game.getMessage(), game.getCurrentRoom().getImage(),
                    null, new AdventureState(), commandOptions);
        }
    }

    public HashMap<String, List<String>> getCommandOptions(AdventureGame game) {
        HashMap<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("quit", new ArrayList<>(Arrays.asList("")));
        commandOptions.put("examine", new ArrayList<>(Arrays.asList("")));
        commandOptions.put("retrace", new ArrayList<>(Arrays.asList("")));

        ArrayList<String> options = new ArrayList<>();
        for (Direction direction : game.getCurrentRoom().getDirections()) {
            options.add(direction.getDirectionName() + ": " + direction.getRoom());
        }
        commandOptions.put("go", new ArrayList<>(options));

        options.clear();
        for (Item item : game.getCurrentRoom().getItems()) {
            options.add(item.getItemName() + ": " + item.getItemDescription());
        }
        commandOptions.put("take", new ArrayList<>(options));

        options.clear();
        for (Item item : game.getInventory()) {
            options.add(item.getItemName() + ": " + item.getItemDescription());
        }
        commandOptions.put("drop", new ArrayList<>(options));

        return commandOptions;
    }

    @Override
    public boolean destroyGame(int id) {
        if (games.containsKey(id)) {
            games.remove(id);
            return true;
        }
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
