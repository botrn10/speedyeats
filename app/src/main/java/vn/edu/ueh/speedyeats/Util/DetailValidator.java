package vn.edu.ueh.speedyeats.Util;

public class DetailValidator {

    // Kiểm tra số lượng hợp lệ
    public static boolean isQuantityValid(int quantity) {
        return quantity > 0;
    }

    // Có thể giảm tiếp không
    public static boolean canDecrease(int quantity) {
        return quantity > 1;
    }

    // Có thể tăng tiếp không (nếu muốn giới hạn theo kho)
    public static boolean canIncrease(int quantity, long maxStock) {
        return quantity < maxStock;
    }

    // Kiểm tra product hợp lệ
    public static boolean isProductValid(String productId) {
        return productId != null && !productId.isEmpty();
    }
}