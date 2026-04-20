package com.training.Repository;

import com.training.Entity.Inventory;
import com.training.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByRoomAndDateAfter(Room room, LocalDate date);
    List<Inventory> findByHotelIdAndDateBetween(Long hotelId, LocalDate startDate, LocalDate endDate);
}
