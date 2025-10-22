package com.example.ad_management.repositories;

import com.example.ad_management.models.AdContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AdContentRepository extends JpaRepository<AdContentEntity, Long>, JpaSpecificationExecutor<AdContentEntity> {
    Page<AdContentEntity> findByAdvertiserContainingIgnoreCase(String advertiser, Pageable pageable);

}
