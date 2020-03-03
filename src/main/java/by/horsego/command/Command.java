package by.horsego.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Base interface for classes in the layer Command.
 *
 * @author Mikita Masukhranau
 * @version 1.0
 */

public interface Command {

    String execute(HttpServletRequest request) throws CommandException;

}
