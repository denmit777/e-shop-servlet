package service.impl;

import model.Cart;
import model.Good;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CartService;
import service.GoodService;
import service.UserService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class CartServiceImpl implements CartService {
    private static final Logger LOGGER = LogManager.getLogger(CartServiceImpl.class.getName());

    private static final String REGEX_ONLY_LETTERS = "[^A-Za-z]";
    private static final String REGEX_ONLY_FIGURES = "[A-Za-z]";

    private final UserService userService = new UserServiceImpl();
    private final GoodService goodService = new GoodServiceImpl();
    private Cart cart;

    @Override
    public void getCart(HttpSession session) {
        if (session.getAttribute("cart") != null) {
            cart = (Cart) session.getAttribute("cart");
        }
    }

    @Override
    public void addGoodToCart(String option, Long id, HttpSession session) {
        String login = (String) session.getAttribute("login");

        User user = userService.getById(id);

        user.setLogin(login);

        Good good = getGoodFromOption(option);

        cart.addGood(new Good(good.getId(), good.getTitle(), good.getPrice()));
        cart.setUser(user);

        LOGGER.info("New cart: " + cart);
    }

    @Override
    public void deleteGoodFromCart(String option) {
        Good good = getGoodFromOption(option);

        cart.deleteGood(new Good(good.getId(), good.getTitle(), good.getPrice()));

        LOGGER.info("Cart after removing " + good.getTitle() + ": " + cart);
    }

    @Override
    public void updateData(HttpSession session) {
        cart = new Cart();
        cart.deleteGoods();

        session.setAttribute("cart", cart);

        String chosenGoods = "Make your order\n";
        session.setAttribute("chosenGoods", chosenGoods);
    }

    @Override
    public String printChosenGoods(Cart cart) {
        if (cart.getGoods().isEmpty()) {
            return "Make your order\n";
        }

        StringBuilder sb = new StringBuilder("You have already chosen:\n");

        int count = 1;

        for (Good el : cart.getGoods()) {
            sb.append(count)
                    .append(") ")
                    .append(el.getTitle())
                    .append(" ")
                    .append(el.getPrice())
                    .append(" $\n");
            count++;
        }

        return sb.toString();
    }

    @Override
    public String printOrder(Cart cart) {
        if (cart.getGoods().isEmpty()) {
            return "";
        }

        return printChosenGoods(cart).replace("You have already chosen:\n", "");
    }

    @Override
    public BigDecimal getTotalPrice(Cart cart) {
        BigDecimal count = BigDecimal.valueOf(0);

        for (Good good : cart.getGoods()) {
            count = count.add(good.getPrice());
        }

        LOGGER.info("Total price: " + count);

        return count;
    }

    private Good getGoodFromOption(String option) {
        String name = option.replaceAll(REGEX_ONLY_LETTERS, "");
        String price = option.replaceAll(REGEX_ONLY_FIGURES, "");

        return goodService.getByTitleAndPrice(name, price);
    }
}
