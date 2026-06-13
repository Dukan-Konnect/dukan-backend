package com.dukankonnect.backend.repository;

import com.dukankonnect.backend.model.Booking;
import com.dukankonnect.backend.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);
    List<Booking> findByStatus(BookingStatus status);
}