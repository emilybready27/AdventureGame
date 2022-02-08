package student.adventure;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import static student.adventure.Direction.isValidDirection;
import static student.adventure.Item.isValidItem;
import static student.adventure.Layout.buildLayout;

/** A class that handles the state and command behaviors of the Adventure Game. */
public class AdventureGame {
    private final Layout layout;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    /**
     * Constructs an AdventureGame by first attempting to build the Layout.
     * @param path String
     * @throws IllegalArgumentException Invalid layout
     */
    public AdventureGame(String path) throws IllegalArgumentException {
        try {
            this.layout = buildLayout(path);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid layout.");
        }
        this.currentRoom = findRoom(layout.getStartingRoom());
        this.inventory = new ArrayList<>();
    }

    /**
     * Gets the Layout of the current game.
     * @return Layout
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Gets the current Room of the game.
     * @return Room
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Gets the current Inventory of Items of the game.
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }


    /**
     * Quits or exits the current game by signaling quit is true.
     * @return boolean
     */
    public boolean quit() {
        System.out.println("Goodbye!");
        return true;
    }

    /**
     * Examines the current state of the game by printing out the name
     * of the current Room, its description, the directions in which to
     * go from this Room, and the Items visible in this Room.
     */
    public void examine() {
        System.out.println(currentRoom.getName());
        System.out.println(currentRoom.getDescription());

        System.out.print("From here, you can go: ");
        for (Direction direction : currentRoom.getDirections()) {
            System.out.print(direction.getDirectionName() + " ");
        }

        System.out.println();
        System.out.print("Items visible: ");
        for (Item item : currentRoom.getItems()) {
            System.out.print(item.getItemName() + " ");
        }
        System.out.println();
    }

    /**
     * Attempts to go in the given Direction, if it is valid, from the current Room.
     * If it isn't a valid Direction, or the Direction isn't possible in this Room,
     * doesn't change the state of the game.
     * If the Direction is valid and leads to the ending Room, terminates the game
     * by signaling quit is true.
     * @param argument String
     * @return boolean
     */
    public boolean go(String argument) {
        if (argument == null || !isValidDirection(argument)) {
            System.out.println("I can't go " + argument + "!");
            return false;
        }

        for (Direction direction : currentRoom.getDirections()) {
            if (argument.equalsIgnoreCase(direction.getDirectionName())) {
                currentRoom = findRoom(direction.getRoom());
                return isEndingRoom();
            }
        }

        System.out.println("I can't go " + argument + "!");
        return false;
    }

    /**
     * Locates the Room object given the String name representing it.
     * @param name String
     * @return Room
     */
    private Room findRoom(String name) {
        for (Room room : layout.getRooms()) {
            if (name.equalsIgnoreCase(room.getName())) {
                return room;
            }
        }
        return null;
    }

    /**
     * Checks if the current Room is equal to the ending Room and
     * signals to terminate the game by returning true.
     * Otherwise, prints the examine information of the new Room.
     * @return boolean
     */
    private boolean isEndingRoom() {
        if (currentRoom.equals(findRoom(layout.getEndingRoom()))) {
            System.out.println("You're at " + layout.getEndingRoom() + "! You win!");
            return true;
        } else {
            examine();
            return false;
        }
    }

    /**
     * Attempts to take the given Item, if it is valid, from the current Room.
     * If it isn't a valid Item, or the Item isn't present in the Room,
     * doesn't change the state of the game.
     * Prompts the user for a selection and takes that particular Item.
     * @param argument String
     */
    public void take(String argument) {
        if (argument == null || !isValidItem(argument)
                || !currentRoom.getItems().contains(findItem(argument, currentRoom.getItems()))) {
            System.out.println("There is no item " + argument + " in the room!");
            return;
        }

        ArrayList<Item> items = promptForItems(argument, currentRoom.getItems());
        int selection = getItemSelection();

        if (selection >= 0 && selection < items.size()) {
            inventory.add(items.get(selection));
            currentRoom.getItems().remove(items.get(selection));
        } else {
            System.out.println("Invalid number: aborting action.");
        }
    }

    /**
     * Attempts to drop the given Item, if it is valid, into the current Room.
     * If it isn't a valid Item, or the item isn't present in the inventory,
     * doesn't change the state of the game.
     * Prompts the user for a selection and drops that particular Item.
     * @param argument String
     */
    public void drop(String argument) {
        if (argument == null || !isValidItem(argument)
                || !inventory.contains(findItem(argument, inventory))) {
            System.out.println("You don't have " + argument + "!");
            return;
        }

        ArrayList<Item> items = promptForItems(argument, inventory);
        int selection = getItemSelection();

        if (selection >= 0 && selection < items.size()) {
            inventory.remove(items.get(selection));
            currentRoom.getItems().add(items.get(selection));
        } else {
            System.out.println("Invalid number: aborting action.");
        }
    }

    /**
     * Locates the Item object given the String name representing it
     * and a specified container to search in.
     * @param name String
     * @param container ArrayList<Item>
     * @return Item
     */
    private Item findItem(String name, ArrayList<Item> container) {
        for (Item item : container) {
            if (name.equalsIgnoreCase(item.getItemName())) {
                return item;
            }
        }
        return null;
    }

    /**
     * Matches all Items with the same given Item name in the given container
     * and prompts the user for a selection based on their descriptions.
     * @param argument String
     * @param container ArrayList<Item>
     * @return ArrayList<Item>
     */
    private ArrayList<Item> promptForItems(String argument, ArrayList<Item> container) {
        System.out.println("Input the number for which " + argument + " to choose.");
        int count = 0;
        ArrayList<Item> items = new ArrayList<>();

        for (Item item : container) {
            if (argument.equalsIgnoreCase(item.getItemName()) ) {
                System.out.println(count + ": " + item.getItemDescription());
                count++;
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Gets user input for the number of the Item to select.
     * @return int
     */
    private int getItemSelection() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1;
        }
    }

    /**
     * Responds to an invalid command with a print statement.
     * @param userInput String[]
     */
    public void invalidCommand(String[] userInput) {
        System.out.println("I don't understand " + userInput[0] + " " + userInput[1] + "!");
    }
}
