package controller;

import domain.Booking;
import service.BookingServiceImpl;
import util.ExceptionMapper;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class BookingControllerImpl implements BookingController {

    private final BookingServiceImpl bookingService;

    public BookingControllerImpl(BookingServiceImpl bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public boolean createBooking(String roomId, Date date, Time startTime, Time endTime, String organizer, Booking.Status status) {
        try {
            // Call service to create booking
            return bookingService.createBooking(roomId, date, startTime, endTime, organizer, status);

        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return false;

        }
    }

    @Override
    public List<Booking> getAllBookings() {
        try {
            // Call service layer to retrieve all bookings
            return bookingService.getAllBookings();

        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return List.of(); // return empty list on error
        }
    }

    @Override
    public Booking findBookingById(int bookingId) {
        try {
            // Call service to retrieve booking
            return bookingService.findBookingById(bookingId);

        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateBookingStatus(Booking booking) {
        try {
            // Call service layer to update booking status
            return bookingService.updateBookingStatus(booking);

        } catch (RuntimeException e) {

            // Print status code error
            int statusCode = ExceptionMapper.getHttpStatusCode(e);
            System.err.println("Error (" + statusCode + "): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isBookingDateValid(Date bookingDate) {
        Date currentDate = new Date(System.currentTimeMillis());
        return !bookingDate.before(currentDate);
    }

    @Override
    public boolean isValidTimeRange(Time startTime, Time endTime) {
        return endTime.after(startTime);
    }

    @Override
    public boolean isOverlapping(String roomId, Date bookingDate, Time StartTime, Time endTime) {
        return getAllBookings().stream()
                .filter(b ->
                        b.getRoomId().equalsIgnoreCase(roomId)
                                && b.getDate().equals(bookingDate)
                                && b.getStatus() == Booking.Status.ACTIVE)
                .anyMatch(b -> timesOverlap(StartTime, endTime, b.getStartTime(), b.getEndTime()));
    }

    @Override
    public boolean timesOverlap(Time start1, Time end1, Time start2, Time end2) {
        return start1.before(end2) && start2.before(end1);
    }
}
