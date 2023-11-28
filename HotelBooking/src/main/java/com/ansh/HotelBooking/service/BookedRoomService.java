package com.ansh.HotelBooking.service;

import com.ansh.HotelBooking.model.BookedRoom;
import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.repository.BookedRoomRepository;
import com.ansh.HotelBooking.response.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookedRoomService implements IBookedRoomService{

    @Autowired
    private BookedRoomRepository bookedRoomRepository;
    @Autowired
    private RoomService roomService;
    @Override
    public void cancelBooking(Long bookingId) {
        this.bookedRoomRepository.deleteById(bookingId);
    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return bookedRoomRepository.findByRoomId(roomId);
    }

    @Override
    public String saveBooking(Long roomId, BookedRoom bookingRequest) throws Exception {
        System.out.println(bookingRequest);
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())){
            throw new Exception("Check-in date must come before check-out date");
        }
        Room room = this.roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        if (roomIsAvailable){
            room.addBooking(bookingRequest);
            bookedRoomRepository.save(bookingRequest);
        }else{
            throw  new Exception("Sorry, This room is not available for the selected dates;");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) throws Exception {
        return bookedRoomRepository.findByBookingConfirmationCode(confirmationCode)
                .orElseThrow(() -> new Exception("No booking found with booking code :"+confirmationCode));
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return bookedRoomRepository.findAll();
    }

    @Override
    public List<BookedRoom> getBookingsByUserEmail(String email) {
        return bookedRoomRepository.findByGuestEmail(email);
    }
}
