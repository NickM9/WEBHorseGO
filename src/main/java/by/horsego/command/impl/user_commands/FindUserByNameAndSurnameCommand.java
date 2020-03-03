package by.horsego.command.impl.user_commands;

import by.horsego.bean.Bet;
import by.horsego.bean.Game;
import by.horsego.bean.User;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.BetService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import by.horsego.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FindUserByNameAndSurnameCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private BetService betService = serviceFactory.getBetService();
    private static final Logger logger = Logger.getLogger(FindUserByNameAndSurnameCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = PagesManager.getProperty("admin-single-user");

        String userName = request.getParameter("foundUserName");
        String userSurname = request.getParameter("foundUserSurname");

        List<User> foundUsers = null;
        try {
            foundUsers = userService.findUsersByNameAndSurname(userName, userSurname);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        HttpSession session = request.getSession();
        Locale locale = new Locale((String) session.getAttribute("local"));

        if (foundUsers == null || foundUsers.isEmpty()){
            request.setAttribute("singleUserMessage", MessagesManager.getProperty("singleUserMessage", locale));
            return page;
        }

        if(foundUsers.size() == 1){
            User foundUser = foundUsers.get(0);
            Map<Bet, Game> betHistoryMap = null;
            try {
                betHistoryMap = betService.getAllUserBets(foundUser);
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }

            request.setAttribute("foundUserId", foundUser.getId());
            request.setAttribute("foundUser", foundUser);
            request.setAttribute("betHistoryMap", betHistoryMap);

            return page;
        }

        request.setAttribute("foundUsers", foundUsers);
        page = PagesManager.getProperty("admin-choose-user");

        return page;
    }
}
