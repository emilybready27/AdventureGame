package student.server;

import student.adventure.AdventureGame;
import student.adventure.Direction;
import student.adventure.Item;

import java.util.*;

/** A class that services my AdventureGame implementation. */

public class MyAdventureService implements AdventureService {
    private Map<Integer, AdventureGame> games;
    private int currentID;
    private final String PATH = "src/main/resources/westeros.json";

    /**
     * Stores a HashMap of Integer:AdventureGame pairs and current ID number.
     */
    public MyAdventureService() {
        this.games = new HashMap<>();
        this.currentID = 0;
    }

    /**
     * Returns a copy of the Map of AdventureGames.
     * @return Map<Integer, AdventureGame>
     */
    public Map<Integer, AdventureGame> getGames() {
        return new HashMap<>(games);
    }

    /**
     * Clears the Map of stored AdventureGames and resets current ID back to 0.
     */
    @Override
    public void reset() {
        games.clear();
        currentID = 0;
    }

    /**
     * Creates a new AdventureGame and corresponding ID.
     * @return int
     * @throws AdventureException
     */
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

    /**
     * Retrieves the AdventureGame with the given ID by building a GameStatus for it.
     * In the case that the AdventureGame has quit, the only commandOption is to restart.
     * @param id int
     * @return GameStatus
     */
    @Override
    public GameStatus getGame(int id) {
        AdventureGame game = games.get(id);

        if (game.hasQuit()) { // only display restart command option
            HashMap<String, List<String>> restartCommand = new HashMap<>();
            restartCommand.put("restart", new ArrayList<>(Arrays.asList("")));
            return new GameStatus(false, id, game.getMessage(), game.getCurrentRoom().getImage(),
                    null, new AdventureState(), restartCommand);
        } else { // display all other command options
            Map<String, List<String>> commandOptions = getCommandOptions(game);
            return new GameStatus(false, id, game.getMessage(), game.getCurrentRoom().getImage(),
                    null, new AdventureState(), commandOptions);
        }
    }

    /**
     * Composes a list of the available commands and their corresponding arguments,
     * given that the game in question has not already quit.
     * @param game AdventureGame
     * @return HashMap<String, List<String>>
     */
    public HashMap<String, List<String>> getCommandOptions(AdventureGame game) {
        HashMap<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("quit", new ArrayList<>(Arrays.asList("")));
        commandOptions.put("examine", new ArrayList<>(Arrays.asList("")));
        commandOptions.put("retrace", new ArrayList<>(Arrays.asList("")));

        // go, take, drop command options vary by current game state
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

    /**
     * Removes the AdventureGame with the given ID from the Map of AdventureGames,
     * if it is found in the Map.
     * @param id int
     * @return boolean
     */
    @Override
    public boolean destroyGame(int id) {
        if (games.containsKey(id)) {
            games.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Executes the specified command on the AdventureGame referenced by the given ID.
     * @param id int
     * @param command Command
     */
    @Override
    public void executeCommand(int id, Command command) {
        AdventureGame adventureGame = games.get(id);
        String[] userInput = new String[]{command.getCommandName(), command.getCommandValue()};
        adventureGame.evaluate(userInput);
    }

    /**
     * Returns a sorted leaderboard of player "high" scores.
     * @return SortedMap<String, Integer>
     */
    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
