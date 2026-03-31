package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import vn.edu.ueh.speedyeats.Util.AuthValidator;

public class AuthValidatorTest {

    @Test
    public void testEmailEmpty() {
        assertTrue(AuthValidator.isEmailEmpty(""));
    }

    @Test
    public void testEmailNotEmpty() {
        assertFalse(AuthValidator.isEmailEmpty("user@gmail.com"));
    }

    @Test
    public void testPasswordEmpty() {
        assertTrue(AuthValidator.isPasswordEmpty(""));
    }

    @Test
    public void testValidLoginInput() {
        assertTrue(AuthValidator.isValidLoginInput("user@gmail.com", "123456"));
    }

    @Test
    public void testInvalidLoginInput() {
        assertFalse(AuthValidator.isValidLoginInput("", "123456"));
    }

    @Test
    public void testAdminLogin() {
        assertTrue(AuthValidator.isAdmin("admin", "admin123"));
    }

    @Test
    public void testNotAdminLogin() {
        assertFalse(AuthValidator.isAdmin("user", "123"));
    }
}