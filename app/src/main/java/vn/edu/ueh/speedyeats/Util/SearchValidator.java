package vn.edu.ueh.speedyeats.Util;

public class SearchValidator {

    // Check text search hợp lệ
    public static boolean isValidQuery(String query) {
        return query != null && !query.trim().isEmpty();
    }

    // Clean text (tránh space thừa)
    public static String normalizeQuery(String query) {
        if (query == null) return "";
        return query.trim();
    }

    // Check voice result
    public static boolean isVoiceResultValid(java.util.ArrayList<String> result) {
        return result != null && result.size() > 0;
    }
}