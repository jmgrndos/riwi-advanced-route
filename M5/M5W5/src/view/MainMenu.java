package view;

import javax.swing.*;

public class MainMenu {

    public static void showMainMenu()  {

        // Declare menu options
        String[] options = {"Room Menu", "Booking Menu", "Exit"};

        // Define options dialog
        while (true) {

            // Show options dialog
            int option = JOptionPane.showOptionDialog(null, "Main Menu", "GDR Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            // Handle close button and exit option
            if(option == -1 || option == 2) break;

            // Handle user selection
            switch (option) {
                case 0 -> RoomView.showRoomMenu();
                case 1 -> BookingView.showBookingMenu();
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }


}
