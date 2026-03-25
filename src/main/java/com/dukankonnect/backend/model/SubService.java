package com.dukankonnect.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "subservices")
public class SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "category_id")
    private UUID categoryId;

    @Column(columnDefinition = "text")
    private String title;

    @Column(name = "price_cents")
    private Integer priceCents;

    @Column(columnDefinition = "text")
    private String currency;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    private Double rating;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "thumbnail_url", columnDefinition = "text")
    private String thumbnailUrl;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getCategoryId() { return categoryId; }
    public void setCategoryId(UUID categoryId) { this.categoryId = categoryId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getPriceCents() { return priceCents; }
    public void setPriceCents(Integer priceCents) { this.priceCents = priceCents; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
    public Integer getRatingCount() { return ratingCount; }
    public void setRatingCount(Integer ratingCount) { this.ratingCount = ratingCount; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
}
