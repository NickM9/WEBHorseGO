package by.horsego.command.impl.user_commands;

import by.horsego.bean.BetType;
import by.horsego.bean.Game;
import by.horsego.bean.Horse;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.GameService;
import by.horsego.service.HorseService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Implementation command to show start tables.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "SHOW_START_TABLES" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ShowStartTablesCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private GameService gameService = serviceFactory.getGameService();
    private HorseService horseService = serviceFactory.getHorseService();
    private static final Logger logger = Logger.getLogger(ShowStartTablesCommand.class);

    /**
     * This method get horses and games for tables.
     *
     * Using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bets-history).
     *
     * @param request
     * @return page that will be sent in response to the user.
     * @throws CommandException
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = request.getServletPath();

        List<Game> allGames = null;
        try {
            allGames = gameService.getGamesForTable();
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Map<Horse, List<Integer>> horses = null;
        try {
            horses = horseService.getHorsesForTable();
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        BetType.TypeEnum[] betTypes = BetType.TypeEnum.values();

        request.setAttribute("gamesList", allGames);
        request.setAttribute("horsesMap", horses);
        request.setAttribute("betTypes", betTypes);

        return page;
    }
}
