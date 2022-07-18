package service.impl;

import dbase.GoodsBase;
import model.Good;
import service.GoodService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class GoodServiceImpl implements GoodService {
    private static final String REGEX_LETTERS_FIGURES_POINT = "[^A-Za-z0-9.]";

    @Override
    public Good getByTitleAndPrice(String title, String price) {
        return getAll().stream()
                .filter(good -> title.equals(good.getTitle())
                        && price.equals(String.valueOf(good.getPrice())))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException(String.format("Good with title %s and price %s not found", title, price)));
    }

    @Override
    public List<Good> getAll() {
        GoodsBase base = GoodsBase.getInstance();

        return base.getAllGoods();
    }

    @Override
    public String getStringOfOptionsForDroppingMenuFromGoodList(List<Good> goods) {
        return goods.stream()
                .map(good -> "<option>" + good.getTitle() + " (" + good.getPrice() + ") </option>\n")
                .collect(Collectors.joining());
    }

    @Override
    public String getChoice(String chosenGoods) {
        if (chosenGoods != null) {
            return chosenGoods;
        }

        return "Make your order\n";
    }

    @Override
    public String getStringOfNameAndPriceFromOptionMenu(String s) {
        return s.replaceAll(REGEX_LETTERS_FIGURES_POINT, "");
    }
}
