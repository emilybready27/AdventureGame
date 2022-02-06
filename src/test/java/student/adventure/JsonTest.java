package student.adventure;;

import org.junit.Test;

public class JsonTest {
    /*
    missing: starting room, ending room, rooms, room name, room description, room directions,
             direction name, direction room, room items, item name, item description
    starting room = ending room
    starting room or ending room nonexistent
    any room listed in directions nonexistent
     */

    @Test
    public void testValidFile() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/westeros.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/empty.json");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMissingStartingRoom() {
        AdventureGame adventureGame = new AdventureGame("src/main/resources/missing_starting_room.json");
    }

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



}