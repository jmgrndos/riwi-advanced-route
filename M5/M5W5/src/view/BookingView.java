package view;

import controller.BookingControllerImpl;
import domain.Booking;
import service.BookingServiceImpl;
import util.ExceptionMapper;
import util.InputValidator;

import javax.swing.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class BookingView {

    // Initialize RoomController
    static BookingControllerImpl controller = new BookingControllerImpl(new BookingServiceImpl());

    // Booking menu
    public static void showBookingMenu() {

        // Define options
        String[] options = {"Create booking", "Consult booking", "Update booking status", "See all bookings", "Go back", "Exit"};

        // Show options dialog
        int option = JOptionPane.showOptionDialog(null, "Welcome to the bookings menu!, select an option:", "Bookings Menu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Handle close button and exit option
        if (option == -1 || option == 5) return;

        // Handle user selection
        switch (option) {
            case 0 -> createBookingMenu();
            case 1 -> consultBooking();
            case 2 -> updateBookingStatus();
            case 3 -> showAllBookings();
            case 4 -> view.MainMenu.showMainMenu();
            default -> JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
        }

    }

    // Menu to create new booking
    public static void createBookingMenu() {

        // Declare variables
        String roomId, bookingDateStr, startTimeStr, endTimeStr, organizer;
        Date bookingDate;
        Time startTime, endTime;

        // Prompt for booking details

        // Loop until a valid room ID is entered or user cancels
        while (true) {

            // Room id
            roomId = JOptionPane.showInputDialog(null, "Enter room ID (F1R1):", "Create Booking", JOptionPane.QUESTION_MESSAGE);

            // Handle cancel button
            if (roomId == null) return;

            // Validate room id input
            if (InputValidator.isEmptyInput(roomId)) {
                JOptionPane.showMessageDialog(null, "Room ID cannot be empty.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Validate room id format
            if (InputValidator.isInvalidId(roomId.trim())) {
                JOptionPane.showMessageDialog(null, "Invalid room ID format. Please use the format F1R1.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Validate that room exists.
            if (RoomView.controller.findRoomById(roomId) == null) {
                JOptionPane.showMessageDialog(null, "Room does not exist.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Input is valid, exit this loop
            break;
        }

        // Loop until a valid date and time is entered
        while (true) {

            // Loop until a valid booking date is entered or user cancels
            while (true) {

                // Booking date
                bookingDateStr = JOptionPane.showInputDialog(null, "Enter Date (yyyy-MM-dd):", "Create Booking", JOptionPane.QUESTION_MESSAGE);

                // Handle cancel button
                if (bookingDateStr == null) return;

                // Validate booking date input
                if (InputValidator.isEmptyInput(bookingDateStr)) {
                    JOptionPane.showMessageDialog(null, "Date cannot be empty.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Validate booking date format
                if (!InputValidator.isValidDate(bookingDateStr)) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please use yyyy-MM-dd.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Validate valid date
                if (!controller.isBookingDateValid(Date.valueOf(bookingDateStr.trim()))) {
                    JOptionPane.showMessageDialog(null, "Date cannot be in the past, check current date", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Input is valid, exit this loop
                break;
            }

            // Assign date value
            bookingDate = Date.valueOf(bookingDateStr.trim());

            // Loop until a valid start time is entered or user cancels
            while (true) {
                // Booking start time
                startTimeStr = JOptionPane.showInputDialog(null, "Enter Start Time (HH:mm):", "Create Booking", JOptionPane.QUESTION_MESSAGE);

                // Handle cancel button
                if (startTimeStr == null) return;

                // Validate start time input
                if (InputValidator.isEmptyInput(startTimeStr)) {
                    JOptionPane.showMessageDialog(null, "Start time cannot be empty.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Validate time format
                if (InputValidator.isInvalidValidTime(startTimeStr)) {
                    JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm:ss.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Input is valid, exit this loop
                break;
            }

            // Assign start time value
            startTime = Time.valueOf(startTimeStr.trim() + ":00");

            // Loop until a valid end time is entered or user cancels
            while (true) {
                // Booking end time
                endTimeStr = JOptionPane.showInputDialog(null, "Enter End Time (HH:mm):", "Create Booking", JOptionPane.QUESTION_MESSAGE);

                // Handle cancel button
                if (endTimeStr == null) return;

                // Validate end time input
                if (InputValidator.isEmptyInput(endTimeStr)) {
                    JOptionPane.showMessageDialog(null, "End time cannot be empty.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Validate time format
                if (InputValidator.isInvalidValidTime(endTimeStr)) {
                    JOptionPane.showMessageDialog(null, "Invalid time format. Please use HH:mm:ss.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Input is valid, exit this loop
                break;
            }

            // Assign end time value
            endTime = Time.valueOf(endTimeStr.trim() + ":00");

            // Validate end time being after start time
            if (!controller.isValidTimeRange(startTime, endTime)) {
                JOptionPane.showMessageDialog(null, "Start time must be before end time.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Check for overlapping bookings
            if (controller.isOverlapping(roomId, bookingDate, startTime, endTime)) {
                JOptionPane.showMessageDialog(null, "The booking you're trying to create overlaps with an already existing booking.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Break datetime loop
            break;
        }

        // Loop until a valid organizer name is entered or user cancels
        while (true) {
            // Booking organizer
            organizer = JOptionPane.showInputDialog(null, "Enter Organizer Name:", "Create Booking", JOptionPane.QUESTION_MESSAGE);

            // Handle cancel button
            if (organizer == null) return;

            // Validate organizer input
            if (InputValidator.isEmptyInput(organizer)) {
                JOptionPane.showMessageDialog(null, "Organizer cannot be empty.", "Create Booking", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            // Input is valid, exit this loop
            break;
        }

        // Assign status
        Booking.Status status = Booking.Status.valueOf("ACTIVE");

        try {
            // Call controller to create booking
            boolean success = controller.createBooking(roomId, bookingDate, startTime, endTime, organizer, status);

            // If operation fails
            if (!success){
                JOptionPane.showMessageDialog(null, "Booking creation failed.", "Create Booking", JOptionPane.INFORMATION_MESSAGE);
            }

            // Display success message
            JOptionPane.showMessageDialog(null, "Booking successfully created!", "Create Booking", JOptionPane.INFORMATION_MESSAGE);

        } catch (Throwable e) {
            handleException(e, "Create Booking");
        }


    }

    // Menu to consult booking
    public static void consultBooking() {

        // Declare variables
        String bookingIdStr;
        int bookingId;
        Booking booking;

        try {
            // Loop until a valid booking ID is entered or user cancels
            while (true) {

                // Room id
                bookingIdStr = JOptionPane.showInputDialog(null, "Enter Booking ID:", "Update Booking", JOptionPane.QUESTION_MESSAGE);

                // Handle cancel button
                if (bookingIdStr == null) return;

                // Validate booking id input
                if (InputValidator.isEmptyInput(bookingIdStr)) {
                    JOptionPane.showMessageDialog(null, "Booking ID cannot be empty.", "Update Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Validate input format
                if (InputValidator.isInvalidInteger(bookingIdStr)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid id.", "Update Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Parse and call controller
                bookingId = Integer.parseInt(bookingIdStr);
                booking = controller.findBookingById(bookingId);

                // Validate that booking exists.
                if (booking == null) {
                    JOptionPane.showMessageDialog(null, "Booking does not exist.", "Update Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // If all inputs are valid
                break;
            }

            // Build booking data string
            StringBuilder bookingStr = new StringBuilder();
            bookingStr.append("Booking ID: ").append(booking.getBookingId()).append("\n");
            bookingStr.append("Room Id: ").append(booking.getRoomId()).append("\n");
            bookingStr.append("Date: ").append(booking.getDate()).append("\n");
            bookingStr.append("Start time: ").append(booking.getStartTime()).append("\n");
            bookingStr.append("End time: ").append(booking.getEndTime()).append("\n");
            bookingStr.append("Organizer: ").append(booking.getOrganizer()).append("\n");
            bookingStr.append("Status: ").append(booking.getStatus()).append("\n");
            bookingStr.append("\n");

            // Display booking info
            JOptionPane.showMessageDialog(null, bookingStr, "Consult Booking", JOptionPane.INFORMATION_MESSAGE);

        } catch (Throwable e) {
            handleException(e, "Consult Booking");
        }
    }

    // Menu to update booking status
    public static void updateBookingStatus() {

        // Declare variables
        String bookingIdStr;
        int bookingId;
        Booking booking;

        try {
            // Loop until a valid booking ID is entered or user cancels
            while (true) {

                // Room id
                bookingIdStr = JOptionPane.showInputDialog(null, "Enter Booking ID:", "Update Booking", JOptionPane.QUESTION_MESSAGE);

                // Handle cancel button
                if (bookingIdStr == null) return;

                // Validate booking id input
                if (InputValidator.isEmptyInput(bookingIdStr)) {
                    JOptionPane.showMessageDialog(null, "Booking ID cannot be empty.", "Update Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Validate input format
                if (InputValidator.isInvalidInteger(bookingIdStr)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid id.", "Update Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Parse and call controller
                bookingId = Integer.parseInt(bookingIdStr);
                booking = controller.findBookingById(bookingId);

                // Validate that booking exists.
                if (booking == null) {
                    JOptionPane.showMessageDialog(null, "Booking does not exist.", "Update Booking", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Input is valid, exit this loop
                break;
            }

            // Build update message
            StringBuilder updateMsg = new StringBuilder();
            updateMsg.append("Booking: ")
                    .append(booking.getBookingId())
                    .append(" Is currently: ")
                    .append(booking.getStatus())
                    .append(". \n")
                    .append("Select it's new status: ");

            // Define options booking status
            String[] options = {"Active", "Cancelled", "Cancel"};

            // Show options dialog
            int option = JOptionPane.showOptionDialog(null, updateMsg, "Update booking status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            // Handle close button and exit option
            if (option == -1 || option == 2) return;

            // Handle user selection
            switch (option) {
                case 0 -> booking.setStatus(Booking.Status.ACTIVE);
                case 1 -> booking.setStatus(Booking.Status.CANCELLED);
                default -> {
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.", "Update Booking Status", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }

            // Print success or failure message
            boolean success = controller.updateBookingStatus(booking);
            if (!success) {
                JOptionPane.showMessageDialog(null, "Update failed, try again", "Update Booking Status", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(null, "Updated successfully", "Update Booking Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (Throwable e) {
            handleException(e, "Consult Booking");
        }
    }

    // Menu to show all bookings
    public static void showAllBookings() {
        try {
            // Get list of all bookings
            List<Booking> bookings = controller.getAllBookings();

            // If list is empty, show error message
            if (bookings == null || bookings.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No bookings created.", "Show all Bookings", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Build a string to display all bookings
            StringBuilder bookingStr = new StringBuilder();
            for (Booking booking : bookings) {
                bookingStr.append("Booking ID: ").append(booking.getBookingId()).append("\n");
                bookingStr.append("Room Id: ").append(booking.getRoomId()).append("\n");
                bookingStr.append("Date: ").append(booking.getDate()).append("\n");
                bookingStr.append("Start time: ").append(booking.getStartTime()).append("\n");
                bookingStr.append("End time: ").append(booking.getEndTime()).append("\n");
                bookingStr.append("Organizer: ").append(booking.getOrganizer()).append("\n");
                bookingStr.append("Status: ").append(booking.getStatus()).append("\n");
                bookingStr.append("\n");
            }

            // Display all bookings
            JOptionPane.showMessageDialog(null, bookingStr, "Show all Bookings", JOptionPane.INFORMATION_MESSAGE);

        } catch (Throwable e) {
            handleException(e, "Show All Bookings");
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