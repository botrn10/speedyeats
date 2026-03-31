package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import vn.edu.ueh.speedyeats.Util.DetailValidator;

public class DetailValidatorTest {

    @Test
    public void testQuantityValid() {
        assertTrue(DetailValidator.isQuantityValid(1));
        assertFalse(DetailValidator.isQuantityValid(0));
    }

    @Test
    public void testCanDecrease() {
        assertTrue(DetailValidator.canDecrease(2));
        assertFalse(DetailValidator.canDecrease(1));
    }

    @Test
    public void testCanIncrease() {
        assertTrue(DetailValidator.canIncrease(1, 10));
        assertFalse(DetailValidator.canIncrease(10, 10));
    }

    @Test
    public void testProductValid() {
        assertTrue(DetailValidator.isProductValid("abc"));
        assertFalse(DetailValidator.isProductValid(""));
        assertFalse(DetailValidator.isProductValid(null));
    }
}