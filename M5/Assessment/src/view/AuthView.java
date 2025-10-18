package view;

import javax.swing.*;

public class AuthView {

    public String getEmailInput(String title) {
        return JOptionPane.showInputDialog(null, "Enter user email:", title, JOptionPane.QUESTION_MESSAGE);
    }

    public String getPasswordInput(String title) {

        // Create a password field
        JPasswordField passwordField = new JPasswordField();

        // Show the dialog with the password field
        int option = JOptionPane.showConfirmDialog(null, passwordField, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // Handle user input
        if (option == JOptionPane.OK_OPTION) {
            return new String(passwordField.getPassword());
        }

        // User cancelled
        return null;
    }

    public String getNameInput(String title) {
        return JOptionPane.showInputDialog(null, "Enter user name:", title, JOptionPane.QUESTION_MESSAGE);
    }

    public void showErrorMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccessMessage(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
