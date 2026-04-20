package com.training.service;

import com.training.Dto.RoomDto;
import com.training.Entity.Room;
import com.training.Repository.RoomRepository;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId, RoomDto RoomDto);

    List<RoomDto> getAllRoomInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);


}
