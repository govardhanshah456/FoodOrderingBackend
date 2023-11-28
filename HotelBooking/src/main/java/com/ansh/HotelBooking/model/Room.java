package com.ansh.HotelBooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    private BigDecimal roomPrice;
    private boolean isBooked = false;
    @Lob
    private Blob photo;

    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<BookedRoom> bookings;

    public Room(){
        this.bookings = new ArrayList<>();
    }

    public void addBooking(BookedRoom bookedRoom){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(bookedRoom);
        bookedRoom.setRoom(this);
        isBooked = true;
        bookedRoom.setBookingConfirmationCode(RandomStringUtils.randomNumeric(10));
    }

}
