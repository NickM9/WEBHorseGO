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

public class UpdateNameCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(UpdateNameCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = PagesManager.getProperty("name-settings");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String password = request.getParameter("password");

        boolean updateName = false;
        try {
            updateName = userService.isNameUpdated(user, name, surname, password);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (!updateName){
            session.setAttribute("updateNameMessage", MessagesManager.getProperty("incorrectPassword", locale));
            return page;
        }

        session.setAttribute("updateNameMessage", MessagesManager.getProperty("nameIsUpdated", locale));

        return page;
    }
}
