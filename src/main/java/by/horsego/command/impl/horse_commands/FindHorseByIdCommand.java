package by.horsego.command.impl.horse_commands;

import by.horsego.bean.*;
import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.MessagesManager;
import by.horsego.properties_manager.PagesManager;
import by.horsego.service.HorseService;
import by.horsego.service.ServiceException;
import by.horsego.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Implementation command for find horse.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "FIND_HORSE_BY_ID" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class FindHorseByIdCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.getHorseService();
    private static final Logger logger = Logger.getLogger(FindHorseByIdCommand.class);

    /**
     * This method get horse by id.
     *
     * Get parameters user, horse id from request object.
     * Apply {@link HorseService} object and call {@link HorseService#findHorseById(int)} method
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bets-history).
     *
     * @see HorseService
     * @see HorseService#findHorseById(int)
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

        String page = PagesManager.getProperty("single-horse");
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        int horseId = Integer.parseInt(request.getParameter("horseId"));

        Horse horse = null;
        try {
            horse = horseService.findHorseById(horseId);
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        BetType.TypeEnum[] betTypes = BetType.TypeEnum.values();

        Locale locale = new Locale((String) session.getAttribute("local"));

        if (horse == null){
            request.setAttribute("singleHorseMessage", MessagesManager.getProperty("singleHorseMessage", locale));
        } else {

            List<Game> horseGames = null;
            try {
                horseGames = horseService.getHorseGames(horse);
            } catch (ServiceException e) {
                logger.error(e);
                throw new CommandException(e);
            }

            request.setAttribute("horseGames", horseGames);
        }

        request.setAttribute("horseId", horseId);
        request.setAttribute("horse", horse);
        request.setAttribute("betTypes", betTypes);


        if (user.getRole().equals(Role.ADMIN)) {
            page = PagesManager.getProperty("admin-single-horse");
            return page;
        }

        return page;
    }
}
