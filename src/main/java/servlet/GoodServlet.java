package servlet;

import model.Cart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.Pages;
import service.CartService;
import service.GoodService;
import service.OrderService;
import service.impl.CartServiceImpl;
import service.impl.GoodServiceImpl;
import service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

@WebServlet("/goods")
public class GoodServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(GoodServlet.class.getName());

    private GoodService goodService;
    private CartService cartService;
    private OrderService orderService;
    private Cart cart;

    public void init() throws ServletException {
        goodService = new GoodServiceImpl();
        cartService = new CartServiceImpl();
        orderService = new OrderServiceImpl();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();

        try (PrintWriter writer = response.getWriter()) {
            Pages.goodPage(writer, session);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        createCart(session);

        String command = request.getParameter("submit");

        clickingActions(command, request, response, session);

        doGet(request, response);
    }

    private void createCart(HttpSession session) {
        cartService.getCart(session);

        if (session.getAttribute("cart") == null) {
            cart = new Cart();
        } else {
            cart = (Cart) session.getAttribute("cart");
        }
    }

    private void clickingActions(String command, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        BigDecimal totalPrice = cartService.getTotalPrice(cart);

        session.setAttribute("cart", cart);
        session.setAttribute("totalPrice", totalPrice);

        String option = goodService.getStringOfNameAndPriceFromOptionMenu(request.getParameter("goodName"));

        switch (command) {
            case "Add Good":
                cartService.addGoodToCart(option, (Long) session.getAttribute("userId"), session);

                getChosenGoods(session);

                break;
            case "Remove Good":
                cartService.deleteGoodFromCart(option);

                getChosenGoods(session);

                break;
            case "Submit":
                String order = cartService.printOrder(cart);
                session.setAttribute("order", order);

                orderService.save(session, totalPrice);

                response.sendRedirect("order");

                break;
            case "Log out":
                response.sendRedirect("e-shop");

                break;
        }
    }

    private void getChosenGoods(HttpSession session) {
        String chosenGoods = cartService.printChosenGoods(cart);

        LOGGER.info("Chosen goods: " + chosenGoods);

        session.setAttribute("chosenGoods", chosenGoods);
    }
}
