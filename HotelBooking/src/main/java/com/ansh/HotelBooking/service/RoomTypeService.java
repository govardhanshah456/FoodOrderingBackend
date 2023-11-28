package com.ansh.HotelBooking.service;

import com.ansh.HotelBooking.model.RoomType;
import com.ansh.HotelBooking.repository.RoomTypeRepository;
import com.ansh.HotelBooking.response.RoomTypeRequest;
import com.ansh.HotelBooking.response.RoomTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoomTypeService implements IRoomTypeService{

    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Override
    public RoomType addRoomType(RoomTypeRequest roomType) {
        RoomType room = new RoomType();
        room.setName(roomType.getName());
        System.out.println("here");
        return this.roomTypeRepository.save(room);
    }

    @Override
    public List<RoomTypeResponse> getAllRoomTypes() {
        List<RoomTypeResponse> roomTypeResponses = new ArrayList<>();
        List<RoomType> rooms = this.roomTypeRepository.findAll();
        rooms.forEach((room)->{
            RoomTypeResponse resp = new RoomTypeResponse(room.getId(), room.getName());
            roomTypeResponses.add(resp);
        });
        return roomTypeResponses;
    }

    @Override
    public RoomType getRoomTypeById(Long id) {
        return this.roomTypeRepository.getById(id);
    }
}
