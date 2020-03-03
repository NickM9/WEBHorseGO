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
 * Implementation administrator command for user create.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "ADMIN_CREATE_USER" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminCreateUserCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(AdminCreateUserCommand.class);

    /**
     * This method create user.
     *
     * Get parameters name, surname, login, password, wallet, role from request object.
     * Apply {@link UserService} object and call {@link UserService#isUserCreated(String, String, String, String, double, String)} method
     * In case of successful execution displays a localized message about a successful create,
     * otherwise, the game is not created, using a {@link MessagesManager} class
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (admin-games).
     *
     * @see UserService
     * @see UserService#isUserCreated(String, String, String, String, double, String)
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

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        double wallet = Double.parseDouble(request.getParameter("wallet"));
        String role = request.getParameter("role");

        boolean userCreate = false;
        try {
            userCreate = userService.isUserCreated(name, surname, login, password, wallet, role);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(userCreate){
            session.setAttribute("createUserMessage", MessagesManager.getProperty("userCreated", locale));
        } else {
            session.setAttribute("createUserMessage", MessagesManager.getProperty("userNotCreated", locale));
        }

        return page;
    }
}
