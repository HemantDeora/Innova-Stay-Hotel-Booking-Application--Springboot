package com.training.Controller;

import com.training.Dto.HotelDto;
import com.training.Entity.Hotel;
import com.training.service.HotelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/hotels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping()
    public ResponseEntity<HotelDto> createHotel(@RequestBody HotelDto hotelDto) {
        log.info("Attempting to Create a new Hotel with name : {} ", hotelDto.getName());
        HotelDto savedHotel = hotelService.createHotel(hotelDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHotel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @PutMapping("/{HotelId}")
    public ResponseEntity<HotelDto> updateHotelById(@PathVariable Long HotelId, @RequestBody HotelDto hotelDto){
        HotelDto hotel = hotelService.updateHotelById(HotelId, hotelDto);
        return  ResponseEntity.ok(hotel);

    }

    @DeleteMapping("/{HotelId}")
    public ResponseEntity<Void> deleteHotelById(@PathVariable Long HotelId){
        hotelService.deleteHotelById(HotelId);
        return  ResponseEntity.noContent().build();

    }

    @PatchMapping("/{HotelId}")
    public ResponseEntity<Void> activateHotel(@PathVariable Long HotelId){
        hotelService.activateHotel(HotelId);
        return  ResponseEntity.noContent().build();

    }
}
