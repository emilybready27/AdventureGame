package student.adventure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** A class that handles the Direction functionality of the Adventure Game. */
public class Direction {
    private static final Set<String> validDirections = new HashSet(Arrays.asList("north","south",
            "east","west","northeast","northwest","southeast","southwest"));
    private String directionName;
    private String room;

    public void setDirectionName(String name) {
        this.directionName = name;
    }

    public String getDirectionName() {
        return directionName;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    /**
     * Overrides the equals function to compare two Directions.
     * Because Direction names cannot be repeated within a single room,
     * two Directions are defined as equal if their Direction names match.
     * @param other Direction
     * @return boolean
     */
    public boolean equals(Direction other) {
        if (this == other) {
            return true;
        }
        return other != null && directionName.equalsIgnoreCase(other.getDirectionName());
    }

    /**
     * Determines if a given argument is a valid Direction.
     * @param argument String
     * @return boolean
     */
    public static boolean isValidDirection(String argument) {
        return validDirections.contains(argument);
    }

    /**
     * Gets the set of valid Directions as an ArrayList of Strings.
     * @return ArrayList<String>
     */
    public static ArrayList<String> getValidDirections() {
        return new ArrayList<>(validDirections);
    }

    /**
     * Checks if any of the attributes of a valid Direction are missing.
     * @return boolean
     */
    public boolean isEmpty() {
        return directionName == null || room == null;
    }
}
