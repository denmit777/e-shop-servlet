package service.impl;

import dbase.OrdersBase;
import model.Order;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.OrderService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class.getName());

    private static final String ORDER_NOT_PLACED = "order not placed yet";
    private static final String RESULT_ORDER = "your order:";

    private final UserServiceImpl userService = new UserServiceImpl();

    @Override
    public Order save(HttpSession session, BigDecimal totalPrice) {
        Order order = new Order();

        Long userId = (Long) session.getAttribute("userId");
        User user = userService.getById(userId);

        order.setId(getAll().size() + 1L);
        order.setUserId(user.getId());
        order.setTotalPrice(totalPrice);

        if (!totalPrice.equals(BigDecimal.valueOf(0))) {
            OrdersBase base = OrdersBase.getInstance();
            base.addOrder(order);

            LOGGER.info("New order: " + order);
        }

        return order;
    }

    @Override
    public List<Order> getAll() {
        OrdersBase base = OrdersBase.getInstance();

        return base.getAllOrders();
    }

    @Override
    public String orderResult(BigDecimal totalPrice) {
        if (totalPrice.equals(BigDecimal.valueOf(0))) {

            LOGGER.info(ORDER_NOT_PLACED);

            return ORDER_NOT_PLACED;
        }

        return RESULT_ORDER;
    }
}
