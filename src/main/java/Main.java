import com.google.gson.JsonParseException;
import student.adventure.AdventureGame;
import student.adventure.UserInput;

import java.io.IOException;
import java.util.NoSuchElementException;
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
            UserInput userInput = UserInput.getUserInput();
            switch (userInput.getCommand()) {
                case "quit":
                case "exit":
                    System.out.println("Goodbye!");
                    quit = true;
                    break;
                case "go":
                    quit = adventureGame.go(userInput.getArgument());
                    break;
                case "examine":
                    adventureGame.examine();
                    break;
                case "take":
                    adventureGame.take(userInput.getArgument());
                    break;
                case "drop":
                    adventureGame.drop(userInput.getArgument());
                    break;
                default:
                    System.out.println("I don't understand " + userInput.getFullInput() + "!");
                    break;
            }
        }

    }
}
