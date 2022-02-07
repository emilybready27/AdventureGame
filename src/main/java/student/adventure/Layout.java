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

/** A class that handles the layout and structure of the Adventure Game. */
public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    /**
     * Sets the Layout starting Room.
     * @param startingRoom String
     */
    public void setStartingRoom(String startingRoom) {
        this.startingRoom = startingRoom;
    }

    /**
     * Gets the Layout starting Room.
     * @return String
     */
    public String getStartingRoom() {
        return startingRoom;
    }

    /**
     * Sets the Layout ending Room.
     * @param endingRoom String
     */
    public void setEndingRoom(String endingRoom) {
        this.endingRoom = endingRoom;
    }

    /**
     * Gets the Layout ending Room.
     * @return String
     */
    public String getEndingRoom() {
        return endingRoom;
    }

    /**
     * Sets the Layout List of Rooms.
     * @param rooms ArrayList<Room>
     */
    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the Layout List of Rooms.
     * @return ArrayList<Room>
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Combines the functionality that parses the JSON file using GSON with several
     * checks to ensure that the provided layout fits the necessary schema:
     *   1. Fields must be non-null.
     *   2. Duplicates are not allowed.
     *   3. Directions to non-existent rooms are not allowed.
     *   4. Fields for Items and Directions must be valid.
     * If any step of building and verifying the layout fails, throws an exception.
     * Otherwise, returns a valid layout with normalized values.
     * @param path String
     * @return Layout
     * @throws IllegalArgumentException Invalid JSON
     */
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
     * @param path String
     * @return String
     * @throws IOException Read error
     */
    private static String readFileAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    /**
     * Checks for fields from the JSON file that were not filled and by default are null.
     * Any instance of a missing field is treated as invalid JSON.
     * @throws JsonParseException Missing field
     */
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
                if ((item.getItemName() == null || item.getItemDescription() == null)) {
                    throw new JsonParseException("Missing field.");
                }
            }
        }
    }

    /**
     * Checks for two Rooms of the same name.
     * Checks for two Directions of the same name within a single Room.
     * Checks for two Items of the same name and description within a single Room.
     * @throws JsonParseException Duplicate field
     */
    private void checkForDuplicates() throws JsonParseException {
        if (startingRoom.equalsIgnoreCase(endingRoom)) {
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

    /**
     * Determines if a given Room has duplicate Direction names.
     * Uses equals override to compare two Directions.
     * @param room Room
     * @return boolean
     */
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

    /**
     * Determines if a given Room has duplicate Items.
     * Uses equals override to compare two Items.
     * @param room Room
     * @return boolean
     */
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

    /**
     * Checks if there is an instance where a direction maps the current Room
     * to another Room that is nonexistent.
     * @throws JsonParseException Nonexistent room
     */
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

    /**
     * Gets a List of Strings of all the Room names.
     * Since duplicates were already checked, elements are assumed to be unique.
     * @return ArrayList<String>
     */
    private ArrayList<String> getRoomNames() {
        ArrayList<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getName());
        }
        return roomNames;
    }

    /**
     * Checks if a Direction field contains an invalid Direction name.
     * Checks if an Item field contains an invalid Item name.
     * @throws JsonParseException Invalid field
     */
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

    /**
     * Sanitizes the data to so that Direction names and Item names contain only
     * lowercase values, while Room names retain their original casing.
     */
    private void normalizeLayout() {
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