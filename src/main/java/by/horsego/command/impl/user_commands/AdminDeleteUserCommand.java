package by.horsego.command.impl.user_commands;

import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import by.horsego.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Implementation administrator command for user delete.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "ADMIN_DELETE_USER" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminDeleteUserCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(AdminDeleteUserCommand.class);

    /**
     * This method delete user.
     *
     * Get parameter user id from request object.
     * Apply {@link UserService} object and call {@link UserService#isUserDeleted(int)} method
     * In case of successful execution displays a localized message about a successful delete,
     * otherwise, the game is not deleted, using a {@link MessagesManager} class,
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user.
     *
     * @see UserService
     * @see UserService#isUserDeleted(int)
     * @see MessagesManager
     * @see MessagesManager#getProperty(String, Locale)
     * @see PagesManager
     * @see PagesManager#getProperty(String)
     * @param request
     * @return page that will be sent in response to the user.
     * @throws CommandException
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = PagesManager.getProperty("admin-users");
        HttpSession session = request.getSession();

        int userId = Integer.parseInt(request.getParameter("userId"));

        boolean userDelete = false;
        try {
            userDelete = userService.isUserDeleted(userId);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(userDelete){
            session.setAttribute("deleteUserMessage", MessagesManager.getProperty("userDeleted", locale));
        } else {
            session.setAttribute("deleteUserMessage", MessagesManager.getProperty("userNotDeleted", locale));
        }

        return page;
    }
}
