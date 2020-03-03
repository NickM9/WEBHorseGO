package by.horsego.command.impl.user_commands;

import by.horsego.command.Command;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.PagesManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Implementation user command to change language.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "CHANGE_LANGUAGE" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ChangeLanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        String page = PagesManager.getProperty("main");

        HttpSession session = request.getSession();
        String language = request.getParameter("local");
        session.setAttribute("local", language);

        return page;
    }
}
