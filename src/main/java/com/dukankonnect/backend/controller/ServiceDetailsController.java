package com.dukankonnect.backend.controller;

import com.dukankonnect.backend.model.*;
import com.dukankonnect.backend.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ServiceDetailsController {

    private final ServiceItemRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final SubServiceRepository subServiceRepository;
    private final ServiceProviderRepository providerRepository;

    public ServiceDetailsController(
            ServiceItemRepository serviceRepository,
            CategoryRepository categoryRepository,
            SubServiceRepository subServiceRepository,
            ServiceProviderRepository providerRepository
    ) {
        this.serviceRepository = serviceRepository;
        this.categoryRepository = categoryRepository;
        this.subServiceRepository = subServiceRepository;
        this.providerRepository = providerRepository;
    }

    @GetMapping("/services/{serviceId}/details")
    public ResponseEntity<ServiceResponses.ServiceDetailsResponse> getServiceDetails(@PathVariable Long serviceId) {

        ServiceItem service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Service not found"));

        List<Category> categories = categoryRepository.findByServiceId(serviceId);
        List<UUID> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());

        List<SubService> subServices = categoryIds.isEmpty() ? List.of() : subServiceRepository.findByCategoryIdIn(categoryIds);

        ServiceResponses.ServiceDetailsResponse response = new ServiceResponses.ServiceDetailsResponse();
        response.id = service.getId();
        response.title = service.getName();
        response.bannerTitle = service.getCategory() != null ? service.getCategory().name() : service.getName();
        response.bannerImage = service.getThumbnail() != null ? service.getThumbnail() : "";

        response.categories = categories.stream().map(c -> {
            ServiceResponses.CategoryItemDTO dto = new ServiceResponses.CategoryItemDTO();
            dto.id = c.getId();
            dto.label = c.getName();
            dto.image = c.getIconUrl() != null ? c.getIconUrl() : "";
            return dto;
        }).collect(Collectors.toList());

        response.sections = categories.stream().map(cat -> {
            ServiceResponses.ServiceSectionDTO section = new ServiceResponses.ServiceSectionDTO();
            section.id = cat.getId();
            section.title = cat.getName();

            section.items = subServices.stream()
                    .filter(s -> s.getCategoryId().equals(cat.getId()))
                    .map(s -> {
                        ServiceResponses.SubServiceDTO sub = new ServiceResponses.SubServiceDTO();
                        sub.id = s.getId();
                        sub.title = s.getTitle();
                        sub.rating = s.getRating() != null ? s.getRating() : 0.0;
                        sub.reviewCount = s.getRatingCount() != null ? s.getRatingCount() : 0;
                        sub.durationMin = s.getDurationMinutes();
                        sub.price = s.getPriceCents() != null ? s.getPriceCents() / 100 : 0;
                        sub.currency = s.getCurrency() != null ? s.getCurrency() : "INR";
                        sub.image = s.getThumbnailUrl() != null ? s.getThumbnailUrl() : "";
                        return sub;
                    }).collect(Collectors.toList());
            return section;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/subservices/{subserviceId}/providers")
    public ResponseEntity<List<AppServiceProvider>> getProviders(@PathVariable UUID subserviceId) {
        List<AppServiceProvider> providers = providerRepository.findBySubserviceId(subserviceId);
        return ResponseEntity.ok(providers);
    }
}