package student.adventure;

import com.google.gson.JsonParseException;

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

    public void printRoom() {
        System.out.println(name);
        System.out.println(description);
        for (Direction direction : directions) {
            direction.printDirection();
        }
        for (Item item : items) {
            item.printItem();
        }
    }

    public void checkForNull() throws JsonParseException {
        if (name == null || description == null) {
            throw new JsonParseException("Missing field.");
        }
        try {
            for (Direction direction : directions) {
                direction.checkForNull();
            }
            for (Item item : items) {
                if (item != null) {
                    item.checkForNull();
                }
            }
        } catch (JsonParseException e) {
            throw new JsonParseException("Missing field.");
        }
    }

    public void checkForDuplicates() throws JsonParseException {
        for (Direction direction1 : directions) {
            int count = 0;
            for (Direction direction2 : directions) {
                if (direction1.equals(direction2) && count == 1) {
                    throw new JsonParseException("Duplicate direction.");
                } else if (direction1.equals(direction2)) {
                    count++;
                }
            }
        }
        for (Item item1 : items) {
            int count = 0;
            for (Item item2 : items) {
                if (item1.equals(item2) && count == 1) {
                    throw new JsonParseException("Duplicate item.");
                } else if (item1.equals(item2)) {
                    count++;
                }
            }
        }
    }

    public void normalizeRoom() {
        name = name.toLowerCase();
        for (Direction direction : directions) {
            direction.normalizeDirection();
        }
        for (Item item : items) {
            item.normalizeItem();
        }
    }
}
