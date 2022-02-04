package student.adventure;

public class Direction {
    private String directionName;
    private String room;

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }

    public void printDirection() {
        System.out.println(directionName + ": " + room);
    }
}
