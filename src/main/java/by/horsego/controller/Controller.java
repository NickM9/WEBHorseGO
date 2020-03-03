package by.horsego.controller;

import by.horsego.command.Command;
import by.horsego.command.CommandException;
import by.horsego.command.CommandProvider;
import by.horsego.properties_manager.PagesManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final Logger logger = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String stringCommand = null;

        if (request.getPathInfo() != null){
            stringCommand = request.getPathInfo();
            stringCommand = stringCommand.substring(1);
            logger.info(stringCommand);
        }

        Command command = null;
        String page = null;
        try {
            command = CommandProvider.getCommand(stringCommand);
            page = command.execute(request);
        } catch (CommandException e) {
            errorRedirect(response);
        }

        if (page == null){
            page = PagesManager.getProperty("index");
            response.sendRedirect(request.getContextPath() + page);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stringCommand = request.getParameter("command");

        Command command = null;
        String page = null;
        try {
            command = CommandProvider.getCommand(stringCommand);
            page = command.execute(request);
        } catch (CommandException e) {
            errorRedirect(response);
        }

        if (page == null) {
            page = PagesManager.getProperty("index");
        }
        response.sendRedirect(request.getContextPath() + page);
    	
    }

    private void errorRedirect(HttpServletResponse response) throws IOException {
        String page = PagesManager.getProperty("error");

        response.sendError(500);
        response.sendRedirect(page);
    }
}
