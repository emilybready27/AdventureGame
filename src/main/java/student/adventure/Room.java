package student.adventure;

import java.util.ArrayList;

/** A class that handles the Room functionality of the Adventure Game. */
public class Room {
    private String name;
    private String description;
    private ArrayList<Direction> directions;
    private ArrayList<Item> items;

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
        this.directions = directions;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public boolean equals(Room other) {
        if (this == other) {
            return true;
        }
        return other != null && name.equals(other.getName());
    }
}
