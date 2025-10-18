package controller;

import domain.Room;

import java.util.List;

public interface RoomController {

    boolean createRoom(String roomId);

    Room findRoomById(String roomId);

    List<Room> getAllRooms();

}
