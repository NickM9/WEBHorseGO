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
 * Implementation administrator command for game delete.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "ADMIN_DELETE_GAME" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminDeleteGameCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private final static Logger logger = Logger.getLogger(AdminDeleteGameCommand.class);

    /**
     * This method delete game.
     *
     * Get parameter game id from request object.
     * Apply {@link GameService} object and call {@link GameService#isGameDeleted(int)} method
     * In case of successful execution displays a localized message about a successful delete,
     * otherwise, the game is not deleted, using a {@link MessagesManager} class,
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (admin-games).
     *
     * @see GameService
     * @see GameService#isGameDeleted(int)
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

        String page = PagesManager.getProperty("admin-games");
        HttpSession session = request.getSession();

        int gameId = Integer.parseInt(request.getParameter("gameId"));

        boolean gameDeleted = false;
        try {
            gameDeleted = gameService.isGameDeleted(gameId);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (gameDeleted){
            session.setAttribute("gameDeleteMessage", MessagesManager.getProperty("gameDeleted", locale));
        } else {
            session.setAttribute("gameDeleteMessage", MessagesManager.getProperty("gameNotDeleted", locale));
        }

        return page;
    }
}
