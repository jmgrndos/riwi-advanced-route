package dao;

import domain.Booking;
import exceptions.DataAccessException;

import java.util.List;

public interface BookingDAO {

    void createBooking(Booking booking) throws DataAccessException;

    Booking findBookingById(int bookingId) throws DataAccessException;

    void updateBookingStatus(Booking booking) throws DataAccessException;

    List<Booking> getAllBookings() throws DataAccessException;

}
