package by.horsego.command.impl.horse_commands;

import by.horsego.bean.Horse;
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
import java.util.Map;

/**
 * Implementation user command to show horses.
 *
 * Implements {@link Command} interface.
 * To get an instance of a class you need to call the method {@link CommandProvider#getCommand(String)}
 * and pass a string "SHOW_HORSES" as an argument in any register.
 *
 * @see Command
 * @see CommandProvider
 * @see CommandProvider#getCommand(String)
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class ShowHorsesCommand implements Command {

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private HorseService horseService = serviceFactory.getHorseService();
    private static final Logger logger = Logger.getLogger(ShowHorsesCommand.class);

    /**
     * This method get horses.
     *
     * Apply {@link HorseService} object and call {@link HorseService#getHorsesForTable()} method
     * Then using {@link PagesManager} class call the {@link PagesManager#getProperty(String)} method
     * to get the address of the page that will be sent in response to the user (bets-history).
     *
     * @see HorseService
     * @see HorseService#getHorsesForTable()
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

        String page = request.getServletPath();

        Map<Horse, List<Integer>> horses = null;
        try {
            horses = horseService.getHorsesForTable();
        } catch (ServiceException e) {
            logger.error(e);
            throw new CommandException(e);
        }

        HttpSession session = request.getSession();
        Locale locale = new Locale((String) session.getAttribute("local"));

        if(horses == null || horses.isEmpty()){
            request.setAttribute("horsesTableMessage", MessagesManager.getProperty("horsesTableMessage", locale));
        } else {
            request.setAttribute("horsesMap", horses);
        }

        return page;
    }
}
