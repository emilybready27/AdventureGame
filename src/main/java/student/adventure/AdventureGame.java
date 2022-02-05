package student.adventure;

import java.util.ArrayList;

/** A class that handles the current state of the Adventure Game. */
public class AdventureGame {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    public AdventureGame(String path) {

    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public ArrayList<Room> getRoom() {
        return new ArrayList<Room>(rooms);
    }

    public void printLayout() {
        System.out.println(startingRoom + " -> " + endingRoom);
        System.out.println();
        for (Room room : rooms) {
            room.printRoom();
            System.out.println();
        }
    }
}