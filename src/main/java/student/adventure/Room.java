package student.adventure;

import com.google.gson.JsonParseException;

import java.util.ArrayList;

public class Room {
    private final String name;
    private final String description;
    private final ArrayList<Direction> directions;
//    private ArrayList<Item> items;

    public Room(String name, String description, ArrayList<Direction> directions) {
        this.name = name;
        this.description = description;
        this.directions = new ArrayList<>(directions);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Direction> getDirections() {
        return new ArrayList<Direction>(directions);
    }

    public void printRoom() {
        System.out.println(name);
        System.out.println(description);
        for (Direction direction : directions) {
            direction.printDirection();
        }
    }

    public void checkNullRoomField() throws JsonParseException {
        if (name == null || description == null) {
            throw new JsonParseException("Missing field");
        }
        try {
            for (Direction direction : directions) {
                direction.checkNullDirectionField();
            }
        } catch (JsonParseException e) {
            throw new JsonParseException("Missing field");
        }
    }
}
