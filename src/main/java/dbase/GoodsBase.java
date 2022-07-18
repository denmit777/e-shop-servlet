package dbase;

import model.Good;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GoodsBase {

    private static final GoodsBase instance = new GoodsBase();

    private final List<Good> goods;

    private GoodsBase() {
        goods = new ArrayList<>();

        Good good1 = new Good(1L, "Book", BigDecimal.valueOf(5.5));
        Good good2 = new Good(2L, "Phone", BigDecimal.valueOf(100));
        Good good3 = new Good(3L, "Juice", BigDecimal.valueOf(2));
        Good good4 = new Good(4L, "Phone", BigDecimal.valueOf(200));
        Good good5 = new Good(5L, "Beer", BigDecimal.valueOf(1.5));
        Good good6 = new Good(6L, "Computer", BigDecimal.valueOf(500));
        Good good7 = new Good(7L, "Book", BigDecimal.valueOf(4.2));

        goods.add(good1);
        goods.add(good2);
        goods.add(good3);
        goods.add(good4);
        goods.add(good5);
        goods.add(good6);
        goods.add(good7);
    }

    public static GoodsBase getInstance() {
        return instance;
    }

    public List<Good> getAllGoods() {
        return goods;
    }
}
