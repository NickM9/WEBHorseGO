package by.horsego.command.impl.user_commands;

import by.horsego.properties_manager.PagesManager;
import by.horsego.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        session.invalidate();
        String page = PagesManager.getProperty("index");
        return page;

    }
}
