package student.adventure;

import com.google.gson.JsonParseException;

import java.util.ArrayList;

/** A class that handles the current state of the Adventure Game. */
public class AdventureGame {
    private final String startingRoom;
    private final String endingRoom;
    private final ArrayList<Room> rooms;

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

    public void checkNullAdventureGameField() throws JsonParseException {
        if (startingRoom == null || endingRoom == null) {
            throw new JsonParseException("Missing Field");
        }
        try {
            for (Room room : rooms) {
                room.checkNullRoomField();
            }
        } catch (JsonParseException e) {
            throw new JsonParseException("Missing field");
        }
    }
}