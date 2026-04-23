package com.training.Repository;

import com.training.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
