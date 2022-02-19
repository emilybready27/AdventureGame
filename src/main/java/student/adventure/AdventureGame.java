package student.adventure;

import java.util.*;

import static student.adventure.Direction.isValidDirection;
import static student.adventure.Item.isValidItem;

/** A class that handles the state and command behaviors of the Adventure Game. */
public class AdventureGame {
    private final Layout layout;
    private Room currentRoom;
    private ArrayList<Item> inventory;
    private ArrayList<Room> roomPath;
    private boolean hasQuit;
    private boolean usesConsole;
    private String message;

    public AdventureGame(String path) throws IllegalArgumentException {
        this(path, true);
    }

    /**
     * Constructs an AdventureGame by first attempting to build the Layout.
     * @param path String
     * @throws IllegalArgumentException Invalid layout
     */
    public AdventureGame(String path, boolean usesConsole) throws IllegalArgumentException {
        try {
            this.layout = new Layout(path);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid layout.");
        }

        this.currentRoom = layout.getRoomByName(layout.getStartingRoom());
        this.inventory = new ArrayList<>();
        this.roomPath = new ArrayList<>();
        this.roomPath.add(this.currentRoom);
        this.hasQuit = false;
        this.usesConsole = usesConsole;
        this.message = examine("");
    }

    public Layout getLayout() {
        return layout;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public ArrayList<Room> getRoomPath() {
        return new ArrayList<>(roomPath);
    }

    public boolean hasQuit() {
        return hasQuit;
    }

    /**
     * Returns a message to print on startup of AdventureGame.
     * @return String message
     */
    public String getMessage() {
        return message;
    }

    public String getNewMessage(String newMessage) {
        message = newMessage;
        return message;
    }

    /**
     * Invokes the game behavior that corresponds to the given user input.
     * @param userInput String[]
     * @return boolean for quit
     */
    public String evaluate(String[] userInput) {
        switch (userInput[0]) {
            case "quit":
            case "exit":
                if (!userInput[1].equals("")) {
                    return invalidCommand(userInput);
                }
                return quit(userInput[1]);
            case "examine":
                if (!userInput[1].equals("")) {
                    return invalidCommand(userInput);
                }
                return examine(userInput[1]);
            case "go":
                return go(userInput[1]);
            case "take":
                return take(userInput[1]);
            case "drop":
                return drop(userInput[1]);
            case "retrace":
                if (!userInput[1].equals("")) {
                    return invalidCommand(userInput);
                }
                return retrace(userInput[1]);
            case "restart":
                if (!userInput[1].equals("")) {
                    return invalidCommand(userInput);
                }
                return restart(userInput[1]);
            default:
                return invalidCommand(userInput);
        }
    }

    /**
     * Quits or exits the current game by signaling quit is true.
     * @param argument String
     * @return String message
     */
    public String quit(String argument) {
        hasQuit = true;
        return getNewMessage("Goodbye!");
    }

    /**
     * Examines the current state of the game by printing out the name
     * of the current Room, its description, the directions in which to
     * go from this Room, and the Items visible in this Room.
     * @param argument String
     * @return String message
     */
    public String examine(String argument) {
        String message = "";
        message += currentRoom.getName() + "\r\n";
        message += currentRoom.getDescription() + "\r\n";

        message += "From here, you can go: ";
        for (Direction direction : currentRoom.getDirections()) {
            message = message + direction.getDirectionName() + " ";
        }

        message += "\r\n";
        message += "Items visible: ";
        for (Item item : currentRoom.getItems()) {
            message += item.getItemName() + " ";
        }

        return getNewMessage(message);
    }

    /**
     * Attempts to go in the given Direction, if it is valid, from the current Room.
     * If it isn't a valid Direction, or the Direction isn't possible in this Room,
     * sends "undefined" message.
     * If the Direction is valid and leads to the ending Room, sends "win" message.
     * @param argument String
     * @return String message
     */
    public String go(String argument) {
        if (argument == null) {
            return "I can't go " + argument + "!";
        }

        if (usesConsole) { // text-based doesn't have direction rooms visible
            return goUsesConsole(argument);
        }

        // web-based has direction rooms visible
        String[] arguments = argument.split(": ");
        for (Direction direction : currentRoom.getDirections()) {
            if (arguments[0].equals(direction.getDirectionName())) {
                currentRoom = layout.getRoomByName(direction.getRoom());
                roomPath.add(currentRoom);
                if (isEndingRoom()) {
                    hasQuit = true;
                    return getNewMessage("You're at " + layout.getEndingRoom() + "! You win!");
                } else {
                    return examine("");
                }
            }
        }

        return "I can't go " + argument + "!";
    }

    private String goUsesConsole(String argument) {
        if (!isValidDirection(argument)) {
            return "I can't go " + argument + "!";
        }

        for (Direction direction : currentRoom.getDirections()) {
            if (argument.equals(direction.getDirectionName())) {
                currentRoom = layout.getRoomByName(direction.getRoom());
                roomPath.add(currentRoom);
                if (isEndingRoom()) {
                    hasQuit = true;
                    return getNewMessage("You're at " + layout.getEndingRoom() + "! You win!");
                } else {
                    return examine("");
                }
            }
        }
        return "I can't go " + argument + "!";
    }

    /**
     * Checks if the current Room is equal to the ending Room.
     * @return boolean
     */
    private boolean isEndingRoom() {
        return currentRoom.equals(layout.getRoomByName(layout.getEndingRoom()));
    }

    /**
     * Attempts to take the given Item, if it is valid, from the current Room.
     * If it isn't a valid Item, or the Item isn't present in the Room,
     * sends "undefined" message.
     * Uses UserInteraction to get selection for item.
     * @param argument String
     * @return String message
     */
    public String take(String argument) {
        if (argument == null) {
            return "There is no item " + argument + " in the room!";
        }

        if (usesConsole) { // text-based requires additional prompt for user input
            return takeUsesConsole(argument);
        }

        // web-based makes selection directly by descriptions being visible
        String[] arguments = argument.split(": ");
        Item item = findItemByDescription(arguments, currentRoom.getItems());
        if (item == null) {
            return "There is no item " + argument + " in the room!";
        } else {
            inventory.add(item);
            currentRoom.getItems().remove(item);
            return "";
        }
    }

    private String takeUsesConsole(String argument) {
        if (!isValidItem(argument)
                || !currentRoom.getItems().contains(findItemByName(argument, currentRoom.getItems()))) {
            return "There is no item " + argument + " in the room!";
        }

        ArrayList<Item> items = promptForItems(argument, currentRoom.getItems());
        int selection = getItemSelection();

        if (selection >= 0 && selection < items.size()) {
            inventory.add(items.get(selection));
            currentRoom.getItems().remove(items.get(selection));
            return "";
        } else {
            return "Invalid number: aborting action.";
        }
    }

    /**
     * Attempts to drop the given Item, if it is valid, into the current Room.
     * If it isn't a valid Item, or the item isn't present in the inventory,
     * doesn't change the state of the game.
     * Uses UserInteraction to get selection for item.
     * @param argument String
     * @return String message
     */
    public String drop(String argument) {
        if (argument == null) {
            return "You don't have " + argument + "!";
        }

        if (usesConsole) { // text-based requires additional prompt for user input
            return dropUsesConsole(argument);
        }

        // web-based makes selection directly by descriptions being visible
        String[] arguments = argument.split(": ");
        Item item = findItemByDescription(arguments, inventory);
        if (item == null) {
            return "You don't have " + argument + "!";
        } else {
            inventory.remove(item);
            currentRoom.getItems().add(item);
            return "";
        }
    }

    private String dropUsesConsole(String argument) {
        if (!isValidItem(argument) || !inventory.contains(findItemByName(argument, inventory))) {
            return "You don't have " + argument + "!";
        }

        ArrayList<Item> items = promptForItems(argument, inventory);
        int selection = getItemSelection();

        if (selection >= 0 && selection < items.size()) {
            inventory.remove(items.get(selection));
            currentRoom.getItems().add(items.get(selection));
            return "";
        } else {
            return "Invalid number: aborting action.";
        }
    }

    /**
     * Locates the Item object given the String name representing it
     * and a specified container to search in.
     * @param name String
     * @param container ArrayList<Item>
     * @return Item
     */
    private Item findItemByName(String name, ArrayList<Item> container) {
        for (Item item : container) {
            if (name.equals(item.getItemName())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Locates the Item object given the String name and description representing it
     * and a specified container to search in.
     * @param itemInformation String[]
     * @param container ArrayList<Item>
     * @return Item
     */
    private Item findItemByDescription(String[] itemInformation, ArrayList<Item> container) {
        for (Item item : container) {
            if (itemInformation[0].equals(item.getItemName())
                    && itemInformation[1].equals(item.getItemDescription())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Matches all Items with the same given Item name in the given container
     * and uses UserInteraction to prompt user for selection based on item descriptions.
     * @param argument String
     * @param container ArrayList<Item>
     * @return ArrayList<Item>
     */
    private ArrayList<Item> promptForItems(String argument, ArrayList<Item> container) {
        String message = "Input the number for which " + argument + " to choose.\r\n";
        int count = 0;
        ArrayList<Item> items = new ArrayList<>();

        for (Item item : container) {
            if (argument.equals(item.getItemName()) ) {
                message += count + ": " + item.getItemDescription();
                count++;
                items.add(item);
            }
        }
        UserInteraction.printMessage(message);
        return items;
    }

    /**
     * Uses UserInteraction to get item selection number.
     * @return int
     */
    private int getItemSelection() {
        try {
            String userInput = UserInteraction.getUserInput();
            return Integer.parseInt(userInput);
        } catch (InputMismatchException | IllegalArgumentException e) {
            return -1;
        }
    }

    /**
     * Retraces path of user from startingRoom to currentRoom.
     * @param argument String
     * @return String message
     */
    public String retrace(String argument) {
        String message = "Path to " + currentRoom.getName() + "\r\n";
        for (Room room : roomPath) {
            message += room.getName() + " -> ";
        }

        // remove dangling arrow
        return getNewMessage((String) message.subSequence(0, message.length() - 4));
    }

    public String restart(String argument) {
        currentRoom = layout.getRoomByName(layout.getStartingRoom());
        inventory = new ArrayList<>();
        roomPath = new ArrayList<>();
        roomPath.add(this.currentRoom);
        hasQuit = false;
        message = examine("");
        return message;
    }

    /**
     * Responds to an invalid command with an "undefined" message.
     * @param userInput String
     * @return String message
     */
    public String invalidCommand(String[] userInput) {
        String message = "";
        message += "I don't understand";
        for (int i = 0; i < userInput.length; i++) {
            message += " " + userInput[i];
        }
        return message + "!";
    }
}
