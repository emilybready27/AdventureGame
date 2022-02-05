package student.adventure;

import com.google.gson.JsonParseException;

public class Direction {
    private final String directionName;
    private final String room;

    public Direction(String directionName, String room) {
        this.directionName = directionName;
        this.room = room;
    }

    public String getDirectionName() {
        return directionName;
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
}
