package util;

import java.util.regex.Pattern;

public class InputValidator {

    // Define patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Checks if the input is null or empty after trimming
    public static boolean isEmptyInput(Object input) {
        if (input == null) return true;
        String str = input.toString().trim();
        return str.isEmpty();
    }

    // Checks if the input is a valid email address
    public static boolean isInvalidEmail(String input) {

        // Validate against email pattern
        return !EMAIL_PATTERN.matcher(input.trim()).matches();

    }

    // Checks if the input is a valid integer greater than 0
    public static boolean isInvalidInteger(String input) {
        try {
            // Parse value and verify if it's greater than 0
            int value = Integer.parseInt(input.trim());
            return value <= 0;

        } catch (NumberFormatException e) {
            return true;
        }
    }


}