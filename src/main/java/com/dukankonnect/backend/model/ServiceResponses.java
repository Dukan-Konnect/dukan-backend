package com.dukankonnect.backend.model;

import java.util.List;
import java.util.UUID;

public class ServiceResponses {

    public static class ServiceDetailsResponse {
        public Long id;
        public String title;
        public String bannerTitle;
        public String bannerImage;
        public Double rating = 0.0;
        public Integer reviewCount = 0;
        public String bookingsText = "Same day bookings available";
        public List<CategoryItemDTO> categories;
        public List<ServiceSectionDTO> sections;
    }

    public static class CategoryItemDTO {
        public UUID id;
        public String label;
        public String image;
    }

    public static class ServiceSectionDTO {
        public UUID id;
        public String title;
        public List<SubServiceDTO> items;
    }

    public static class SubServiceDTO {
        public UUID id;
        public String title;
        public Double rating;
        public Integer reviewCount;
        public Integer durationMin;
        public Integer price;
        public String currency;
        public String image;
    }
}