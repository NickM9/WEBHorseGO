package by.horsego.command.impl.user_commands;

import by.horsego.bean.User;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import by.horsego.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class UpdateLoginCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(UpdateLoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = PagesManager.getProperty("login-settings");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        boolean loginExist = false;
        try {
            loginExist = userService.isLoginExist(login);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(loginExist){
            session.setAttribute("updateLoginMessage", MessagesManager.getProperty("loginIsNotUpdated", locale));
            return page;
        }

        boolean loginUpdated = false;
        try {
            loginUpdated = userService.isLoginUpdated(user, login, password);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        if (!loginUpdated) {
            session.setAttribute("updateLoginMessage", MessagesManager.getProperty("incorrectPassword", locale));
            return page;
        }

        session.setAttribute("updateLoginMessage", MessagesManager.getProperty("loginIsUpdated", locale));

        return page;
    }
}
