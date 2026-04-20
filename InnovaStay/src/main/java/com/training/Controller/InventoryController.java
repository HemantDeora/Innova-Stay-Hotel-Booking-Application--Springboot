package com.training.Controller;

import com.training.Dto.InventoryDto;
import com.training.Dto.UpdateInventoryRequestDto;
import com.training.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hotels/{hotelId}/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryDto>> getAllInventories(
            @PathVariable Long hotelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return ResponseEntity.ok(inventoryService.getAllInventories(hotelId, startDate, endDate));
    }

    @PatchMapping("/{inventoryId}")
    public ResponseEntity<InventoryDto> updateInventory(
            @PathVariable Long hotelId,
            @PathVariable Long inventoryId,
            @RequestBody UpdateInventoryRequestDto dto) {

        return ResponseEntity.ok(inventoryService.updateInventory(hotelId, inventoryId, dto));
    }
}