package vn.edu.ueh.speedyeats.Util;

import android.util.Patterns;

public class SignUpValidator {

    public static boolean isEmailValid(String email) {
        if (email == null) return false;

        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(regex);
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 6;
    }

    public static boolean isConfirmPasswordMatch(String pass, String confirm) {
        return pass.equals(confirm);
    }

    public static boolean isValidSignUp(String email, String pass, String confirm) {
        return isEmailValid(email)
                && isPasswordValid(pass)
                && isConfirmPasswordMatch(pass, confirm);
    }
}