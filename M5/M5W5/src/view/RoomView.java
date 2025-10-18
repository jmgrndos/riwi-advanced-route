package view;

import controller.RoomControllerImpl;
import domain.Room;
import service.RoomServiceImpl;
import util.ExceptionMapper;
import util.InputValidator;

import javax.swing.*;
import java.util.List;

public class RoomView {

    // Initialize RoomController
    static RoomControllerImpl controller = new RoomControllerImpl(new RoomServiceImpl());

    // Room menu
    public static void showRoomMenu() {

        // Define options
        String[] options = {"Add room", "See all rooms", "Go back", "Exit"};

        // Loop until user exits
        while (true) {

            // Show options dialog
            int option = JOptionPane.showOptionDialog(null, "Welcome to the rooms menu!, select an option:", "Rooms Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            // Handle close button and exit option
            if (option == -1 || option == 3) return;

            // Handle user selection
            switch (option) {
                case 0 -> createRoomMenu();
                case 1 -> showAllRooms();
                case 2 -> view.MainMenu.showMainMenu();
                default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }

    }

    // Menu to create new room
    public static void createRoomMenu() {

        // Introduction
        JOptionPane.showMessageDialog(null, "Use following convention for the room ID:\nF1R1\nF1 = Floor number.\nR1 = Room number.", "Create Room", JOptionPane.INFORMATION_MESSAGE);

        // Loop until room is created or user cancels
        while (true) {

            // Prompt for room details
            String roomId = JOptionPane.showInputDialog(null, "Enter room ID (F1R1):", "Create Room", JOptionPane.QUESTION_MESSAGE);

            // Handle cancel button
            if (roomId == null) break;

            // Validate room id input
            if (InputValidator.isEmptyInput(roomId)) {
                JOptionPane.showMessageDialog(null, "Room ID cannot be empty.", "Create Room", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Validate room id format
            if (InputValidator.isInvalidId(roomId.trim().toUpperCase())) {
                JOptionPane.showMessageDialog(null, "Invalid room ID format. Please use the format F1R1.", "Create Room", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            try {

                // Call controller to create room
                boolean success = controller.createRoom(roomId.trim().toUpperCase());

                // If operation is not successful
                if (!success) {
                    JOptionPane.showMessageDialog(null, "Room creation failed. Room may already exist.", "Create Room", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Display success message
                JOptionPane.showMessageDialog(null, "Room created successfully!", "Create Room", JOptionPane.INFORMATION_MESSAGE);
                break;

            } catch (Throwable e) {
                handleException(e, "Create Room");
            }
        }
    }

    // Menu to show all rooms
    public static void showAllRooms() {
        try {
            // Call controller to get all rooms
            List<Room> rooms = controller.getAllRooms();

            // If list is null, show error message
            if (rooms == null) {
                JOptionPane.showMessageDialog(null, "No rooms added.", "Show all Rooms", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Build string to display all rooms
            StringBuilder roomsStr = new StringBuilder();
            for (Room room : rooms) {
                roomsStr.append("Room: ").append(room.getRoomId()).append("\n");
            }

            // Display all rooms
            JOptionPane.showMessageDialog(null, roomsStr, "Show all Rooms", JOptionPane.INFORMATION_MESSAGE);

        } catch (Throwable e) {
            handleException(e, "Show All Rooms");
        }
    }

    // Exception mapper
    private static void handleException(Throwable e, String contextMessage) {
        int statusCode = ExceptionMapper.getHttpStatusCode(e);
        String errorMessage = switch (statusCode) {
            case 400 -> "Bad Request: " + e.getMessage();
            case 404 -> "Not Found: " + e.getMessage();
            case 409 -> "Conflict: " + e.getMessage();
            case 500 -> "Internal Error: " + e.getMessage();
            default -> "Unexpected Error: " + e.getMessage();
        };
        JOptionPane.showMessageDialog(null, errorMessage, contextMessage, JOptionPane.ERROR_MESSAGE);
    }
}
