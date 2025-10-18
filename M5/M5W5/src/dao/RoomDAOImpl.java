package dao;

import config.ConnectionFactory;
import domain.Room;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    @Override
    public void createRoom(Room room) throws DataAccessException {

        // SQL query to insert a new room
        String query = "INSERT INTO room (room_id) VALUES (?)";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setString(1, room.getRoomId());

            // Execute the update
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error inserting room data: ", e);
        }

    }

    @Override
    public Room findRoomById(String roomId) throws DataAccessException {

        // Initialize room on null
        Room room = null;

        // SQL query to find a room by ID
        String query = "SELECT * FROM room WHERE room_id = ?";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the prepared statement
            stmt.setString(1, roomId);

            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery();

            // If a room is found, create a Room object
            if (rs.next()) {
                room = new Room(
                        rs.getString("room_id")
                );
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error accessing room data", e);
        }

        // Return found room (if there is)
        return room;

    }

    @Override
    public List<Room> getAllRooms() throws DataAccessException {

        // Initialize room list
        List<Room> rooms = new ArrayList<>();

        // SQL query to retrieve all rooms
        String query = "SELECT * FROM room";

        // Use try-with-resources to ensure resources are closed
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Execute the query and get the result set
            ResultSet rs = stmt.executeQuery();

            // Iterate through the result set and create Room objects
            while (rs.next()) {
                Room room = new Room(
                        rs.getString("room_id")
                );

                // Add room to the list
                rooms.add(room);

            }

        } catch (SQLException e) {
            throw new DataAccessException("Error accessing room data: ", e);
        }

        // Return list
        return rooms;
    }

}
