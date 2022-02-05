package student.adventure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/** A class that handles the current state of the Adventure Game. */
public class AdventureGame {
    @Retention(RetentionPolicy.RUNTIME)
    public @interface JsonRequired {}

    @JsonRequired private final String startingRoom;
    @JsonRequired private final String endingRoom;
    @JsonRequired private final ArrayList<Room> rooms;

    public AdventureGame(String startingRoom, String endingRoom, ArrayList<Room> rooms) {
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
        this.rooms = new ArrayList<>(rooms);
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