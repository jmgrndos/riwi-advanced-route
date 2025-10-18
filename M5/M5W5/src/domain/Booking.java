package domain;

import java.sql.Date;
import java.sql.Time;

public class Booking {

    private int bookingId;
    private String roomId;
    private Date date;
    private Time startTime;
    private Time endTime;
    private String organizer;
    private Status status;

    public enum Status {
        ACTIVE,
        CANCELLED
    }

    public Booking() {
    }

    public Booking(int bookingId, String roomId, Date date, Time startTime, Time endTime, String organizer, Status status) {
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", roomId='" + roomId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", organizer='" + organizer + '\'' +
                ", status=" + status +
                '}';
    }


}
