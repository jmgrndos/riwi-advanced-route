package utils;

import javax.swing.*;

public class InputValidator {

    // String input validation
    public static Boolean isValidString(String input) {

        // Return true if string is not empty
        return !input.trim().isEmpty();

    }

    // Double input validation
    public static Boolean isValidDouble(String input) {

        // Check data type
        try {

            // Return true if parsed correctly and greater than 0
            if (Double.parseDouble(input) > 0) {
                return true;
            }

        } catch (NumberFormatException e) {

            return false;

        }

        return false;

    }

    // Integer input validation
    public static boolean isValidInteger(String input) {

        // Check data type
        try {

            // Return true if parsed correctly and greater than 0
            if (Integer.parseInt(input.trim()) > 0) {

                return true;

            }

        } catch (NumberFormatException e) {

            return false;

        }

        return false;

    }

}
