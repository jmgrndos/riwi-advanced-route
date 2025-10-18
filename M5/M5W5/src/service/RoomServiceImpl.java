package service;

import dao.RoomDAO;
import dao.RoomDAOImpl;
import domain.Room;
import exceptions.ConflictException;
import exceptions.DataAccessException;
import exceptions.NotFoundException;
import exceptions.ServiceException;

import java.util.List;

public class RoomServiceImpl implements RoomService {

    private final RoomDAO roomDAO = new RoomDAOImpl();

    @Override
    public boolean createRoom(String roomId) {
        try {

            // If room already exists
            if (roomDAO.findRoomById(roomId) != null) {
                throw new ConflictException("Room already exists with ID: " + roomId);
            }

            // Create new Room and call DAO to insert it
            Room room = new Room(roomId);
            roomDAO.createRoom(room);
            return true;

        } catch (DataAccessException e) {
            throw new ServiceException("Error creating room.", e);
        }
    }

    @Override
    public Room findRoomById(String roomId) {
        try {

            // Retrieve room
            Room room = roomDAO.findRoomById(roomId);

            // If room is not found
            if (room == null) {
                throw new NotFoundException("Room with ID " + roomId + " not found.");
            }

            // If room is found, return it
            return room;

        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving room.", e);
        }
    }

    @Override
    public List<Room> getAllRooms() {
        try {

            // Retrieve all rooms
            List<Room> rooms = roomDAO.getAllRooms();

            // If no rooms have been added
            if (rooms == null || rooms.isEmpty()) {
                throw new NotFoundException("No rooms available.");
            }

            // Return room list
            return rooms;

        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving rooms.", e);
        }
    }
}
