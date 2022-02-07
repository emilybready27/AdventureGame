package student.adventure;

/** A class that handles the Direction functionality of the Adventure Game. */
public class Direction {
    private String directionName;
    private String room;

    public Direction() {
    }

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

    public boolean equals(Direction other) {
        if (this == other) {
            return true;
        }
        return other != null && directionName.equalsIgnoreCase(other.getDirectionName());
    }

    public static boolean isValidDirection(String argument) {
        return (argument.equalsIgnoreCase("north") || argument.equalsIgnoreCase("south")
                || argument.equalsIgnoreCase("east") || argument.equalsIgnoreCase("west")
                || argument.equalsIgnoreCase("northeast") || argument.equalsIgnoreCase("northwest")
                || argument.equalsIgnoreCase("southeast") || argument.equalsIgnoreCase("southwest"));
    }
}
