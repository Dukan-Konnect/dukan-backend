package com.dukankonnect.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", unique = true)
    private String orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_service_id", nullable = false)
    private SubService subService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private AppServiceProvider provider;

    @Column(name = "provider_fee_cents")
    private Long providerFeeCents;

    @Column(name = "service_price_cents")
    private Long servicePriceCents;

    @Column(name = "total_amount_cents")
    private Long totalAmountCents;

    @Column(name = "booking_date")
    private Long bookingDate;

    @Column(name = "scheduled_date")
    private String scheduledDate;

    @Column(columnDefinition = "text")
    private String address;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }
    public SubService getSubService() { return subService; }
    public void setSubService(SubService subService) { this.subService = subService; }
    public AppServiceProvider getProvider() { return provider; }
    public void setProvider(AppServiceProvider provider) { this.provider = provider; }
    public Long getProviderFeeCents() { return providerFeeCents; }
    public void setProviderFeeCents(Long providerFeeCents) { this.providerFeeCents = providerFeeCents; }
    public Long getServicePriceCents() { return servicePriceCents; }
    public void setServicePriceCents(Long servicePriceCents) { this.servicePriceCents = servicePriceCents; }
    public Long getTotalAmountCents() { return totalAmountCents; }
    public void setTotalAmountCents(Long totalAmountCents) { this.totalAmountCents = totalAmountCents; }
    public Long getBookingDate() { return bookingDate; }
    public void setBookingDate(Long bookingDate) { this.bookingDate = bookingDate; }
    public String getScheduledDate() { return scheduledDate; }
    public void setScheduledDate(String scheduledDate) { this.scheduledDate = scheduledDate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
}