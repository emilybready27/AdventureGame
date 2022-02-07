import student.adventure.AdventureGame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!");
        System.out.println("Input the path to a valid JSON file.");
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
            }
        }
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
                    System.out.println("I don't understand " + userInput[0] + " " + userInput[1] + "!");
                    break;
            }
        }
    }

    public static String[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String input = "";
        while (input.isEmpty()) {
            input = scanner.nextLine();
        }
        String[] inputArray = input.trim().split(" ", 2);
        String[] userInput = new String[]{"", ""};
        if (inputArray.length >= 1) {
            userInput[0] = inputArray[0].toLowerCase().trim();
        }
        if (inputArray.length >= 2) {
            userInput[1] = inputArray[1].trim();
        }
        return userInput;
    }
}
