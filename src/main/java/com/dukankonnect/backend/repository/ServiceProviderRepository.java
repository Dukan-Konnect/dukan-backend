package com.dukankonnect.backend.repository;

import com.dukankonnect.backend.model.AppServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceProviderRepository extends JpaRepository<AppServiceProvider, UUID> {
    List<AppServiceProvider> findBySubserviceId(UUID subserviceId);
}
