package student.adventure;

import com.google.gson.JsonParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import static student.adventure.Layout.parseJson;

/** A class that handles the current state of the Adventure Game. */
public class AdventureGame {
    private final Layout layout;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public AdventureGame(String path) throws IllegalArgumentException {
        try {
            this.layout = parseJson(path);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid.");
        }
        this.currentRoom = findRoom(layout.getStartingRoom());
        this.inventory = new ArrayList<>();
    }

    public Layout getLayout() {
        return layout;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = new ArrayList<>(inventory);
    }

    public ArrayList<Item> getInventory() {
        return new ArrayList<>(inventory);
    }

    public void examine() {
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

    public boolean go(String argument) {
        if (!isValidDirection(argument)) {
            System.out.println("I can't go " + argument + "!");
            return false;
        }
        for (Direction direction : currentRoom.getDirections()) {
            if (argument.equals(direction.getDirectionName())) {
                currentRoom = findRoom(direction.getRoom());
                return checkIfEndingRoom();
            }
        }
        System.out.println("I can't go " + argument + "!");
        return false;
    }

    private Room findRoom(String name) {
        for (Room room : layout.getRooms()) {
            if (name.equals(room.getName())) {
                return room;
            }
        }
        return null;
    }

    private Item findItem(String name, ArrayList<Item> container) {
        for (Item item : container) {
            if (name.equals(item.getItemName())) {
                return item;
            }
        }
        return null;
    }

    private boolean isValidDirection(String argument) {
        return (argument.equals("north") || argument.equals("south") || argument.equals("east")
                || argument.equals("west") || argument.equals("northeast") || argument.equals("northwest")
                || argument.equals("southeast") || argument.equals("southwest"));
    }

    private boolean checkIfEndingRoom() {
        if (currentRoom.equals(findRoom(layout.getEndingRoom()))) {
            System.out.println("You're at " + layout.getEndingRoom() + "! You win!");
            return true;
        }
        return false;
    }

    public void take(String argument) {
        if (!isValidItem(argument)
                || !currentRoom.getItems().contains(findItem(argument, currentRoom.getItems()))) {
            System.out.println("There is no item " + argument + " in the room!");
            return;
        }

        ArrayList<Item> items = matchItems(argument, currentRoom.getItems());
        int input = getItemInput();
        if (input >= 0 && input < items.size()) {
            inventory.add(items.get(input));
            currentRoom.getItems().remove(items.get(input));
        } else {
            System.out.println("Invalid number: aborting action.");
        }
    }

    public void drop(String argument) {
        if (!isValidItem(argument) || !inventory.contains(findItem(argument, inventory))) {
            System.out.println("You don't have " + argument + "!");
            return;
        }

        ArrayList<Item> items = matchItems(argument, inventory);
        int input = getItemInput();
        if (input >= 0 && input < items.size()) {
            inventory.remove(items.get(input));
            currentRoom.getItems().add(items.get(input));
        } else {
            System.out.println("Invalid number: aborting action.");
        }
    }

    private ArrayList<Item> matchItems(String argument, ArrayList<Item> container) {
        System.out.println("Input the number for which " + argument + " to choose.");
        int count = 0;
        ArrayList<Item> items = new ArrayList<>();
        for (Item item : container) {
            if (argument.equals(item.getItemName()) ) {
                System.out.println(count + ": " + item.getItemDescription());
                count++;
                items.add(item);
            }
        }
        return items;
    }

    private int getItemInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1;
        }
    }

    private boolean isValidItem(String argument) {
        return (argument.equals("banner") || argument.equals("weapon") || argument.equals("tool")
                || argument.equals("crown"));
    }
}
