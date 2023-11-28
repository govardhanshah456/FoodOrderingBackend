package com.ansh.HotelBooking.service;

import com.ansh.HotelBooking.model.BookedRoom;
import com.ansh.HotelBooking.model.Room;
import com.ansh.HotelBooking.model.RoomType;
import com.ansh.HotelBooking.response.RoomTypeRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    public Room addRoom(MultipartFile photo, Long roomTypeId, BigDecimal roomPrice) throws IOException, SQLException;
    public List<Room> getAllRooms();
    public Optional<Room> getRoomById(Long id);
    public byte[] getPhotoByRoomId(Long id) throws SQLException;

    public void deleteRoomByID(long roomId);

    public Room updateRoomById(long roomId,long roomTypeId,BigDecimal roomPrice, byte[] photo) throws SQLException;
}
