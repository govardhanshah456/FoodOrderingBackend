package com.ansh.HotelBooking.service;

import com.ansh.HotelBooking.model.BookedRoom;
import com.ansh.HotelBooking.response.BookingResponse;

import java.util.List;

public interface IBookedRoomService {
    void cancelBooking(Long bookingId);

    List<BookedRoom> getAllBookingsByRoomId(Long roomId);

    String saveBooking(Long roomId, BookedRoom bookingRequest) throws Exception;

    BookedRoom findByBookingConfirmationCode(String confirmationCode) throws Exception;

    List<BookedRoom> getAllBookings();

    List<BookedRoom> getBookingsByUserEmail(String email);
}
