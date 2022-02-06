package student.adventure;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/** A class that handles the map / layout of the Adventure Game. */
public class Layout {
    private String startingRoom;
    private String endingRoom;
    private ArrayList<Room> rooms;

    public Layout() {
    }

    public void setStartingRoom(String startingRoom) {
        this.startingRoom = startingRoom;
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public void setEndingRoom(String endingRoom) {
        this.endingRoom = endingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void printLayout() {
        System.out.println(startingRoom + " -> " + endingRoom);
        System.out.println();
        for (Room room : rooms) {
            room.printRoom();
            System.out.println();
        }
    }

    public void checkForNull() throws JsonParseException {
        if (startingRoom == null || endingRoom == null) {
            throw new JsonParseException("Missing Field.");
        }
        try {
            for (Room room : rooms) {
                room.checkForNull();
            }
        } catch (JsonParseException e) {
            throw new JsonParseException("Missing field.");
        }
    }

    public void checkForDuplicates() throws JsonParseException {
        for (Room room1 : rooms) {
            int count = 0;
            for (Room room2 : rooms) {
                if (room1.equals(room2) && count == 1) {
                    throw new JsonParseException("Duplicate room.");
                } else if (room1.equals(room2)) {
                    count++;
                }
            }
            room1.checkForDuplicates();
        }
    }

    public void normalizeLayout() {
        startingRoom = startingRoom.toLowerCase();
        endingRoom = endingRoom.toLowerCase();
        for (Room room : rooms) {
            room.normalizeRoom();
        }
    }

    public static Layout parseJson(String path) throws IllegalArgumentException {
        try {
            String json = readFileAsString(path);
            Gson gson = new Gson();
            Layout layout = gson.fromJson(json, Layout.class);
            layout.checkForNull();
            layout.checkForDuplicates();
            layout.normalizeLayout();
            return layout;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid.");
        }

    }

    /**
     * Transforms a JSON file's contents into a String to facilitate GSON parsing.
     * @param file A String for path to file
     * @return A String of the file's contents
     * @throws IOException
     */
    public static String readFileAsString(String file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file)));
    }
}