package view;


import javax.swing.*;

public class MenuMessage {

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

}
