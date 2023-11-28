package com.ansh.HotelBooking.repository;

import com.ansh.HotelBooking.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Blob;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Long> {

    @Query("SELECT r.photo FROM Room r where r.id = :roomId")
    public Blob getPhotoByRoomId(@Param("roomId") Long id);
}
