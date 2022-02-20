package student.adventure;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static student.adventure.Direction.isValidDirection;
import static student.adventure.Item.isValidItem;

/** A class that handles the layout and structure of the Adventure Game. */
public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    /**
     * Attempts to construct a Layout to represent the JSON schema.
     * @param path String
     * @throws IllegalArgumentException
     */
    public Layout(String path) throws IllegalArgumentException {
        Layout layout;
        try {
            layout = parseJson(path);
            this.startingRoom = layout.startingRoom;
            this.endingRoom = layout.endingRoom;
            this.rooms = new ArrayList<>(layout.rooms);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid file.");
        }

        if (layout.isEmpty() || layout.isInvalidLayout()) {
            throw new IllegalArgumentException("Invalid JSON.");
        }
        layout.normalizeToLowerCase();
    }

    /**
     * Attempts to parse the JSON file into the Layout object.
     * @param path String
     * @return Layout
     * @throws IOException Invalid read
     */
    private static Layout parseJson(String path) throws IOException {
        String json = readFileAsString(path);
        Gson gson = new Gson();
        return gson.fromJson(json, Layout.class);
    }

    /**
     * Transforms a JSON file's contents into a String to facilitate GSON parsing.
     * @param path String
     * @return String
     * @throws IOException Read error
     */
    private static String readFileAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
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

    /**
     * Gets a List of Strings of all the Room names.
     * @return ArrayList<String>
     */
    public ArrayList<String> getRoomNames() {
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room : getRooms()) {
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    /**
     * Gets a List of Strings of all the Direction names in a Room.
     * @return ArrayList<String>
     */
    public ArrayList<String> getRoomDirectionNames(Room room) {
        ArrayList<String> roomDirectionNames = new ArrayList<>();
        for (Direction direction : room.getDirections()) {
            roomDirectionNames.add(direction.getDirectionName());
        }
        return roomDirectionNames;
    }

    /**
     * Gets a List of Strings of all the Item descriptions in a Room.
     * @return ArrayList<String>
     */
    public ArrayList<String> getRoomItemDescriptions(Room room) {
        ArrayList<String> roomItemNames = new ArrayList<>();
        for (Item item : room.getItems()) {
            roomItemNames.add(item.getItemDescription());
        }
        return roomItemNames;
    }

    /**
     * Locates the Room object given the String name representing it.
     * @param name String
     * @return Room
     */
    public Room getRoomByName(String name) {
        for (Room room : getRooms()) {
            if (name.equalsIgnoreCase(room.getName())) {
                return room;
            }
        }
        return null;
    }

    public boolean equals(Layout other) {
        if (this == other) {
            return true;
        }
        return other != null
                && startingRoom.equals(other.startingRoom)
                && endingRoom.equals(other.endingRoom)
                && getRoomNames().equals(other.getRoomNames());
    }

    /**
     * Checks if any of the attributes of a valid Layout are missing.
     * @return boolean
     */
    private boolean isEmpty() {
        return startingRoom == null || endingRoom == null || rooms.isEmpty();
    }


    /**
     * Checks several conditions for an invalid layout.
     * @return boolean
     */
    private boolean isInvalidLayout() {
        return hasMissingFields() || hasDuplicates() || hasNonexistentRoom() || hasInvalidFields();
    }

    /**
     * Checks for fields from the JSON file that were not filled and by default are null.
     * Any instance of a missing field is treated as invalid Layout.
     * @throws JsonParseException Missing field
     */
    private boolean hasMissingFields() {
        if (isEmpty()) {
            return true;
        }
        for (Room room : rooms) {
            if (room == null || room.isEmpty()) {
                return true;
            }
            for (Direction direction : room.getDirections()) {
                if (direction == null || direction.isEmpty()) {
                    return true;
                }
            }
            for (Item item : room.getItems()) {
                if (item == null || item.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for duplicates:
     *   1. Two Rooms of the same name
     *   2. Two Directions of the same name within a single Room
     *   3. Two Items of the same description within a single Room.
     * @throws JsonParseException Duplicate field
     */
    private boolean hasDuplicates() {
        if (startingRoom.equalsIgnoreCase(endingRoom)) {
            return true;
        }
        Set<String> roomNames = new HashSet<>(getRoomNames());
        if (roomNames.size() != rooms.size()) {
            return true;
        }
        for (Room room : rooms) {
            Set<String> roomDirectionNames = new HashSet<>(getRoomDirectionNames(room));
            Set<String> roomItemNames = new HashSet<>(getRoomItemDescriptions(room));
            if (roomDirectionNames.size() != room.getDirections().size()
                    || roomItemNames.size() != room.getItems().size()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is an instance where a direction maps the current Room
     * to another Room that is nonexistent.
     * @throws JsonParseException Nonexistent room
     */
    private boolean hasNonexistentRoom() {
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
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a Direction field contains an invalid Direction name.
     * Checks if an Item field contains an invalid Item name.
     * @throws JsonParseException Invalid field
     */
    private boolean hasInvalidFields() {
        for (Room room : rooms) {
            for (Direction direction : room.getDirections()) {
                if (!isValidDirection(direction.getDirectionName().toLowerCase())) {
                    return true;
                }
            }

            for (Item item : room.getItems()) {
                if (!isValidItem((item.getItemName().toLowerCase()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Sanitizes the data to so that Direction names and Item names contain only
     * lowercase values, while Room names retain their original casing.
     */
    private void normalizeToLowerCase() {
        for (Room room : rooms) {
            for (Direction direction : room.getDirections()) {
                direction.setDirectionName(direction.getDirectionName().toLowerCase());
            }
            for (Item item : room.getItems()) {
                item.setItemName(item.getItemName().toLowerCase());
            }
        }
    }
}