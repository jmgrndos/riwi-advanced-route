package view;

import javax.swing.JOptionPane;

public class PartnerView {

    // Enum for user menu options
    public enum UserMenuOption {
        VIEW_LOANS,
        MAKE_LOAN,
        RETURN_LOAN,
        LOG_OUT
    }

    // Show user menu
    public UserMenuOption showUserMenu(String userName) {

        // Define menu options
        String[] options = {"View My Loans", "Make a Loan", "Return Loan", "Log Out"};

        // Create a title for the dialog
        String title = "Partner Menu - Welcome, " + userName;

        // Show the options dialog
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select an option:",
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Handle user choice
        return switch (choice) {
            case 0 -> UserMenuOption.VIEW_LOANS;
            case 1 -> UserMenuOption.MAKE_LOAN;
            case 2 -> UserMenuOption.RETURN_LOAN;
            default -> UserMenuOption.LOG_OUT;
        };
    }

    // Get book id input
    public String getBookIdInput(String title) {
        return JOptionPane.showInputDialog(
                null,
                "Enter the ID of the book you want to loan:",
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Get loan id input
    public String getLoanIdInput(String title) {
        return JOptionPane.showInputDialog(
                null,
                "Enter the ID of the loan you want to return:",
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Show success message
    public void showSuccessMessage(String title, String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Show all loans
    public void showLoans(String title, String loans) {
        JOptionPane.showMessageDialog(
                null,
                loans,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Show error message
    public void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(
                null,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

}
