package student.adventure;

/** A class that handles the Direction functionality of the Adventure Game. */
public class Direction {
    private String directionName;
    private String room;

    /**
     * Sets the Direction name.
     * @param name String
     */
    public void setDirectionName(String name) {
        this.directionName = name;
    }

    /**
     * Gets the Direction name.
     * @return String
     */
    public String getDirectionName() {
        return directionName;
    }

    /**
     * Sets the Room name.
     * @param room String
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Gets the Room name;
     * @return String
     */
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
     * Determines if a given argument is north, south, east, west,
     * northeast, northwest, southeast, or southwest.
     * @param argument String
     * @return boolean
     */
    public static boolean isValidDirection(String argument) {
        return (argument.equalsIgnoreCase("north") || argument.equalsIgnoreCase("south")
                || argument.equalsIgnoreCase("east") || argument.equalsIgnoreCase("west")
                || argument.equalsIgnoreCase("northeast") || argument.equalsIgnoreCase("northwest")
                || argument.equalsIgnoreCase("southeast") || argument.equalsIgnoreCase("southwest"));
    }
}
