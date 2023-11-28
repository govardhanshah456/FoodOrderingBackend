package com.ansh.HotelBooking.controller;

import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.model.RoomType;
import com.ansh.HotelBooking.response.RoomResponse;
import com.ansh.HotelBooking.response.RoomTypeRequest;
import com.ansh.HotelBooking.response.RoomTypeResponse;
import com.ansh.HotelBooking.service.RoomService;
import com.ansh.HotelBooking.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/room-type")
@CrossOrigin(origins = "http://localhost:5173")
public class RoomTypeController {
    @Autowired
    private RoomTypeService roomTypeService;
    @PostMapping("/add/new-room-type")
    public ResponseEntity<RoomTypeResponse> addRoomType (@RequestBody RoomTypeRequest request){
        System.out.println("HERE3");
        RoomType room = this.roomTypeService.addRoomType(request);
        RoomTypeResponse response = new RoomTypeResponse(room.getId(),room.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/all-room-type")
    public ResponseEntity<List<RoomTypeResponse>> getAllRoomTypes (){
        List<RoomTypeResponse> response = this.roomTypeService.getAllRoomTypes();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/get/{roomTypeId}")
    public ResponseEntity<RoomTypeResponse> getRoomTypeById (@RequestParam("roomTypeId") Long id){
        RoomType res = this.roomTypeService.getRoomTypeById(id);
        RoomTypeResponse response = new RoomTypeResponse(res.getId(), res.getName());
        return ResponseEntity.ok(response);
    }
}
