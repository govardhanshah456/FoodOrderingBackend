package com.ansh.HotelBooking.service;


import com.ansh.HotelBooking.model.BookedRoom;
import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.model.RoomType;
import com.ansh.HotelBooking.repository.RoomRepository;
import com.ansh.HotelBooking.response.RoomTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeService roomTypeService;
    @Override
    public Room addRoom(MultipartFile photo, Long roomTypeId, BigDecimal roomPrice) throws IOException, SQLException {
        Room room = new Room();
        RoomType roomType = this.roomTypeService.getRoomTypeById(roomTypeId);
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if(!photo.isEmpty()){
            byte[] bytes = photo.getBytes();
            Blob img = new SerialBlob(bytes);
            room.setPhoto(img);
        }
        this.roomRepository.save(room);
        return room;
    }

    @Override
    public List<Room> getAllRooms() {
        return this.roomRepository.findAll();
    }

    @Override
    public Optional<Room> getRoomById(Long id) {
        return Optional.of(roomRepository.findById(id).get());
    }

    @Override
    public byte[] getPhotoByRoomId(Long id) throws SQLException {
        Blob photoBlob =  this.roomRepository.getPhotoByRoomId(id);
        if(photoBlob != null && photoBlob.length()>0){
            return photoBlob.getBytes(1,(int)photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoomByID(long roomId) {
        this.roomRepository.deleteById(roomId);
    }

    @Override
    public Room updateRoomById(long roomId, long roomTypeId, BigDecimal roomPrice, byte[] photo) throws SQLException {
        Room room = this.roomRepository.findById(roomId).get();
        if(roomPrice != null)
            room.setRoomPrice(roomPrice);
        if(photo!=null && photo.length>0){
            try {
                room.setPhoto(new SerialBlob(photo));
            } catch (SQLException ex) {
                throw new SQLException("Fail updating room");
            }
        }
        RoomType roomType = this.roomTypeService.getRoomTypeById(roomTypeId);
        if(roomType != null)
            room.setRoomType(roomType);
        return this.roomRepository.save(room);
    }


}
