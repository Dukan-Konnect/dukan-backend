package com.dukankonnect.backend.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(columnDefinition = "text")
    private String name;

    @Column(name = "icon_url", columnDefinition = "text")
    private String iconUrl;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getIconUrl() { return iconUrl; }
    public void setIconUrl(String iconUrl) { this.iconUrl = iconUrl; }
}