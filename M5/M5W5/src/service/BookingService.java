package service;

import domain.Booking;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface BookingService {

    boolean createBooking(String roomId, Date date, Time startTime, Time endTime, String organizer, Booking.Status status);

    List<Booking> getAllBookings();

    Booking findBookingById(int bookingId);

    boolean updateBookingStatus(Booking booking);

}
