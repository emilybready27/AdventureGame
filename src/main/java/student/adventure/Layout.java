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

/** A class that handles the map and layout of the Adventure Game. */
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

    public static Layout validateJson(String path) throws IllegalArgumentException {
        try {
            String json = readFileAsString(path);
            Gson gson = new Gson();
            Layout layout = gson.fromJson(json, Layout.class);
            layout.checkForMissingFields();
            layout.checkForDuplicates();
            layout.checkForNonexistentRoom();
            layout.checkForInvalidFields();
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
    private static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private void checkForMissingFields() throws JsonParseException {
        if (startingRoom == null || endingRoom == null) {
            throw new JsonParseException("Missing Field.");
        }
        for (Room room : rooms) {
            if (room.getName() == null || room.getDescription() == null) {
                throw new JsonParseException("Missing field.");
            }
            for (Direction direction : room.getDirections()) {
                if (direction.getDirectionName() == null || direction.getRoom() == null) {
                    throw new JsonParseException("Missing field.");
                }
            }
            for (Item item : room.getItems()) {
                if (item != null && (item.getItemName() == null || item.getItemDescription() == null)) {
                    throw new JsonParseException("Missing field.");
                }
            }
        }
    }

    private void checkForDuplicates() throws JsonParseException {
        if (startingRoom.equals(endingRoom)) {
            throw new JsonParseException("Duplicate field.");
        }
        for (Room room1 : rooms) {
            int count = 0;
            for (Room room2 : rooms) {
                if (room1.equals(room2) && count == 1) {
                    throw new JsonParseException("Duplicate field.");
                } else if (room1.equals(room2)) {
                    count++;
                }
            }
            if (hasDuplicateDirections(room1) || hasDuplicateItems(room1)) {
                throw new JsonParseException("Duplicate field.");
            }
        }
    }

    private boolean hasDuplicateDirections(Room room) {
        for (Direction direction1 : room.getDirections()) {
            int count = 0;
            for (Direction direction2 : room.getDirections()) {
                if (direction1.equals(direction2) && count == 1) {
                    return true;
                } else if (direction1.equals(direction2)) {
                    count++;
                }
            }
        }
        return false;
    }

    private boolean hasDuplicateItems(Room room) {
        for (Item item1 : room.getItems()) {
            int count = 0;
            for (Item item2 : room.getItems()) {
                if (item1.equals(item2) && count == 1) {
                    return true;
                } else if (item1.equals(item2)) {
                    count++;
                }
            }
        }
        return false;
    }

    private void checkForNonexistentRoom() throws JsonParseException {
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

    private ArrayList<String> getRoomNames() {
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    private void checkForInvalidFields() throws JsonParseException {
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

    private void normalizeLayout() {
        startingRoom = startingRoom.toLowerCase();
        endingRoom = endingRoom.toLowerCase();
        for (Room room : rooms) {
            room.setName(room.getName().toLowerCase());
            for (Direction direction : room.getDirections()) {
                direction.setDirectionName(direction.getDirectionName().toLowerCase());
                direction.setRoom(direction.getRoom().toLowerCase());
            }
            for (Item item : room.getItems()) {
                item.setItemName(item.getItemName().toLowerCase());
            }
        }
    }
}