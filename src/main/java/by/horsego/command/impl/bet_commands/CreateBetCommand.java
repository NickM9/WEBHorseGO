package by.horsego.command.impl.bet_commands;

import by.horsego.bean.User;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.BetService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Implementation of the user command to create a bet for a participant in the game
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "CREATE_BET" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class CreateBetCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.getBetService();
    private final static Logger logger = Logger.getLogger(CreateBetCommand.class);

    /**
     * This method create the bet on a participant in the game.
     *
     * Get parameters user, game id, horse id, bet type, bet amount from request object.
     * Apply {@link BetService} object and call {@link BetService#isBetCreated(User, int, int, String, double)} method
     * In case of successful execution displays a localized message about a successful create,
     * otherwise, the bet is not create, using a {@link MessagesManager} class,
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (main).
     *
     * @see BetService
     * @see BetService#isBetCreated(User, int, int, String, double)
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

        String page = PagesManager.getProperty("main");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        int gameId = Integer.parseInt(request.getParameter("gameId"));
        int horseId = Integer.parseInt(request.getParameter("horseId"));
        String strBetType = request.getParameter("betType");
        double betAmount = Double.parseDouble(request.getParameter("betAmount"));

        boolean betCreated = false;
        try {
            betCreated = betService.isBetCreated(user, gameId, horseId, strBetType, betAmount);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(betCreated){
            session.setAttribute("betCreateMessage", MessagesManager.getProperty("betCreated", locale));
        } else {
            session.setAttribute("betCreateMessage", MessagesManager.getProperty("betNotCreated", locale));
        }

        return page;
    }
}
