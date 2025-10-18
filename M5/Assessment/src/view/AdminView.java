package view;

import javax.swing.JOptionPane;

public class AdminView {

    // Enum for admin menu options
    public enum AdminMenuOption {
        VIEW_BOOKS,
        CREATE_BOOK,
        UPDATE_BOOK_STOCK,
        VIEW_ALL_LOANS,
        UPDATE_PARTNER_STATUS,
        EXPORT_ALL_BOOKS,
        EXPORT_ALL_OVERDUE_LOANS,
        LOG_OUT
    }

    // Show admin menu
    public AdminMenuOption showAdminMenu(String userName) {

        // Define menu options
        String[] options = {"View all books", "Create book", "Update book stock", "View all loans", "Update partner status", "Export all books", "Export all overdue loans", "Log Out"};
        String title = "Admin Menu - Welcome, " + userName;

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
            case 0 -> AdminMenuOption.VIEW_BOOKS;
            case 1 -> AdminMenuOption.CREATE_BOOK;
            case 2 -> AdminMenuOption.UPDATE_BOOK_STOCK;
            case 3 -> AdminMenuOption.VIEW_ALL_LOANS;
            case 4 -> AdminMenuOption.UPDATE_PARTNER_STATUS;
            case 5 -> AdminMenuOption.EXPORT_ALL_BOOKS;
            case 6 -> AdminMenuOption.EXPORT_ALL_OVERDUE_LOANS;
            default -> AdminMenuOption.LOG_OUT;
        };
    }

    // Get title input
    public String getTitleInput(String title) {
        return JOptionPane.showInputDialog(
                null,
                "Enter book title:",
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Get book id input
    public String getBookIdInput(String title) {
        return JOptionPane.showInputDialog(
                null,
                "Enter book Id:",
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Get stock input
    public String getStockInput(String title) {
        return JOptionPane.showInputDialog(
                null,
                "Enter book stock:",
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Get user id input
    public String getUserIdInput(String title) {
        return JOptionPane.showInputDialog(
                null,
                "Enter user ID:",
                title,
                JOptionPane.QUESTION_MESSAGE
        );
    }

    // Get partner status input
    public boolean getPartnerStatusInput(String title) {

        // Define options
        String[] options = {"Active", "Inactive"};

        // Show the options dialog
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select partner status:",
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Handle user choice
        return choice == 0;
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
