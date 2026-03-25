package com.dukankonnect.backend.repository;

import com.dukankonnect.backend.model.SubService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubServiceRepository extends JpaRepository<SubService, UUID> {
    List<SubService> findByCategoryIdIn(List<UUID> categoryIds);
}
