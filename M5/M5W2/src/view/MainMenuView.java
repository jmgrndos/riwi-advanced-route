package view;

import javax.swing.*;

public class MainMenuView {

    // Show main menu
    public static int showMainMenu() {

        // Initialize menu options
        Object[] options = {"Add a product", "List products", "Buy product", "Show stats", "Search product", "Leave"};

        // Retrieve selected option
        return JOptionPane.showOptionDialog(
                null,
                "Select an option: ",
                "Main menu",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

    }

    // Confirm leave menu
    public static boolean confirmLeave() {

        // Print confirm message
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to leave?",
                "Confirm Leave",
                JOptionPane.YES_NO_OPTION
        );

        // Retrieve selected opiton
        return confirm == JOptionPane.YES_OPTION;
    }

    // Print bought products summary
    public static void showSummary (int articlesBought, double totalSpent){

        // Check if there are articles bought
        if (articlesBought > 0){

            // Print summary
            InventoryView.infoMessage(String.format("Articles bought: %d. \nTotal spent: %.2f$.", articlesBought, totalSpent), "Summary");

        } else {

            // Print info message
            InventoryView.infoMessage("No articles bought.", "Summary");

        }
    }
}
