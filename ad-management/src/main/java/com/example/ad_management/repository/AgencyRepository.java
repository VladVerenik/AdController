package com.example.ad_management.repository;

import com.example.ad_management.entity.AdAgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<AdAgencyEntity, Long> {
}
