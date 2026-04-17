package com.training.service.Impl;

import com.training.Dto.HotelDto;
import com.training.Entity.Hotel;
import com.training.Exception.ResourceNotFoundException;
import com.training.Repository.HotelRepository;
import com.training.service.HotelService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

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

//       TODO -> Inventory update ->
    }


}
