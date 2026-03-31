package vn.edu.ueh.speedyeats;

import org.junit.Test;
import static org.junit.Assert.*;

import vn.edu.ueh.speedyeats.Util.SignUpValidator;

public class SignUpValidatorTest {

    @Test
    public void testValidEmail() {
        assertTrue(SignUpValidator.isEmailValid("test@gmail.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(SignUpValidator.isEmailValid("abc"));
    }

    @Test
    public void testPasswordValid() {
        assertTrue(SignUpValidator.isPasswordValid("123456"));
    }

    @Test
    public void testPasswordInvalid() {
        assertFalse(SignUpValidator.isPasswordValid("123"));
    }

    @Test
    public void testConfirmPasswordMatch() {
        assertTrue(SignUpValidator.isConfirmPasswordMatch("123456", "123456"));
    }

    @Test
    public void testConfirmPasswordNotMatch() {
        assertFalse(SignUpValidator.isConfirmPasswordMatch("123", "456"));
    }

    @Test
    public void testValidSignUp() {
        assertTrue(SignUpValidator.isValidSignUp("test@gmail.com", "123456", "123456"));
    }
}