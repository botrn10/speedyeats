package vn.edu.ueh.speedyeats.Util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import vn.edu.ueh.speedyeats.Model.Product;

public class OrderValidator {

    public static boolean isValidString(String s) {
        return s != null && !s.trim().isEmpty();
    }

    public static int parseMoney(String money) {
        try {
            Number number = NumberFormat.getInstance().parse(money);
            return Integer.parseInt(String.valueOf(number));
        } catch (Exception e) {
            return 0;
        }
    }

    public static int calculateProductTotal(List<Product> list) {
        int total = 0;
        if (list == null) return total;

        for (Product p : list) {
            total += p.getGiatien() * p.getSoluong();
        }
        return total;
    }

    public static boolean isListValid(List<Product> list) {
        return list != null && list.size() > 0;
    }
}