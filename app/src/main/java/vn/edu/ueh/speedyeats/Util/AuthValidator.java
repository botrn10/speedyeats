package vn.edu.ueh.speedyeats.utils;

public class AuthValidator {

    public static boolean isEmailEmpty(String email) {
        return email == null || email.trim().isEmpty();
    }

    public static boolean isPasswordEmpty(String password) {
        return password == null || password.trim().isEmpty();
    }

    public static boolean isValidLoginInput(String email, String password) {
        return !isEmailEmpty(email) && !isPasswordEmpty(password);
    }

    public static boolean isAdmin(String email, String password) {
        return email.equals("admin") && password.equals("admin123");
    }
}