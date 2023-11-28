package com.ansh.HotelBooking.controller;


import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.response.RoomResponse;
import com.ansh.HotelBooking.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @PostMapping("/add/new-room")
    public ResponseEntity<RoomResponse> addRoom (@RequestParam("photo") MultipartFile photo, @RequestParam("roomTypeId") Long roomTypeId, @RequestParam("roomPrice") BigDecimal roomPrice) throws IOException, SQLException {
        Room room = this.roomService.addRoom(photo,roomTypeId,roomPrice);
        RoomResponse response = new RoomResponse(room.getId(),room.getRoomType().getName(),room.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all-rooms")
    public ResponseEntity<List<RoomResponse>> getAllRooms(){
        List<Room> rooms = this.roomService.getAllRooms();
        List<RoomResponse> response = new ArrayList<>();
        rooms.forEach((room)->{
            RoomResponse res = null;
            try {
                res = getRoomResponse(room);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            res.setRoomTypeName(res.getRoomType().getName());
            res.setRoomType(null);
            response.add(res);
        });
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/room/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long roomId){
        this.roomService.deleteRoomByID(roomId);
        return ResponseEntity.ok("Deleted Room Having ID:"+roomId);
    }

    @PutMapping("/edit/room/{roomId}")
    public ResponseEntity<RoomResponse> editRoom(@PathVariable Long roomId,@RequestParam(required = false) MultipartFile photo, @RequestParam(required = false) Long roomTypeId, @RequestParam(required = false) BigDecimal roomPrice)throws IOException,SQLException{
        System.out.println(roomPrice);
        byte[] photoBytes = photo != null && !photo.isEmpty() ?
                photo.getBytes() : this.roomService.getPhotoByRoomId(roomId);
        Blob photoBlob = photoBytes != null && photoBytes.length >0 ? new SerialBlob(photoBytes): null;
        Room room = this.roomService.updateRoomById(roomId,roomTypeId,roomPrice,photoBytes);
        RoomResponse response = new RoomResponse(room.getId(),room.getRoomType(),room.getRoomPrice());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{roomId}")
    public Optional<ResponseEntity<Optional<RoomResponse>>> getRoomById(@PathVariable("roomId") Long roomId) throws SQLException {
        Optional<Room> room = this.roomService.getRoomById(roomId);
        return room.map((roomInfo) -> {
            RoomResponse roomResponse = null;
            try {
                roomResponse = getRoomResponse(roomInfo);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return  ResponseEntity.ok(Optional.of(roomResponse));
        });
    }

    private RoomResponse getRoomResponse(Room room) throws SQLException {
        byte[] photoBytes = null;
        Blob photoBlob = room.getPhoto();
        if (photoBlob != null) {
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            } catch (SQLException e) {
                throw new SQLException("Error retrieving photo");
            }
        }
        return new RoomResponse(room.getId(),
                room.getRoomType(), room.getRoomPrice(),
                room.isBooked(), photoBytes, null);
    }

}
