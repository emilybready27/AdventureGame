package student.adventure;

import java.util.Scanner;

/** A class that handles the user input of the Adventure Game. */
public class UserInput {
    private String command;
    private String argument;

    public UserInput() {
        this.command = "";
        this.argument = "";
    }

    public UserInput(String command) {
        this.command = command;
        this.argument = "";
    }

    public UserInput(String command, String argument) {
        this.command = command;
        this.argument = argument;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    public String getArgument() {
        return argument;
    }

    public String getFullInput() {
        return this.command + " " + this.argument;
    }

    public static UserInput getUserInput() {
        Scanner scanner = new Scanner(System.in);
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
}
