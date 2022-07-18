package dbase;

import model.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrdersBase {

    private static final OrdersBase instance = new OrdersBase();

    private final List<Order> orders;

    private OrdersBase() {
        orders = new ArrayList<>();

        Order order1 = new Order(1L, 1L, BigDecimal.valueOf(75));
        Order order2 = new Order(2L, 3L, BigDecimal.valueOf(134.56));
        Order order3 = new Order(3L, 1L, BigDecimal.valueOf(54));
        Order order4 = new Order(4L, 2L, BigDecimal.valueOf(175));
        Order order5 = new Order(5L, 1L, BigDecimal.valueOf(25));
        Order order6 = new Order(6L, 5L, BigDecimal.valueOf(63));
        Order order7 = new Order(7L, 4L, BigDecimal.valueOf(88));

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);
        orders.add(order6);
        orders.add(order7);
    }

    public static OrdersBase getInstance() {
        return instance;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}
