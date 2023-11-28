package com.ansh.HotelBooking.response;

import com.ansh.HotelBooking.model.BookedRoom;
import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.model.RoomType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;

import java.math.BigDecimal;
//import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomTypeName;
    private RoomType roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    private String photo;

    private List<BookingResponse> bookings;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomTypeName = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponse(Long id, RoomType roomType, BigDecimal roomPrice) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, boolean isBooked, byte[] photo, List<BookingResponse> bookings) {
        this.id = id;
        this.roomTypeName = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photo != null ? Base64.encodeBase64String(photo) : null;
        this.bookings = bookings;
    }

    public RoomResponse(Long id, RoomType roomType, BigDecimal roomPrice, boolean isBooked, byte[] photo, List<BookingResponse> bookings) {
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.isBooked = isBooked;
        this.photo = photo != null ? Base64.encodeBase64String(photo) : null;
        this.bookings = bookings;
    }
}
