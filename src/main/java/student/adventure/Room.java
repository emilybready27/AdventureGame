package student.adventure;

import com.google.gson.JsonParseException;

import java.util.ArrayList;

/** A class that handles the Room functionality of the Adventure Game. */
public class Room {
    private String name;
    private String description;
    private ArrayList<Direction> directions;
//    private ArrayList<Item> items;

    public Room() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDirections(ArrayList<Direction> directions) {
        this.directions = new ArrayList<>(directions);
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
