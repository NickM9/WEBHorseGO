package by.horsego.command.impl.game_commands;

import by.horsego.bean.Horse;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.GameService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Implementation administrator command to start the game.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "ADMIN_START_GAME" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminStartGameCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private final static Logger logger = Logger.getLogger(AdminStartGameCommand.class);

    /**
     * This method start the game.
     *
     * Get parameter game id from request object.
     * Apply {@link GameService} object and call {@link GameService#startGame(int)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (admin-games).
     *
     * @see GameService
     * @see GameService#startGame(int)
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

        Map<Integer, Horse> gameResult = null;
        try {
            gameResult = gameService.startGame(gameId);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        session.setAttribute("gameResult", gameResult);

        return page;
    }
}
