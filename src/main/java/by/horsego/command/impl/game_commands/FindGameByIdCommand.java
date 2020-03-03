package by.horsego.command.impl.game_commands;

import by.horsego.bean.*;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.*;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Implementation command for find game.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "FIND_GAME_BY_ID" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class FindGameByIdCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private HorseService horseService = serviceFactory.getHorseService();
    private final static Logger logger = Logger.getLogger(FindGameByIdCommand.class);

    /**
     * This method get game by id.
     *
     * Get parameters user, game id from request object.
     * Apply {@link GameService} object and call {@link GameService#findGameById(int, User)} method
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bets-history).
     *
     * @see GameService
     * @see GameService#findGameById(int, User)
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

        User user = (User) session.getAttribute("user");
        int gameId = Integer.parseInt(request.getParameter("gameId"));

        Game game = null;
        try {
            game = gameService.findGameById(gameId, user);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (game == null){
            request.setAttribute("singleGameMessage", MessagesManager.getProperty("singleGameMessage", locale));
        }

        BetType.TypeEnum[] betTypes = BetType.TypeEnum.values();

        request.setAttribute("betTypes", betTypes);
        request.setAttribute("gameId", gameId);
        request.setAttribute("game", game);

        String page;

        if (user.getRole().equals(Role.BOOKMAKER)){
            page = PagesManager.getProperty("bookmaker-single-game");
        } else if (user.getRole().equals(Role.ADMIN)) {
            List<Horse> horsesList = null;
            try {
                horsesList = horseService.getAllHorses();
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }
            request.setAttribute("horsesList", horsesList);
            page = PagesManager.getProperty("admin-single-game");
        } else {
            page = PagesManager.getProperty("create-bet");
        }

        return page;
    }
}
