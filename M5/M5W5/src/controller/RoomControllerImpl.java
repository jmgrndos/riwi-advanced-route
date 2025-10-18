package controller;

import domain.Room;
import service.RoomServiceImpl;
import util.ExceptionMapper;

import java.util.List;

public class RoomControllerImpl implements RoomController {

    private final RoomServiceImpl roomService;

    public RoomControllerImpl(RoomServiceImpl roomService) {
        this.roomService = roomService;
    }

    @Override
    public boolean createRoom(String roomId) {
        try {
            // Call service to create room
            return roomService.createRoom(roomId);
        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return false;
        }
    }

    @Override
    public Room findRoomById(String roomId) {
        try {
            // Call service to create room
            return roomService.findRoomById(roomId);

        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Room> getAllRooms() {
        try {
            // Call service to retrieve all rooms
            return roomService.getAllRooms();

        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return List.of();
        }
    }
}
