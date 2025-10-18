package dao;

import domain.Room;
import exceptions.DataAccessException;

import java.util.List;

public interface RoomDAO {

    void createRoom(Room room) throws DataAccessException;

    Room findRoomById(String roomId) throws DataAccessException;

    List<Room> getAllRooms() throws DataAccessException;

}
