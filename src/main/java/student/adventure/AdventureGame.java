package student.adventure;

import java.util.ArrayList;
import java.util.Locale;

/** A class that handles the current state of the Adventure Game. */
public class AdventureGame {
    private final Layout layout;
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public AdventureGame(Layout layout) {
        this.layout = layout;
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
        for (Item item : inventory) {
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
}
