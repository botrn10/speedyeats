package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.Util.CartValidator;

public class CartValidatorTest {

    @Test
    public void testCartNotEmpty() {
        List<Product> list = new ArrayList<>();
        assertFalse(CartValidator.isCartNotEmpty(list));

        list.add(new Product());
        assertTrue(CartValidator.isCartNotEmpty(list));
    }

    @Test
    public void testValidateUserInfo() {
        assertFalse(CartValidator.isNameValid(""));
        assertTrue(CartValidator.isNameValid("Bao"));

        assertFalse(CartValidator.isAddressValid(""));
        assertTrue(CartValidator.isAddressValid("HCM"));

        assertFalse(CartValidator.isPhoneValid(""));
        assertTrue(CartValidator.isPhoneValid("0123456789"));
    }

    @Test
    public void testCalculateTotal() {
        List<Product> list = new ArrayList<>();

        Product p1 = new Product();
        p1.setGiatien(100);
        p1.setSoluong(2);

        Product p2 = new Product();
        p2.setGiatien(50);
        p2.setSoluong(1);

        list.add(p1);
        list.add(p2);

        int total = CartValidator.calculateTotalPrice(list);
        assertEquals(250, total);
    }

    @Test
    public void testFinalPrice() {
        int result = CartValidator.calculateFinalPrice(200, 10000);
        assertEquals(10200, result);
    }
}