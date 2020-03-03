package by.horsego.filter;

import by.horsego.properties_manager.PagesManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * A filter to restrict access to the pages of an authorized user.
 *
 * The class implements the base interface {@link Filter}, checks whether the user is authorized.
 * If the user is not authorized (null), redirect it to the login page.
 *
 * @see Filter
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        if (session.getAttribute("user") == null){
            response.sendRedirect(request.getContextPath() + PagesManager.getProperty("log-in"));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
