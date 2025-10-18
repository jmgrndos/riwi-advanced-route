package dao;

import config.ConnectionFactory;
import domain.Booking;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    @Override
    public void createBooking(Booking booking) throws DataAccessException {

        // SQL query to insert a new booking
        String query = "INSERT INTO booking (room_id, date, start_time, end_time, organizer, status) VALUES (?, ?, ?, ?, ?, ?)";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setString(1, booking.getRoomId());
            stmt.setDate(2, booking.getDate());
            stmt.setTime(3, booking.getStartTime());
            stmt.setTime(4, booking.getEndTime());
            stmt.setString(5, booking.getOrganizer());
            stmt.setString(6, booking.getStatus().name());

            // Execute the update
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting booking data: ", e);
        }

    }

    @Override
    public Booking findBookingById(int bookingId) throws DataAccessException {

        // Initialize booking on null
        Booking booking = null;

        // SQL query to find a booking by ID
        String query = "SELECT * FROM booking WHERE booking_id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setInt(1, bookingId);

            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery();

            // If a room is found, create a Room object
            if (rs.next()) {
                booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getString("room_id"),
                        rs.getDate("date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("organizer"),
                        rs.getString("status").equals("ACTIVE") ? Booking.Status.ACTIVE : Booking.Status.CANCELLED
                );
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error accessing booking data: ", e);
        }

        // Return found booking (if there is)
        return booking;
    }

    @Override
    public void updateBookingStatus(Booking booking) throws DataAccessException {

        // SQL query to update a booking
        String query = "UPDATE booking SET room_id = ?, date = ?, start_time = ?, end_time = ?, organizer = ?, status = ? WHERE booking_id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setString(1, booking.getRoomId());
            stmt.setDate(2, booking.getDate());
            stmt.setTime(3, booking.getStartTime());
            stmt.setTime(4, booking.getEndTime());
            stmt.setString(5, booking.getOrganizer());
            stmt.setString(6, booking.getStatus().equals(Booking.Status.ACTIVE) ? "ACTIVE" : "CANCELLED");
            stmt.setInt(7, booking.getBookingId());

            // Execute the query
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error updating booking data: ", e);
        }

    }

    @Override
    public List<Booking> getAllBookings() throws DataAccessException {

        // Initialize booking list on null
        List<Booking> bookings = new ArrayList<>();

        // SQL query to get all bookings
        String query = "SELECT * FROM booking";

        // Use try-with-resources to ensure resources are closed
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Room objects
            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("booking_id"),
                        rs.getString("room_id"),
                        rs.getDate("date"),
                        rs.getTime("start_time"),
                        rs.getTime("end_time"),
                        rs.getString("organizer"),
                        rs.getString("status").equals("ACTIVE") ? Booking.Status.ACTIVE : Booking.Status.CANCELLED
                );

                // Add room to the list
                bookings.add(booking);

            }

        } catch (SQLException e) {
            throw new DataAccessException("Error accessing booking data: ", e);
        }

        // Return bookings list
        return bookings;
    }
}
