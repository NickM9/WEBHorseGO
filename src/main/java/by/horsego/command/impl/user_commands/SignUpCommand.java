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

public class SignUpCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private final String REG_EX = "((?=.*[a-z])(?=.*[0-9])(?=.*[A-Z]).{6,16})";
    private static final Logger logger = Logger.getLogger(SignUpCommand.class);

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        HttpSession session = request.getSession();
        String page = PagesManager.getProperty("main");

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (role == null){
            role = "USER";
        }

        boolean loginExist = false;
        try {
            loginExist = userService.isLoginExist(login);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (loginExist){
            page = PagesManager.getProperty("sign-up");
            session.setAttribute("signUpError", MessagesManager.getProperty("signUpError", locale));
            return page;
        }

        boolean passwordValid = false;
        try {
            passwordValid = userService.isPasswordValid(password, REG_EX);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        if(!passwordValid){
            page = PagesManager.getProperty("sign-up");
            session.setAttribute("signUpError", MessagesManager.getProperty("passwordMessage", locale));
            return page;
        }

        boolean signUp = false;
        try {
            signUp = userService.isSignUp(name, surname, login, password, role);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        if (!signUp){
            page = PagesManager.getProperty("sign-up");
            return page;
        }

        User user = null;
        try {
            user = userService.logIn(login, password);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        session.setAttribute("user", user);

        return page;
    }
}
