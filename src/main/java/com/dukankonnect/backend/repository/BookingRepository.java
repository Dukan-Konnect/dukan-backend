package com.dukankonnect.backend.repository;

import com.dukankonnect.backend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);
}