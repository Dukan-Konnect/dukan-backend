package com.dukankonnect.backend.controller;

import com.dukankonnect.backend.model.*;
import com.dukankonnect.backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final SubServiceRepository subServiceRepository;
    private final ServiceProviderRepository providerRepository;

    public BookingController(BookingRepository bookingRepository, UserRepository userRepository, SubServiceRepository subServiceRepository, ServiceProviderRepository providerRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.subServiceRepository = subServiceRepository;
        this.providerRepository = providerRepository;
    }

    private AppUser getCurrentUser() {
        String phoneNumber = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
    }

    @PostMapping
    public ResponseEntity<BookingDTOs.BookingResponse> createBooking(@RequestBody BookingDTOs.CreateBookingRequest request) {
        AppUser user = getCurrentUser();

        SubService subService = subServiceRepository.findById(request.subServiceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubService not found"));

        AppServiceProvider provider = providerRepository.findById(request.providerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Provider not found"));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSubService(subService);
        booking.setProvider(provider);
        booking.setScheduledDate(request.scheduledDate);
        booking.setAddress(request.address);

        booking.setOrderId("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        booking.setBookingDate(System.currentTimeMillis());

        long serviceFee = subService.getPriceCents() != null ? subService.getPriceCents() : 0L;
        long providerFee = provider.getFee() != null ? provider.getFee() : 0L;
        booking.setServicePriceCents(serviceFee);
        booking.setProviderFeeCents(providerFee);
        booking.setTotalAmountCents(serviceFee + providerFee);

        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(PaymentStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        return ResponseEntity.ok(new BookingDTOs.BookingResponse(savedBooking));
    }

    @GetMapping
    public ResponseEntity<List<BookingDTOs.BookingResponse>> getMyBookings() {
        AppUser user = getCurrentUser();

        List<Booking> bookings = bookingRepository.findByUserIdOrderByBookingDateDesc(user.getId());

        List<BookingDTOs.BookingResponse> response = bookings.stream()
                .map(BookingDTOs.BookingResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // ADMIN ENDPOINT to update booking status
    @PutMapping("/{id}/status")
    public ResponseEntity<BookingDTOs.BookingResponse> updateBookingStatus(
            @PathVariable UUID id,
            @RequestParam BookingStatus newStatus) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        booking.setStatus(newStatus);

        if (newStatus == BookingStatus.CONFIRMED || newStatus == BookingStatus.COMPLETED) {
            booking.setPaymentStatus(PaymentStatus.PAID);
        } else if (newStatus == BookingStatus.CANCELLED) {
            booking.setPaymentStatus(PaymentStatus.FAILED);
        }

        Booking updatedBooking = bookingRepository.save(booking);

        return ResponseEntity.ok(new BookingDTOs.BookingResponse(updatedBooking));
    }
}