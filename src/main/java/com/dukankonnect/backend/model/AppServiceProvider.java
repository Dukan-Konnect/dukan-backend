package com.dukankonnect.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "service_providers")
public class AppServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "subservice_id")
    private UUID subserviceId;

    @Column(columnDefinition = "text")
    private String name;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @Column(name = "phone_number", columnDefinition = "text")
    private String phoneNumber;

    private Double rating;

    private Integer fee;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSubserviceId() {
        return subserviceId;
    }

    public void setSubserviceId(UUID subserviceId) {
        this.subserviceId = subserviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
