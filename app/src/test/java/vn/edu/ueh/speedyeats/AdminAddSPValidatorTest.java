package vn.edu.ueh.speedyeats;
import org.junit.Test;
import static org.junit.Assert.*;

import vn.edu.ueh.speedyeats.Util.AdminAddSPValidator;

public class AdminAddSPValidatorTest {

    @Test
    public void testValidInput() {
        boolean result = AdminAddSPValidator.validateInput(
                "img", "ten", "1000", "2025", "1kg", "10", "1", "mota"
        );
        assertTrue(result);
    }

    @Test
    public void testEmptyInput() {
        boolean result = AdminAddSPValidator.validateInput(
                "", "ten", "1000", "2025", "1kg", "10", "1", "mota"
        );
        assertFalse(result);
    }

    @Test
    public void testInvalidNumber() {
        boolean result = AdminAddSPValidator.isValidNumber("abc");
        assertFalse(result);
    }

    @Test
    public void testValidNumber() {
        boolean result = AdminAddSPValidator.isValidNumber("123");
        assertTrue(result);
    }
}