import com.google.gson.*;
import student.adventure.AdventureGame;
import student.adventure.Layout;
import student.adventure.UserInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static Layout setUp() {
        System.out.print("> ");
        try {
            String path = scanner.next(); // "src/main/resources/westeros.json", "src/main/resources/malformed.json"
            String json = readFileAsString(path);
            Gson gson = new Gson();
            Layout layout = gson.fromJson(json, Layout.class);
            layout.checkNullLayoutField();
            layout.normalizeLayout();
            return layout;
        } catch (JsonParseException e) {
            System.out.println("Json error: exiting.");
        } catch (IOException e) {
            System.out.println("File error: exiting.");
        }
        return null;
    }

    public static void main(String[] args) {

        System.out.println("Welcome!");
        System.out.println("To get started, input a valid JSON file.");

        Layout layout = Main.setUp();
        if (layout == null) {
            return;
        }
        AdventureGame adventureGame = new AdventureGame(layout);
        adventureGame.examine();

        boolean quit = false;
        while (!quit) {
            UserInput userInput = getUserInput();
            switch (userInput.getCommand()) {
                case "quit":
                case "exit":
                    System.out.println("Goodbye!");
                    quit = true;
                    break;
                case "go":
                    quit = adventureGame.go(userInput.getArgument());
                    if (!quit) {
                        adventureGame.examine();
                    }
                    break;
                case "examine":
                    adventureGame.examine();
                    break;
                case "take":
                    adventureGame.take(userInput.getArgument());
                    break;
                default:
                    System.out.println("I don't understand " + userInput.getFullInput() + "!");
                    break;
            }
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
