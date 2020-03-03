package by.horsego.command;

public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Exception e) {
        super(message, e);
    }

    public CommandException(Exception e) {
        super(e);
    }

}
