package com.dukankonnect.backend.model;

import java.util.UUID;

public class BookingDTOs {

    public static class CreateBookingRequest {
        public UUID subServiceId;
        public UUID providerId;
        public String scheduledDate;
        public String address;
    }

    public static class BookingResponse {
        public String id;
        public String orderId;
        public String subServiceId;
        public String subServiceName;
        public String subServiceImage;
        public String providerId;
        public String providerName;
        public String providerImage;
        public String providerPhone;
        public Double providerRating;
        public Long providerFeeCents;
        public Long servicePriceCents;
        public Long totalAmountCents;
        public Long bookingDate;
        public String scheduledDate;
        public String address;
        public String status;
        public String paymentStatus;

        public BookingResponse(Booking b) {
            this.id = b.getId().toString();
            this.orderId = b.getOrderId();
            this.subServiceId = b.getSubService().getId().toString();
            this.subServiceName = b.getSubService().getTitle();
            this.subServiceImage = b.getSubService().getThumbnailUrl();
            this.providerId = b.getProvider().getId().toString();
            this.providerName = b.getProvider().getName();
            this.providerImage = b.getProvider().getImageUrl();
            this.providerPhone = b.getProvider().getPhoneNumber();
            this.providerRating = b.getProvider().getRating();
            this.providerFeeCents = b.getProviderFeeCents();
            this.servicePriceCents = b.getServicePriceCents();
            this.totalAmountCents = b.getTotalAmountCents();
            this.bookingDate = b.getBookingDate();
            this.scheduledDate = b.getScheduledDate();
            this.address = b.getAddress();
            this.status = b.getStatus().name();
            this.paymentStatus = b.getPaymentStatus().name();
        }
    }
}