import com.google.gson.*;
import student.adventure.AdventureGame;
import student.adventure.Room;
import student.adventure.Direction;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to the Adventure Game!");
        System.out.println("To get started, please input a valid JSON file.");

        AdventureGame adventureGame;
        while (true) {
            System.out.print("> ");
            try {
                String path = scanner.next(); // "src/main/resources/westeros.json", "src/main/resources/malformed.json"
                String json = readFileAsString(path);
                Gson gson = new Gson();
                adventureGame = gson.fromJson(json, AdventureGame.class);
                adventureGame.checkNullAdventureGameField();
                break;
            } catch (JsonParseException e) {
                System.out.println("Sorry, there was an error with your Json. Try again?");
            } catch (IOException e) {
                System.out.println("Sorry, there was an error with your file. Try again?");
            }
        }



    }


//    public static String getUserInput() {
//        // TODO: sanitize input
//        return scanner.next();
//    }

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
