import student.adventure.AdventureGame;

import java.util.Scanner;

/** A class that controls the flow of the AdventureGame user commands. */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        System.out.println("Input the path to a valid JSON file.");
        AdventureGame adventureGame = setUpGame();
        adventureGame.examine();

        boolean quit = false;
        while (!quit) {
            String[] userInput = getUserInput();
            switch (userInput[0]) {
                case "quit":
                case "exit":
                    quit = adventureGame.quit();
                    break;
                case "go":
                    quit = adventureGame.go(userInput[1]);
                    break;
                case "examine":
                    adventureGame.examine();
                    break;
                case "take":
                    adventureGame.take(userInput[1]);
                    break;
                case "drop":
                    adventureGame.drop(userInput[1]);
                    break;
                default:
                    adventureGame.invalidCommand(userInput);
                    break;
            }
        }
    }

    /**
     * Sets up the AdventureGame with a path to a JSON file inputted by user.
     * Prompts the user again when input is invalid.
     * @return AdventureGame
     */
    private static AdventureGame setUpGame() {
        Scanner scanner = new Scanner(System.in);
        String path;
        AdventureGame adventureGame;
        while (true) {
            System.out.print("> ");
            try {
                path = scanner.next();
                adventureGame = new AdventureGame(path);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Input a valid JSON file.");
                scanner.reset();
            }
        }
        return adventureGame;
    }

    /**
     * Gets the user input from the console.
     * @return String[]
     */
    public static String[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String input = "";
        while (input.isEmpty()) {
            input = scanner.nextLine();
        }
        return parseUserInput(input);
    }

    /**
     * Parses the user input into commands and corresponding arguments.
     * @param input String
     * @return String[]
     */
    private static String[] parseUserInput(String input) {
        String[] inputArray = input.trim().split(" ", 2);
        String[] userInput = new String[]{"", ""};
        if (inputArray.length >= 1) {
            userInput[0] = inputArray[0].toLowerCase().trim();
        }
        if (inputArray.length >= 2) {
            userInput[1] = inputArray[1].toLowerCase().trim();
        }
        return userInput;
    }
}
