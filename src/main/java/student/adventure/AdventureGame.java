package student.adventure;

import java.util.Locale;

/** A class that handles the current state of the Adventure Game. */
public class AdventureGame {
    private final Layout layout;
    private Room currentRoom;

    public AdventureGame(Layout layout) {
        this.layout = layout;
        this.currentRoom = findRoom(layout.getStartingRoom());
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Layout getLayout() {
        return layout;
    }

    public void examine() {
        System.out.println(currentRoom.getDescription());
        System.out.print("From here, you can go: ");
        for (Direction direction : currentRoom.getDirections()) {
            System.out.print(direction.getDirectionName() + " ");
        }
        System.out.println();
//        System.out.println("Items visible: ");
    }

    public void go(String argument) {
        if (!isValidDirection(argument)) {
            System.out.println("Invalid direction.");
            return;
        }
        for (Direction direction : currentRoom.getDirections()) {
            if (argument.equals(direction.getDirectionName())) {
                currentRoom = findRoom(direction.getRoom());
                return;
            }
        }
        System.out.println("Can't move in that direction.");
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
}
