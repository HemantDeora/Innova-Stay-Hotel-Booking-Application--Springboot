package com.training.service.Impl;

import com.training.Dto.InventoryDto;
import com.training.Dto.UpdateInventoryRequestDto;
import com.training.Entity.Inventory;
import com.training.Entity.Room;
import com.training.Exception.ResourceNotFoundException;
import com.training.Repository.InventoryRepository;
import com.training.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);

        List<Inventory> inventories = new ArrayList<>();

        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .room(room)
                    .hotel(room.getHotel())
                    .date(date)
                    .totalCount(room.getTotalCount())
                    .bookedCount(0)
                    .city(room.getHotel().getCity())
                    .surgeFactor(BigInteger.ONE)
                    .price(room.getPrice())
                    .closed(false)
                    .build();
            inventories.add(inventory);
        }

        inventoryRepository.saveAll(inventories);
    }

    @Override
    public void deleteFutureInventories(Room room) {
        LocalDate today = LocalDate.now();
        inventoryRepository.deleteByRoomAndDateAfter(room, today);
    }

    @Override
    public List<InventoryDto> getAllInventories(Long hotelId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching inventories for hotel ID: {} from {} to {}", hotelId, startDate, endDate);

        return inventoryRepository
                .findByHotelIdAndDateBetween(hotelId, startDate, endDate)
                .stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDto updateInventory(Long hotelId, Long inventoryId, UpdateInventoryRequestDto dto) {
        log.info("Updating inventory ID: {} for hotel ID: {}", inventoryId, hotelId);

        Inventory inventory = inventoryRepository
                .findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with ID: " + inventoryId));

        if (!inventory.getHotel().getId().equals(hotelId)) {
            throw new ResourceNotFoundException("Inventory does not belong to hotel ID: " + hotelId);
        }

        if (dto.getSurgeFactor() != null) inventory.setSurgeFactor(dto.getSurgeFactor());
        if (dto.getPrice() != null) inventory.setPrice(dto.getPrice());
        if (dto.getClosed() != null) inventory.setClosed(dto.getClosed());

        inventoryRepository.save(inventory);

        return modelMapper.map(inventory, InventoryDto.class);
    }
}