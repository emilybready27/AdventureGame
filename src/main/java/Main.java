import student.adventure.AdventureGame;

import java.util.Locale;
import java.util.Scanner;

/** A class that controls the flow of the AdventureGame user commands. */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        System.out.println("Input the path to a valid JSON file.");
        AdventureGame adventureGame = buildGame();
        adventureGame.examine();

        boolean quit = false;
        while (!quit) {
            String[] userInput = getUserInput();
            switch (userInput[0]) {
            case "quit":
            case "exit":
                if (userInput.length == 1) {
                    quit = adventureGame.quit();
                } else {
                    adventureGame.invalidCommand(userInput);
                }
                break;
            case "examine":
                if (userInput.length == 1) {
                    adventureGame.examine();
                } else {
                    adventureGame.invalidCommand(userInput);
                }
                break;
            case "go":
                if (userInput.length == 2) {
                    quit = adventureGame.go(userInput[1]);
                } else {
                    adventureGame.invalidCommand(userInput);
                }
                break;
            case "take":
                if (userInput.length == 2) {
                    adventureGame.take(userInput[1]);
                } else {
                    adventureGame.invalidCommand(userInput);
                }
                break;
            case "drop":
                if (userInput.length == 2) {
                    adventureGame.drop(userInput[1]);
                } else {
                    adventureGame.invalidCommand(userInput);
                }
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
    private static AdventureGame buildGame() {
        Scanner scanner = new Scanner(System.in);
        AdventureGame adventureGame;
        while (true) {
            System.out.print("> ");
            try {
                String path = "";
                while (path.isEmpty()) {
                    path = scanner.nextLine();
                }
                adventureGame = new AdventureGame(path.trim());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Input the path to a valid JSON file.");
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
        String normalized = input.replaceAll("\\s+", " ").trim();
        String[] inputArray = normalized.split(" ");
        if (inputArray.length >= 1) {
            inputArray[0] = inputArray[0].toLowerCase();
        }
        if (inputArray.length >= 2) {
            inputArray[1] = inputArray[1].toLowerCase();
        }
        return inputArray;
    }
}
