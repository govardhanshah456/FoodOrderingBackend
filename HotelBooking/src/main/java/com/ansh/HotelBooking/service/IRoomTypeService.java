package com.ansh.HotelBooking.service;

import com.ansh.HotelBooking.model.RoomType;
import com.ansh.HotelBooking.response.RoomTypeRequest;
import com.ansh.HotelBooking.response.RoomTypeResponse;

import java.util.List;

public interface IRoomTypeService {
    public RoomType addRoomType(RoomTypeRequest roomType);
    public List<RoomTypeResponse> getAllRoomTypes();

    public RoomType getRoomTypeById(Long id);
}
