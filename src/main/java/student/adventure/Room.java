package student.adventure;

import java.util.ArrayList;

/** A class that handles the Room functionality of the Adventure Game. */
public class Room {
    private String name;
    private String description;
    private String image;
    private ArrayList<Direction> directions;
    private ArrayList<Item> items;

    /**
     * Sets the Room name.
     * @param name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Room name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Room description.
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the Room description.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the Room image.
     * @param image String
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Gets the Room image.
     * @return String
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the Room's List of Directions.
     * @param directions ArrayList<Direction>
     */
    public void setDirections(ArrayList<Direction> directions) {
        this.directions = directions;
    }

    /**
     * Gets the Room's List of Directions.
     * @return ArrayList<Direction>
     */
    public ArrayList<Direction> getDirections() {
        return directions;
    }

    /**
     * Sets the Room's List of Items.
     * @param items ArrayList<Item>
     */
    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Gets the Room's List of Items.
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Overrides the equals function to compare two Rooms.
     * Because Rooms represent unique locations, two Rooms are defined
     * as equal if their Room names match.
     * @param other Room
     * @return boolean
     */
    public boolean equals(Room other) {
        if (this == other) {
            return true;
        }
        return other != null && name.equalsIgnoreCase(other.getName());
    }

    /**
     * Checks if any of the attributes of a valid Room are missing.
     * @return boolean
     */
    public boolean isEmpty() {
        return name == null || description == null || directions == null || items == null;
    }
}
