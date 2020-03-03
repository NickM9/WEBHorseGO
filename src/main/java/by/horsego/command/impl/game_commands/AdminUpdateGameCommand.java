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
 * Implementation administrator command for game update.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "ADMIN_UPDATE_GAME" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminUpdateGameCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private final static Logger logger = Logger.getLogger(AdminUpdateGameCommand.class);

    /**
     * This method update game.
     *
     * Get parameters game id, game played, array horse id from request object.
     * Apply {@link GameService} object and call {@link GameService#isGameUpdated(int, boolean, String[])} method
     * In case of successful execution displays a localized message about a successful update,
     * otherwise, the game is not updated, using a {@link MessagesManager} class,
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (admin-games).
     *
     * @see GameService
     * @see GameService#isGameUpdated(int, boolean, String[])
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

        int gameId = Integer.parseInt(request.getParameter("gameId"));
        boolean gamePlayed = Boolean.parseBoolean(request.getParameter("gamePlayed"));
        String[] horsesId = request.getParameterValues("horseId");

        boolean gameUpdated = false;
        try {
            gameUpdated = gameService.isGameUpdated(gameId, gamePlayed, horsesId);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(gameUpdated){
            session.setAttribute("gameUpdateMessage", MessagesManager.getProperty("gameUpdated", locale));
        } else {
            session.setAttribute("gameUpdateMessage", MessagesManager.getProperty("gameNotUpdated", locale));
        }

        return page;
    }
}
