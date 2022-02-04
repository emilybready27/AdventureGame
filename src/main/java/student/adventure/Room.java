package student.adventure;

import java.util.ArrayList;

public class Room {
    private String name;
    private String description;
    private ArrayList<Direction> directions;

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
        // System.out.println(description);
        for (Direction direction : directions) {
            direction.printDirection();
        }
    }
}
