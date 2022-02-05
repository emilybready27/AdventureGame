package student.adventure;

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
}
