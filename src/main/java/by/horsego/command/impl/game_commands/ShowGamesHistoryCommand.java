package by.horsego.command.impl.game_commands;

import by.horsego.bean.BetType;
import by.horsego.bean.Game;
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
import java.util.List;
import java.util.Locale;

/**
 * Implementation command to show games history.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "SHOW_GAMES_HISTORY as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ShowGamesHistoryCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private double total = 5;
    private final static Logger logger = Logger.getLogger(ShowGamesHistoryCommand.class);

    /**
     * This method get games history.
     *
     * Get parameters page id from request object.
     * The value pageId is used to realize pagination.
     *
     * Apply {@link GameService} object and call {@link GameService#getAllGames()} method
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bets-history).
     *
     * @see GameService
     * @see GameService#getAllGames()
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

        String page = PagesManager.getProperty("games-history");
        int pageId = Integer.parseInt(request.getParameter("page"));

        List<Game> allGames = null;
        try {
            allGames = gameService.getAllGames();
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        HttpSession session = request.getSession();
        Locale locale = new Locale((String) session.getAttribute("local"));

        if (allGames == null || allGames.isEmpty()) {
            request.setAttribute("gamesTableMessage", MessagesManager.getProperty("gamesTableMessage", locale));
            return page;
        }

        int paddingSize = (int) Math.ceil(allGames.size() / total);
        int startIndex = (int) ((pageId - 1) * total);
        int endIndex = (int) (startIndex + total);

        if (endIndex > allGames.size()){
            endIndex = allGames.size();
        }

        List<Game> gamesList = allGames.subList(startIndex, endIndex);

        request.setAttribute("paddingSize", paddingSize);
        request.setAttribute("gamesList", gamesList);

        BetType.TypeEnum[] betTypes = BetType.TypeEnum.values();
        request.setAttribute("betTypes", betTypes);

        return page;

    }
}
