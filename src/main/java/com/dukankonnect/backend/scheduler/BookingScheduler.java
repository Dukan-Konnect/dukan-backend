package com.dukankonnect.backend.scheduler;

import com.dukankonnect.backend.model.Booking;
import com.dukankonnect.backend.model.BookingStatus;
import com.dukankonnect.backend.repository.BookingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class BookingScheduler {

    private final BookingRepository bookingRepository;

    public BookingScheduler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void checkAndCompleteBookings() {
        List<Booking> confirmedBookings = bookingRepository.findByStatus(BookingStatus.CONFIRMED);

        if (confirmedBookings.isEmpty()) {
            return;
        }

        Instant now = Instant.now();

        for (Booking booking : confirmedBookings) {
            if (booking.getScheduledDate() != null) {
                try {
                    Instant scheduledTime = Instant.parse(booking.getScheduledDate());
                    Instant completionTime = scheduledTime.plus(1, ChronoUnit.HOURS);

                    if (now.isAfter(completionTime)) {
                        booking.setStatus(BookingStatus.COMPLETED);
                        bookingRepository.save(booking);
                        System.out.println("Auto-completed booking ID: " + booking.getId());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to parse date for booking " + booking.getId());
                }
            }
        }
    }
}