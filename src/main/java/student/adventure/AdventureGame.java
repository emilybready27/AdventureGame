package student.adventure;

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

    private Room findRoom(String name) {
        for (Room room : layout.getRooms()) {
            if (name.equals(room.getName())) {
                return room;
            }
        }
        return null;
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
}
