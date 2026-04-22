package com.training.service.Impl;

import com.training.Dto.RoomDto;
import com.training.Repository.HotelRepository;
import com.training.Repository.RoomRepository;
import com.training.Entity.Hotel;
import com.training.Entity.Room;
import com.training.Exception.ResourceNotFoundException;
import com.training.service.InventoryService;
import com.training.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepo;
    private final HotelRepository hotelRepo;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDTO) {
        log.info("Creating a new room in the Hotel with ID : {}", hotelId);
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID :" + hotelId));
        Room room = modelMapper.map(roomDTO, Room.class);
        room.setHotel(hotel);
        room = roomRepo.save(room);
        inventoryService.initializeRoomForAYear(room);

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomInHotel(Long hotelId) {
        log.info("Get all room in the Hotel by ID : {}", hotelId);
        Hotel hotel = hotelRepo.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID :" + hotelId));
        return hotel.getRooms()
                .stream()
                .map(room -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {

        log.info("Get room by ID : {}", roomId);
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID :" + roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public void deleteRoomById(Long roomId) {

        log.info("Deleting the room by ID : {}", roomId);
        Room room = roomRepo.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID :" + roomId));
        inventoryService.deleteFutureInventories(room);

        roomRepo.delete(room);
    }
}