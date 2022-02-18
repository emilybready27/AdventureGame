package student.adventure;

import java.util.Scanner;

/** A class that controls the flow of the AdventureGame user interaction. */
public class UserInteraction {
    private static AdventureGame adventureGame;

    /**
     * Sets up the UserInteraction, AdventureGame, and Layout.
     */
    public static void setUp() {
        printText("Welcome!");
        printText("Input the path to a valid JSON file.");

        while (true) {
            try {
                String path = getUserInput();
                adventureGame = new AdventureGame(path);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Input the path to a valid JSON file.");
            }
        }
        printText(adventureGame.examine(new String[]{"examine",""}));
    }

    /**
     * Turns user commands into game behaviors until user quits or wins.
     */
    public static void play() {
        while (true) {
            String input = getUserInput();
            String[] userInput = parseUserInput(input);
            printText(adventureGame.evaluate(userInput));
            if (adventureGame.hasQuit()) {
                break;
            }
        }
    }

    /**
     * Gets the user input from the console.
     * @return String
     */
    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String userInput = "";
        while (userInput.isEmpty()) {
            userInput = scanner.nextLine();
        }
        return userInput.trim();
    }

    /**
     * Parses the user input into commands and corresponding arguments.
     * @param input String
     * @return String[]
     */
    public static String[] parseUserInput(String input) {
        String lowercased = input.toLowerCase();
        String normalized = lowercased.trim().replaceAll("\\s+", " ");
        String[] split = normalized.split(" ", 2);

        String[] inputArray = new String[]{"", ""};
        if (split.length >= 1) {
            inputArray[0] = split[0];
        }
        if (split.length == 2) {
            inputArray[1] = split[1];
        }
        return inputArray;
    }

    /**
     * Prints a message give by the text.
     * @param text String
     */
    public static void printText(String text) {
        System.out.println(text);
    }
}
