package student.server;

import student.adventure.AdventureGame;
import student.adventure.Direction;
import student.adventure.Item;
import student.adventure.Room;

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
        games.clear();
        currentID = 0;
    }

    @Override
    public int newGame() throws AdventureException {
        AdventureGame adventureGame = new AdventureGame(path, false);
        games.put(currentID, adventureGame);
        return currentID++;
    }

    @Override
    public GameStatus getGame(int id) {
        AdventureGame adventureGame = games.get(id);
        AdventureState adventureState = new AdventureState();
        ArrayList<Direction> roomDirections = adventureGame.getCurrentRoom().getDirections();
        ArrayList<Item> roomItems = adventureGame.getCurrentRoom().getItems();
        ArrayList<Item> inventory = adventureGame.getInventory();
        Map<String, List<String>> commandOptions = getCommandOptions(roomDirections, roomItems, inventory);
        if (adventureGame.hasQuit()) {
            HashMap<String, List<String>> restartCommand = new HashMap<>();
            restartCommand.put("restart", new ArrayList<>(Arrays.asList("")));
            return new GameStatus(false, id, adventureGame.getMessage(), null, null, adventureState, restartCommand);
        }
        return new GameStatus(false, id, adventureGame.getMessage(), null, null, adventureState, commandOptions);
    }

    private HashMap<String, List<String>> getCommandOptions(ArrayList<Direction> roomDirections,
                                                            ArrayList<Item> roomItems, ArrayList<Item> inventory) {
        HashMap<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("quit", new ArrayList<>(Arrays.asList("")));
        commandOptions.put("examine", new ArrayList<>(Arrays.asList("")));
        commandOptions.put("retrace", new ArrayList<>(Arrays.asList("")));

        ArrayList<String> directionOptions = new ArrayList<>();
        for (Direction direction : roomDirections) {
            String directionOption = direction.getDirectionName() + ": " + direction.getRoom();
            directionOptions.add(directionOption);
        }
        commandOptions.put("go", new ArrayList<>(directionOptions));

        ArrayList<String> itemOptions = new ArrayList<>();
        for (Item item : roomItems) {
            String itemOption = item.getItemName() + ": " + item.getItemDescription();
            itemOptions.add(itemOption);
        }
        commandOptions.put("take", new ArrayList<>(itemOptions));

        itemOptions = new ArrayList<>();
        for (Item item : inventory) {
            String itemOption = item.getItemName() + ": " + item.getItemDescription();
            itemOptions.add(itemOption);
        }
        commandOptions.put("drop", new ArrayList<>(itemOptions));

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
//        if (adventureGame.hasQuit()) {
//            destroyGame(id);
//        }
    }

    @Override
    public SortedMap<String, Integer> fetchLeaderboard() {
        return null;
    }
}
