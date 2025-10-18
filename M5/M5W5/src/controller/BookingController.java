package controller;

import domain.Booking;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface BookingController {

    boolean createBooking(String roomId, Date date, Time startTime, Time endTime, String organizer, Booking.Status status);

    List<Booking> getAllBookings();

    Booking findBookingById(int bookingId);

    boolean updateBookingStatus(Booking booking);

    boolean isBookingDateValid(Date bookingDate);

    boolean isValidTimeRange(Time startTime, Time endTime);

    boolean isOverlapping(String roomId, Date bookingDate, Time StartTime, Time endTime);

    boolean timesOverlap(Time start1, Time end1, Time start2, Time end2);

}
