package by.horsego.command.impl.bet_commands;

import by.horsego.command.*;
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
 * Implementation bookmaker command for game bets update on a participant in the game.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "BOOKMAKER_UPDATE_GAME_BETS" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class BookmakerUpdateGameBetsCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private static final Logger logger = Logger.getLogger(BookmakerUpdateGameBetsCommand.class);

    /**
     * This method updates the bets on a participant in the game.
     *
     * Get parameters game id, horse id, array bet coefficients from request object.
     * Apply {@link GameService} object and call {@link GameService#isGameBetsUpdated(int, int, String[])} method
     * In case of successful execution displays a localized message about a successful update,
     * otherwise, the bets are not updated, using a {@link MessagesManager} class,
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bookmaker-find-game).
     *
     * @see GameService
     * @see GameService#isGameBetsUpdated(int, int, String[])
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

        int gameId = Integer.parseInt(request.getParameter("gameId"));
        int horseId = Integer.parseInt(request.getParameter("horseId"));
        String[] betCoefficients = request.getParameterValues("betCoefficient");

        boolean gameBetsUpdated = false;
        try {
            gameBetsUpdated = gameService.isGameBetsUpdated(gameId, horseId, betCoefficients);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(gameBetsUpdated){
            session.setAttribute("gameBetsCreateMessage", MessagesManager.getProperty("gameBetsCreated", locale));
        } else {
            session.setAttribute("gameBetsCreateMessage", MessagesManager.getProperty("gameBetsNotCreated", locale));
        }

        String page = PagesManager.getProperty("bookmaker-find-game");
        page += gameId;

        return page;
    }
}
