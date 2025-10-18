package domain;

public class Room {

    private String roomId;

    public Room() {
    }

    public Room(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId='" + roomId +
                '}';
    }
}

