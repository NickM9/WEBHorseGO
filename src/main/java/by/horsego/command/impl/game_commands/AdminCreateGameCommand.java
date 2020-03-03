package by.horsego.command.impl.game_commands;

import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.GameService;
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
 * and pass a string "ADMIN_CREATE_GAME" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminCreateGameCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private final static Logger logger = Logger.getLogger(AdminCreateGameCommand.class);

    /**
     * This method create game.
     *
     * Get parameter array horse id from request object.
     * Apply {@link GameService} object and call {@link GameService#isGameCreated(String[])} method
     * In case of successful execution displays a localized message about a successful create,
     * otherwise, the game is not created, using a {@link MessagesManager} class,
     * if value horsesId is null or empty displays a localized message that no horse checked
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (admin-games).
     *
     * @see GameService
     * @see GameService#isGameCreated(String[])
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

        HttpSession session = request.getSession();

        String page = PagesManager.getProperty("admin-games");
        String[] horsesId = request.getParameterValues("horseId");

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (horsesId == null || horsesId.length == 0){
            session.setAttribute("noHorseChecked", MessagesManager.getProperty("noHorseChecked", locale));
            return page;
        }

        boolean gameCreated = false;
        try {
            gameCreated = gameService.isGameCreated(horsesId);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        if(gameCreated){
            session.setAttribute("gameCreateMessage", MessagesManager.getProperty("gameCreated", locale));
        } else {
            session.setAttribute("gameCreateMessage", MessagesManager.getProperty("gameNotCreated", locale));
        }

        return page;
    }
}
