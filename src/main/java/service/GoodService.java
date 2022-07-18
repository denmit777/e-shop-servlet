package service;

import model.Good;

import java.util.List;

public interface GoodService {

    Good getByTitleAndPrice(String title, String price);

    List<Good> getAll();

    String getStringOfOptionsForDroppingMenuFromGoodList(List<Good> goods);

    String getChoice(String chosenGoods);

    String getStringOfNameAndPriceFromOptionMenu(String s);
}
