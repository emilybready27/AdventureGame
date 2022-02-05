package student.adventure;

import com.google.gson.JsonParseException;

import java.util.ArrayList;

/** A class that handles the map / layout of the Adventure Game. */
public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    public Layout() {
    }

    public void setStartingRoom(String startingRoom) {
        this.startingRoom = startingRoom;
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public void setEndingRoom(String endingRoom) {
        this.endingRoom = endingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = new ArrayList<>(rooms);
    }

    public ArrayList<Room> getRooms() {
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

    public void checkNullLayoutField() throws JsonParseException {
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

    public void normalizeLayout() {
        startingRoom = startingRoom.toLowerCase();
        endingRoom = endingRoom.toLowerCase();
        for (Room room : rooms) {
            room.normalizeRoom();
        }
    }
}