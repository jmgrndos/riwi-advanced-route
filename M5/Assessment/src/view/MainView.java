package view;

import javax.swing.*;

public class MainView {

    // Enum for main menu options
    public enum MainMenuOption {
        REGISTER,
        LOG_IN,
        EXIT
    }

    // Shows the main menu and returns the selected option
    public MainMenuOption showMainMenu() {

        // Define menu options
        String[] options = {"Register", "Log In", "Exit"};

        // Show the options dialog
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select a menu:",
                "Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Handle user choice
        return switch (choice) {
            case 0 -> MainMenuOption.REGISTER;
            case 1 -> MainMenuOption.LOG_IN;
            default -> MainMenuOption.EXIT;
        };
    }
}
