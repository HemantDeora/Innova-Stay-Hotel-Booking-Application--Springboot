package com.training.Controller;

import com.training.Dto.BookingDto;
import com.training.Dto.BookingRequest;
import com.training.Dto.GuestDto;
import com.training.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.intializeBooking(bookingRequest));
    }

    @PostMapping("{bookingId}/guest")
    public ResponseEntity<BookingDto> addGuest(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDTOList){
        return ResponseEntity.ok(bookingService.addGuest(bookingId,guestDTOList));
    }
}