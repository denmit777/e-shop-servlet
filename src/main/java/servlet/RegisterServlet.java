package servlet;

import model.User;
import pages.Pages;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserServiceImpl userService;

    public void init() throws ServletException {
        userService = new UserServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        try (PrintWriter writer = response.getWriter()) {
            Pages.registerPage(writer);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (userService.isInvalidUser(login, password)) {
            try (PrintWriter writer = response.getWriter()) {
                Pages.registerErrorPage(writer, request);
            }
        } else {
            User user = userService.save(login, password);

            HttpSession session = request.getSession();

            session.setAttribute("userId", user.getId());
            session.setAttribute("login", user.getLogin());

            try (PrintWriter writer = response.getWriter()) {
                Pages.successPage(writer, session);
            }
        }

        doGet(request, response);
    }
}
