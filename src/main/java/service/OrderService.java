package service;

import model.Order;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order save(HttpSession session, BigDecimal totalPrice);

    List<Order> getAll();

    String orderResult(BigDecimal totalPrice);
}

