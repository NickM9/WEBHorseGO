package by.horsego.command.impl.user_commands;

import by.horsego.bean.Role;
import by.horsego.bean.User;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.service.BetService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import by.horsego.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

public class ShowUsersCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private BetService betService = serviceFactory.getBetService();
    private double total = 20;
    private static final Logger logger = Logger.getLogger(ShowUsersCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int pageId = Integer.parseInt(request.getParameter("page"));

        List<User> users = null;
        try {
            users = userService.getAllUsers();
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Role[] roles = Role.values();

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (users == null || users.isEmpty()){
            request.setAttribute("usersTableMessage", MessagesManager.getProperty("usersTableMessage", locale));
        }

        int paddingSize = (int) Math.ceil(users.size() / total);

        int startIndex = (int) ((pageId - 1) * total);
        int endIndex = (int) (startIndex + total);

        if (endIndex > users.size()){
            endIndex = users.size();
        }

        List<User> usersList = users.subList(startIndex, endIndex);

        request.setAttribute("paddingSize", paddingSize);
        request.setAttribute("usersList", usersList);
        request.setAttribute("roleList", roles);

        return page;
    }
}
