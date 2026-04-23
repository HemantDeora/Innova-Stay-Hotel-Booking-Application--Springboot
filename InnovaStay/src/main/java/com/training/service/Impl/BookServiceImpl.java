package com.training.service.Impl;

import com.training.Dto.BookingDto;
import com.training.Dto.BookingRequest;
import com.training.Dto.GuestDto;
import com.training.Entity.Hotel;
import com.training.Entity.Inventory;
import com.training.Entity.Room;
import com.training.Exception.ResourceNotFoundException;
import com.training.Repository.GuestRepository;
import com.training.Repository.HotelRepository;
import com.training.Repository.InventoryRepository;
import com.training.Repository.RoomRepository;
import com.training.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookingService{

    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional
    // Atomicity ->
    public BookingDto intializeBooking(BookingRequest bookingRequest) {
        log.info("Intializing bookinf for hotel : {} , room : {}, date : {} - {}",
                bookingRequest.getHotelId(), bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(()-> new ResourceNotFoundException("Hotel not found with ID : " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(()-> new ResourceNotFoundException("Room not found with ID : " + bookingRequest.getRoomId()));

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(bookingRequest.getRoomId(), bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount());

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())+ 1;

        if(inventoryList.size() != daysCount){
            throw new IllegalStateException("Room is not available anymore");
        }

        for(Inventory inventory : inventoryList){
            // 0 + 5 = 15
            inventory.setReserveCount(inventory.getReserveCount()+ bookingRequest.getRoomsCount());
        }

        // dynamic prices
        inventoryRepository.saveAll(inventoryList);

//        Booking -> reserve krna
        return null;
    }

    @Override
    public BookingDto addGuest(Long bookindId, List<GuestDto> guestDTOList) {
        return null;
    }
}