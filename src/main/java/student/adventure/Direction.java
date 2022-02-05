package student.adventure;

import com.google.gson.JsonParseException;

/** A class that handles the Direciton functionality of the Adventure Game. */
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

    public void printDirection() {
        System.out.println(directionName + ": " + room);
    }

    public void checkNullDirectionField() throws JsonParseException {
        if (directionName == null || room == null) {
            throw new JsonParseException("Missing field");
        }
    }

    public void normalizeDirection() {
        directionName = directionName.toLowerCase();
        room = room.toLowerCase();
    }
}
