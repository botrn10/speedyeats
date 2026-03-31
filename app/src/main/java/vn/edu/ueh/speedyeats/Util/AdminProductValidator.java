package vn.edu.ueh.speedyeats.Util;

import vn.edu.ueh.speedyeats.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class AdminProductValidator {

    // Filter theo category (local - không gọi Firebase)
    public static List<Product> filterByCategory(List<Product> list, String category) {
        List<Product> result = new ArrayList<>();

        if (category == null || category.equals("Tất cả")) {
            return list;
        }

        for (Product p : list) {
            if (p.getLoaisp() != null && p.getLoaisp().equals(category)) {
                result.add(p);
            }
        }

        return result;
    }

    // Check product null safe
    public static boolean isValidProduct(Product p) {
        if (p == null) return false;

        if (p.getTensp() == null || p.getTensp().trim().isEmpty()) return false;

        if (p.getGiatien() <= 0) return false;

        return true;
    }
}