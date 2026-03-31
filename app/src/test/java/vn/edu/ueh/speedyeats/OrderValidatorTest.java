package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.Util.OrderValidator;

public class OrderValidatorTest {
    @Test
    public void testParseMoney() {
        assertEquals(10000, OrderValidator.parseMoney("10,000"));
        assertEquals(0, OrderValidator.parseMoney(null));
    }

    @Test
    public void testCalculateTotal() {
        List<Product> list = new ArrayList<>();

        Product p = new Product();
        p.setGiatien(100);
        p.setSoluong(2);

        list.add(p);

        assertEquals(200, OrderValidator.calculateProductTotal(list));
    }
}
