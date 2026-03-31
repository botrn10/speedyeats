package vn.edu.ueh.speedyeats.Util;

import java.util.List;

import vn.edu.ueh.speedyeats.Model.Product;

public class CartValidator {

    // Giỏ hàng có sản phẩm không
    public static boolean isCartNotEmpty(List<Product> list) {
        return list != null && list.size() > 0;
    }

    // Validate thông tin người dùng
    public static boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isAddressValid(String address) {
        return address != null && !address.trim().isEmpty();
    }

    public static boolean isPhoneValid(String phone) {
        return phone != null && !phone.trim().isEmpty();
    }

    // Tính tổng tiền (test được)
    public static int calculateTotalPrice(List<Product> list) {
        int total = 0;
        if (list == null) return total;

        for (Product p : list) {
            total += p.getGiatien() * p.getSoluong();
        }
        return total;
    }

    // Tổng tiền cuối (có phí ship)
    public static int calculateFinalPrice(int productTotal, int shippingFee) {
        return productTotal + shippingFee;
    }
}