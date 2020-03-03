package by.horsego.command.impl.user_commands;

import by.horsego.bean.User;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import by.horsego.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class UpdatePasswordCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final Logger logger = Logger.getLogger(UpdatePasswordCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String page = PagesManager.getProperty("password-settings");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        String newPassword = request.getParameter("newPassword");
        String newPasswordConfirm = request.getParameter("newPasswordConfirm");
        String password = request.getParameter("password");

        Locale locale = new Locale((String) session.getAttribute("local"));

        if(!newPassword.equals(newPasswordConfirm)){
            session.setAttribute("updatePasswordMessage", MessagesManager.getProperty("notEqualsPasswords", locale));
            return page;
        }

        boolean updatePassword = false;
        try {
            updatePassword = userService.isPasswordUpdate(user, password, newPassword);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        if(updatePassword){
            session.setAttribute("updatePasswordMessage", MessagesManager.getProperty("passwordIsUpdated", locale));
        } else {
            session.setAttribute("updatePasswordMessage", MessagesManager.getProperty("incorrectPassword", locale));
        }

        return page;
    }
}
