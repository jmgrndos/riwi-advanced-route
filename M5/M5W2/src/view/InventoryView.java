package view;

import javax.swing.*;

public class InventoryView {

    // Information message template
    public static void infoMessage(String message, String title) {

        // Print info message
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);

    }

    // Error message template
    public static void errorMessage(String message, String title) {

        // Print error message
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);

    }

    // Input message template
    public static String askForInput(String message, String title) {

        // Print input message
        return JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);

    }

    // Product type select
    public static int typeInput() {

        // Product type
        Object[] optionTypes = {"Food", "Appliance", "Cancel"};

        // Ask the user to prompt the product type
        return JOptionPane.showOptionDialog(
                null,
                "Select type of product: ",
                "Add product",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionTypes,
                optionTypes[0]);

    }

    // Print product details
    public static void showProductDetails(String productName, double price, int amount, String title) {

        // Print product details
        infoMessage(String.format("Product: %s - Price: $%.2f. - Stock: %d units\n", productName, price, amount), title);
    }

}
