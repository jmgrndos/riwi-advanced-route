package service;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import domain.Booking;
import exceptions.ConflictException;
import exceptions.DataAccessException;
import exceptions.NotFoundException;
import exceptions.ServiceException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingDAO bookingDAO = new BookingDAOImpl();

    @Override
    public boolean createBooking(String roomId, Date date, Time startTime, Time endTime, String organizer, Booking.Status status) {

        // Create new booking
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        booking.setDate(date);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setOrganizer(organizer);
        booking.setStatus(status);

        try {
            // Call DAO to create booking on the database
            bookingDAO.createBooking(booking);
            return true;

        } catch (DataAccessException e) {
            throw new ConflictException("Unable to create booking.");
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while creating booking.", e);
        }

    }

    @Override
    public List<Booking> getAllBookings() {
        try {
            // Call DAO to retrieve all bookings
            return bookingDAO.getAllBookings();

        } catch (DataAccessException e) {
            throw new ServiceException("Unable to retrieve bookings.", e);
        }
    }

    @Override
    public Booking findBookingById(int bookingId) {
        try {
            // Retrieve booking
            Booking booking = bookingDAO.findBookingById(bookingId);

            // Booking is not found
            if (booking == null) {
                throw new NotFoundException("Booking with ID " + bookingId + " not found.");
            }

            // If booking is found, return it
            return booking;

        } catch (DataAccessException e) {
            throw new ServiceException("Error retrieving booking.", e);
        }
    }

    @Override
    public boolean updateBookingStatus(Booking booking) {
        try {
            // Call DAO to execute the update
            bookingDAO.updateBookingStatus(booking);
            return true;

        } catch (DataAccessException e) {
            throw new ServiceException("Error updating booking status.", e);
        }
    }
}
