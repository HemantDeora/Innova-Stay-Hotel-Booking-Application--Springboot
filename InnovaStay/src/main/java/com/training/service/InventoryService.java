package com.training.service;

import com.training.Dto.InventoryDto;
import com.training.Dto.UpdateInventoryRequestDto;
import com.training.Entity.Room;

import java.time.LocalDate;
import java.util.List;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);

    List<InventoryDto> getAllInventories(Long hotelId, LocalDate startDate, LocalDate endDate);

    InventoryDto updateInventory(Long hotelId, Long inventoryId, UpdateInventoryRequestDto dto);
}