package student.adventure;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;

/** A class that handles the layout of the Adventure Game. */
public class AdventureGame {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    public AdventureGame() {
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

//    public void examine() {
//        System.out.println(currentRoom.getDescription());
//        System.out.print("From here, you can go: ");
//        for (Direction direction : currentRoom.getDirections()) {
//            System.out.print(direction.getDirectionName() + " ");
//        }
////        System.out.println("Items visible: ");
//    }
}