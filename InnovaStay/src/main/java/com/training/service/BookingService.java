package com.training.service;

import com.training.Dto.BookingDto;
import com.training.Dto.BookingRequest;
import com.training.Dto.GuestDto;

import java.util.List;

public interface BookingService {
    //    intializeBooking
    BookingDto intializeBooking (BookingRequest bookingRequest);
    //    Kitne guest ->
    BookingDto addGuest(Long bookindId , List<GuestDto> guestDTOList);
}
