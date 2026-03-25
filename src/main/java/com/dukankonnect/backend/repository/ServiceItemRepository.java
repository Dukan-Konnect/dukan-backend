package com.dukankonnect.backend.repository;

import com.dukankonnect.backend.model.ServiceCategory;
import com.dukankonnect.backend.model.ServiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItem, Long> {
    List<ServiceItem> findByCategory(ServiceCategory category);
}