package com.training.service.Impl;

import com.training.Dto.HotelDto;
import com.training.Dto.HotelInfoDto;
import com.training.Dto.RoomDto;
import com.training.Entity.Hotel;
import com.training.Entity.Room;
import com.training.Exception.ResourceNotFoundException;
import com.training.Repository.HotelRepository;
import com.training.service.HotelService;
import com.training.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {

        log.info("Creating a new Hotel with Name : {}", hotelDto.getName());

        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        Hotel savedHotel = hotelRepository.save(hotel);
        log.info("Created a new Hotel with Id : {}", hotelDto.getId());
        return modelMapper.map(savedHotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {

        log.info("Getting Hotel Details by Id : {}", id);

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDTO) {
        log.info("Updating Hotel details by ID {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with this ID" +id));
        modelMapper.map(hotelDTO , hotel);
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID :"+id));

        hotelRepository.deleteById(id);
//        TODO -> Inventory
    }

    @Override
    public void activateHotel(Long hotelID) {
        log.info("Activationg Hotel details by ID {}", hotelID);
        Hotel hotel = hotelRepository.findById(hotelID).orElseThrow(()-> new ResourceNotFoundException("Hotel not found with this ID" +hotelID));

        hotel.setActive(true);
        hotelRepository.save(hotel);
//       TODO -> Inventory update ->
        for(Room room: hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        // hotel ke data ko DTO Map krdunga
        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }


    @Override
    public List<HotelDto> getAllHotels() {
        log.info("Fetching all hotels");

        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream()
                .map(hotel -> modelMapper.map(hotel, HotelDto.class))
                .collect(Collectors.toList());
    }

}
