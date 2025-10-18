package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class InputValidator {

    // Define patterns
    private static final Pattern ID_PATTERN = Pattern.compile("^F\\d+R\\d+$");
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    private static final Pattern TIME_PATTERN = Pattern.compile("^([01]?\\d|2[0-3]):[0-5]\\d$");

    // Is valid room ID
    public static boolean isInvalidId(String input) {
        return !ID_PATTERN.matcher(input).matches();
    }

    // Checks if the input is null or empty after trimming
    public static boolean isEmptyInput(Object input) {
        if (input == null) return true;
        String str = input.toString().trim();
        return str.isEmpty();
    }

    // Checks if the input is a valid date in yyyy-MM-dd format
    public static boolean isValidDate(String input) {

        // Check if input follows the date format
        if (!DATE_PATTERN.matcher(input.trim()).matches()) {
            return false;
        }

        // Ensure date it's a valid calendar date
        try {
            LocalDate.parse(input.trim(), DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    // Checks if the input is a valid time in HH:mm format
    public static boolean isInvalidValidTime(String input) {

        // Check if input follows the time format
        if (!TIME_PATTERN.matcher(input.trim()).matches()) {
            return true;
        }

        // Check if it's a valid time
        try {
            java.time.LocalTime.parse(input.trim());
            return false;
        } catch (java.time.format.DateTimeParseException e) {
            return true;
        }
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