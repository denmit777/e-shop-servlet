package servlet;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.Pages;
import service.CartService;
import service.impl.CartServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/e-shop")
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class.getName());

    private UserServiceImpl userService;
    private CartService cartService;

    public void init() throws ServletException {
        userService = new UserServiceImpl();
        cartService = new CartServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try (PrintWriter writer = response.getWriter()) {
            Pages.loginPage(writer);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        User user = userService.getByLoginAndPassword(login, password);

        LOGGER.info(user);

        cartService.updateData(session);

        clickingActions(request, response, user);

        doGet(request, response);
    }

    private void clickingActions(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
        if (request.getParameter("submit").equals("Enter") && !
                user.equals(new User("Unknown"))) {
            eventsWithCheckbox(request, response);

            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("login", user.getLogin());

            response.sendRedirect("goods");
        } else {
            eventsWithCheckbox(request, response);

            response.sendRedirect("register");
        }
    }

    private void eventsWithCheckbox(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("isUserCheck") == null) {
            try (PrintWriter writer = response.getWriter()) {
                Pages.errorPage(writer);
            }
        }
    }
}
