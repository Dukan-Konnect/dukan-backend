package com.dukankonnect.backend.controller;

import com.dukankonnect.backend.model.ServiceCategory;
import com.dukankonnect.backend.model.ServiceItem;
import com.dukankonnect.backend.repository.ServiceItemRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final ServiceItemRepository serviceRepository;

    public ServiceController(ServiceItemRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @GetMapping
    public ResponseEntity<List<ServiceItem>> getServicesByCategory(
            @RequestParam(name = "category") ServiceCategory category
    ) {
        List<ServiceItem> services = serviceRepository.findByCategory(category);
        return ResponseEntity.ok(services);
    }
}