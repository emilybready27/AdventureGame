import com.google.gson.*;
import student.adventure.AdventureGame;
import student.adventure.Room;
import student.adventure.Direction;
import student.adventure.UserInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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

        boolean quit = false;
        while (!quit) {
            UserInput userInput = getUserInput();
            String gameOutput;
            switch (userInput.getCommand()) {
                case "quit":
                case "exit":
                    gameOutput = "Until next time, goodbye!";
                    quit = true;
                    break;
                default:
                    gameOutput = "Command not found: try again";
                    break;
            }
            System.out.println(gameOutput);
        }

    }

    public static UserInput getUserInput() {
        System.out.print("> ");
        String input = "";
        while (input.isEmpty()) {
            input = scanner.nextLine();
        }
        String[] inputArray = input.toLowerCase().trim().split(" ", 2);
        UserInput userInput = new UserInput();
        userInput.setCommand(inputArray.length >= 1 ? inputArray[0].trim() : "");
        userInput.setArgument(inputArray.length >= 2 ? inputArray[1].trim() : "");
        return userInput;
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
