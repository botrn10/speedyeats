package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.Util.AdminProductValidator;

public class AdminProductValidatorTest {

    @Test
    public void testFilterByCategory() {
        List<Product> list = new ArrayList<>();

        list.add(new Product("1", "A", 100L, "", "Food", "", 1L, "", 1L, ""));
        list.add(new Product("2", "B", 200L, "", "Drink", "", 1L, "", 1L, ""));

        List<Product> result = AdminProductValidator.filterByCategory(list, "Food");

        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getTensp());
    }

    @Test
    public void testValidProduct() {
        Product p = new Product("1", "A", 100L, "", "Food", "", 1L, "", 1L, "");

        assertTrue(AdminProductValidator.isValidProduct(p));
    }
}