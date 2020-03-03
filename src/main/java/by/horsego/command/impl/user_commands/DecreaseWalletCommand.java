package by.horsego.command.impl.user_commands;

import by.horsego.bean.User;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import by.horsego.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * Implementation user command to decrease wallet.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "DECREASE_WALLET" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class DecreaseWalletCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(DecreaseWalletCommand.class);

    /**
     * This method decrease user wallet.
     *
     * Get parameters user from session object and sum from request.
     * Apply {@link UserService} object and call {@link UserService#isWalletDecrease(User, double)} method
     * In case of successful execution displays a localized message about a successful decrease,
     * otherwise, the wallet is not decrease, using a {@link MessagesManager} class
     * in {@link MessagesManager#getProperty(String, Locale)} method.
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user.
     *
     * @see UserService
     * @see UserService#isWalletDecrease(User, double)
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

        double sum = Double.parseDouble(request.getParameter("sum"));
        User user = (User) session.getAttribute("user");

        boolean walletUpdated = false;
        try {
            walletUpdated = userService.isWalletDecrease(user, sum);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (walletUpdated){
            session.setAttribute("walletMessage", MessagesManager.getProperty("walletSuccessfullyDecrease", locale));
        } else {
            session.setAttribute("walletMessage", MessagesManager.getProperty("walletIsNotUpdated", locale));
        }

        String page = PagesManager.getProperty("wallet");

        return page;
    }
}
