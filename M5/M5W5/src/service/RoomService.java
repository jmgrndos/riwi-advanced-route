package service;

import domain.Room;

import java.util.List;

public interface RoomService {

    boolean createRoom(String roomId);

    Room findRoomById(String roomId);

    List<Room> getAllRooms();

}
