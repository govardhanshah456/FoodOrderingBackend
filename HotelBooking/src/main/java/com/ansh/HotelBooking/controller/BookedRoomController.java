package com.ansh.HotelBooking.controller;


import com.ansh.HotelBooking.model.BookedRoom;
import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.response.BookingResponse;
import com.ansh.HotelBooking.response.RoomResponse;
import com.ansh.HotelBooking.service.BookedRoomService;
import com.ansh.HotelBooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/booking")
@CrossOrigin(origins = "http://localhost:5173")
public class BookedRoomController {

    @Autowired
    private BookedRoomService bookedRoomService;
    @Autowired
    private RoomService roomService;

    @GetMapping("/all-booking")
    public ResponseEntity<List<BookingResponse>> getAllBookings(){
        List<BookingResponse> res = this.bookedRoomService.getAllBookings().stream().map(this::getBookingResponse).collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<BookingResponse>> getBookingByRoomId(@PathVariable Long roomId){
        List<BookingResponse> res = this.bookedRoomService.getAllBookingsByRoomId(roomId).stream().map(this::getBookingResponse).collect(Collectors.toList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/room/{roomId}/book")
    public ResponseEntity<?> getBookingByRoomId(@PathVariable Long roomId,@RequestBody BookedRoom body) throws Exception {
        String res = this.bookedRoomService.saveBooking(roomId,body);
        return ResponseEntity.ok(
                "Room booked successfully, Your booking confirmation code is :"+res);
    }
    @GetMapping("/confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) throws Exception {
        BookedRoom booking = bookedRoomService.findByBookingConfirmationCode(confirmationCode);
        BookingResponse bookingResponse = getBookingResponse(booking);
        return ResponseEntity.ok(bookingResponse);
    }
    @GetMapping("/user/{email}/bookings")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserEmail(@PathVariable String email) {
        List<BookedRoom> bookings = bookedRoomService.getBookingsByUserEmail(email);
        List<BookingResponse> bookingResponses = new ArrayList<>();
        for (BookedRoom booking : bookings) {
            BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponses.add(bookingResponse);
        }
        return ResponseEntity.ok(bookingResponses);
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public void cancelBooking(@PathVariable Long bookingId){
        bookedRoomService.cancelBooking(bookingId);
    }
    private BookingResponse getBookingResponse(BookedRoom booking) {
        Room theRoom = roomService.getRoomById(booking.getRoom().getId()).get();
        RoomResponse room = new RoomResponse(
                theRoom.getId(),
                theRoom.getRoomType(),
                theRoom.getRoomPrice());
        return new BookingResponse(
                booking.getBookingId(), booking.getCheckInDate(),
                booking.getCheckOutDate(),booking.getGuestFullName(),
                booking.getGuestEmail(), booking.getNumOfAdults(),
                booking.getNumOfChildren(), booking.getNumOfGuests(),
                booking.getBookingConfirmationCode(), room);
    }
}
