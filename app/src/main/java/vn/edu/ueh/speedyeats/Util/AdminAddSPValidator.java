package vn.edu.ueh.speedyeats.Util;

public class AdminAddSPValidator {
    public static boolean validateInput(
            String image,
            String ten,
            String gia,
            String hansudung,
            String trongluong,
            String soluong,
            String type,
            String mota
    ) {
        return !isEmpty(image)
                && !isEmpty(ten)
                && !isEmpty(gia)
                && !isEmpty(hansudung)
                && !isEmpty(trongluong)
                && !isEmpty(soluong)
                && !isEmpty(type)
                && !isEmpty(mota);
    }

    public static boolean isValidNumber(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
