package student.adventure;;

import org.junit.Test;

/** A class that tests handling of JSON files that don't fit the schema or don't exist. */

public class JsonTest {
    @Test
    public void testValidFile() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/westeros.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/empty.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullFile() {
        AdventureGame adventureGame = new AdventureGame(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMalformedFile() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/malformed.json");
    }

    // Missing Fields
    @Test(expected = IllegalArgumentException.class)
    public void testMissingStartingRoom() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_start.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingRooms() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_rooms.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingRoomName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_room_name.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingDirections() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_directions.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingDirectionName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_direction_name.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingItems() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_items.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingItemName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_item_name.json");
    }

    // Duplicates
    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateRoomName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/duplicate_rooms.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateDirectionName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/duplicate_directions.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateItem() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/duplicate_items.json");
    }

    // Start equals end
    @Test(expected = IllegalArgumentException.class)
    public void testStartingRoomEqualsEndingRoom() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/start_equals_end.json");
    }

    // Nonexistent rooms
    @Test(expected = IllegalArgumentException.class)
    public void testNonexistentStartingRoom() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/nonexistent_start.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonexistentEndingRoom() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/nonexistent_end.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonexistentRoom() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/nonexistent_room.json");
    }

    // Invalid keys
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidDirectionName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/invalid_direction.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidItemName() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/invalid_item.json");
    }

}
