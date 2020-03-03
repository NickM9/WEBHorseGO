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

public class LogInCommand implements Command {

	private ServiceFactory serviceFactory = ServiceFactory.getInstance();
	private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(LogInCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = PagesManager.getProperty("main");
        HttpSession session = request.getSession();

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = null;
        try {
            user = userService.logIn(login, password);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (user == null){
            session.setAttribute("loginError", MessagesManager.getProperty("loginError", locale));
            page = PagesManager.getProperty("log-in");
        }

        session.setAttribute("user", user);

        return page;
    }
}
