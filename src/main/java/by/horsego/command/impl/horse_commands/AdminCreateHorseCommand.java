package by.horsego.command.impl.horse_commands;

import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.HorseService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Implementation administrator command for game create.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "ADMIN_CREATE_HORSE" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminCreateHorseCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.getHorseService();
    private final static Logger logger = Logger.getLogger(AdminCreateHorseCommand.class);

    /**
     * This method create horse.
     *
     * Get parameter horse name from request object.
     * Apply {@link HorseService} object and call {@link HorseService#isHorseCreated(String)} method
     * In case of successful execution displays a localized message about a successful create,
     * otherwise, the game is not created, using a {@link MessagesManager} class
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user.
     *
     * @see HorseService
     * @see HorseService#isHorseCreated(String)
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

        String page = PagesManager.getProperty("admin-horses");
        HttpSession session = request.getSession();

        String horseName = request.getParameter("horseName");

        boolean horseCreated = false;
        try {
            horseCreated = horseService.isHorseCreated(horseName);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(horseCreated){
            session.setAttribute("horseCreateMessage", MessagesManager.getProperty("horseCreated", locale));
        } else {
            session.setAttribute("horseCreateMessage", MessagesManager.getProperty("horseNotCreated", locale));
        }

        return page;
    }
}
