package by.horsego.filter;

import by.horsego.bean.Role;
import by.horsego.bean.User;
import by.horsego.properties_manager.PagesManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter for restricting access to admin pages.
 *
 * The class implements the base interface {@link Filter}, checks the user role to restrict access
 * to the administrator pages. If the user is not an administrator, redirect it to the main page.
 *
 * @see Filter
 * @author Mikita Masukhranau
 * @version 1.0
 */

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !(user.getRole().equals(Role.ADMIN))) {
            response.sendRedirect(request.getContextPath() + PagesManager.getProperty("main"));
        }
        filterChain.doFilter(servletRequest, servletResponse);


    }

    @Override
    public void destroy() {

    }
}
