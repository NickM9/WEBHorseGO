package by.horsego.command.impl.bet_commands;

import by.horsego.bean.Bet;
import by.horsego.bean.Game;
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
import java.util.*;

/**
 * Implementation user command to show bets history.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "SHOW_BETS_HISTORY" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ShowBetsHistoryCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private BetService betService = serviceFactory.getBetService();
    private double total = 10;
    private final static Logger logger = Logger.getLogger(ShowBetsHistoryCommand.class);

    /**
     * This method get user bets history.
     *
     * Get parameters user, page id from request object.
     * The value pageId is used to realize pagination.
     * Apply {@link BetService} object and call {@link BetService#getAllUserBets(User)} method
     * In case of successful execution set page with bets,
     * otherwise, the bets are not updated, using a {@link MessagesManager} class,
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bets-history).
     *
     * @see BetService
     * @see BetService#getAllUserBets(User)
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

        String page = PagesManager.getProperty("bets-history");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        int pageId = Integer.parseInt(request.getParameter("page"));

        Map<Bet, Game> betsHistory = null;
        try {
            betsHistory = betService.getAllUserBets(user);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (betsHistory == null || betsHistory.isEmpty()) {
            request.setAttribute("betHistoryMessage", MessagesManager.getProperty("betHistoryMessage", locale));
            return page;
        }
        
        List<Map.Entry<Bet, Game>> bets = new ArrayList<>(betsHistory.entrySet());

        int paddingSize = (int) Math.ceil(betsHistory.size() / total);
        int startIndex = (int) ((pageId - 1) * total);
        int endIndex = (int) (startIndex + total);

        if (endIndex > betsHistory.size()){
            endIndex = betsHistory.size();
        }

        List<Map.Entry<Bet, Game>> betList = bets.subList(startIndex, endIndex);
        Map<Bet, Game> betHistoryMap = new LinkedHashMap<>();

        for (Map.Entry<Bet, Game> entry : betList){
            betHistoryMap.put(entry.getKey(), entry.getValue());
        }

        request.setAttribute("paddingSize", paddingSize);
        request.setAttribute("betHistoryMap", betHistoryMap);

        return page;
    }
}
