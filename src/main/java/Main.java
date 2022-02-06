import com.google.gson.*;
import student.adventure.AdventureGame;
import student.adventure.Layout;
import student.adventure.UserInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static student.adventure.Layout.setUp;
import static student.adventure.UserInput.getUserInput;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome!");
        System.out.println("To get started, input a valid JSON file.");

        Layout layout = Layout.setUp();
        AdventureGame adventureGame = new AdventureGame(layout);
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
