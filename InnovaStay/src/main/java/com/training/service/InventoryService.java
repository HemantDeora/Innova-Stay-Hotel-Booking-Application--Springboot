package com.training.service;

import com.training.Dto.HotelDto;
import com.training.Dto.HotelSearchRequest;
import com.training.Entity.Room;
import org.springframework.data.domain.Page;
public interface InventoryService {


    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}