package com.training.service;

import com.training.Dto.HotelDto;

import java.util.List;

public interface HotelService {

    HotelDto createHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDTO);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelID);


//    HotelDto getHotelById(Long id);
}
