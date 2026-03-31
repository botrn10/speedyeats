package vn.edu.ueh.speedyeats.Util;
public class AuthValidator {

    // Kiểm tra email rỗng
    public static boolean isEmailEmpty(String email) {
        return email == null || email.trim().isEmpty();
    }

    // Kiểm tra password rỗng
    public static boolean isPasswordEmpty(String password) {
        return password == null || password.trim().isEmpty();
    }

    // Check input hợp lệ
    public static boolean isValidLoginInput(String email, String password) {
        return !isEmailEmpty(email) && !isPasswordEmpty(password);
    }

    // Check admin
    public static boolean isAdmin(String email, String password) {
        return email.equals("admin") && password.equals("admin123");
    }
}