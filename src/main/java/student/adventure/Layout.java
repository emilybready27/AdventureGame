package student.adventure;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static student.adventure.Direction.isValidDirection;
import static student.adventure.Item.isValidItem;

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
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<String> getRoomNames() {
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    public void printLayout() {
        System.out.println(startingRoom + " -> " + endingRoom);
        System.out.println();
        for (Room room : rooms) {
            room.printRoom();
            System.out.println();
        }
    }

    public void checkForNull() throws JsonParseException {
        if (startingRoom == null || endingRoom == null) {
            throw new JsonParseException("Missing Field.");
        }
        try {
            for (Room room : rooms) {
                room.checkForNull();
            }
        } catch (JsonParseException e) {
            throw new JsonParseException("Missing field.");
        }
    }

    public void checkForDuplicates() throws JsonParseException {
        if (startingRoom.equals(endingRoom)) {
            throw new JsonParseException("Duplicate room.");
        }
        for (Room room1 : rooms) {
            int count = 0;
            for (Room room2 : rooms) {
                if (room1.equals(room2) && count == 1) {
                    throw new JsonParseException("Duplicate room.");
                } else if (room1.equals(room2)) {
                    count++;
                }
            }
            room1.checkForDuplicates();
        }
    }

    public void checkForNonexistentRoom() throws JsonParseException {
        Set<String> directionRoomNames = new HashSet<>();
        directionRoomNames.add(startingRoom);
        directionRoomNames.add(endingRoom);
        for (Room room : rooms) {
            for (Direction direction : room.getDirections()) {
                directionRoomNames.add(direction.getRoom());
            }
        }
        ArrayList<String> roomNames = getRoomNames();
        for (String directionRoomName : directionRoomNames) {
            if (!roomNames.contains(directionRoomName)) {
                throw new JsonParseException("Nonexistent room.");
            }
        }
    }

    public void checkForValidFields() throws JsonParseException {
        for (Room room : rooms) {
            for (Direction direction : room.getDirections()) {
                if (!isValidDirection(direction.getDirectionName().toLowerCase())) {
                    throw new JsonParseException("Invalid field.");
                }
            }
            for (Item item : room.getItems()) {
                if (!isValidItem(item.getItemName().toLowerCase())) {
                    throw new JsonParseException("Invalid field.");
                }
            }
        }
    }

    public void normalizeLayout() {
        startingRoom = startingRoom.toLowerCase();
        endingRoom = endingRoom.toLowerCase();
        for (Room room : rooms) {
            room.normalizeRoom();
        }
    }

    public static Layout parseJson(String path) throws IllegalArgumentException {
        try {
            String json = readFileAsString(path);
            Gson gson = new Gson();
            Layout layout = gson.fromJson(json, Layout.class);
            layout.checkForNull();
            layout.checkForDuplicates();
            layout.checkForNonexistentRoom();
            layout.checkForValidFields();
            layout.normalizeLayout();
            return layout;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON.");
        }

    }

    /**
     * Transforms a JSON file's contents into a String to facilitate GSON parsing.
     * @param file A String for path to file
     * @return A String of the file's contents
     * @throws IOException
     */
    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}