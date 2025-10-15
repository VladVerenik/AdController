package com.example.ad_management.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AdContentRepository extends JpaRepository<AdContent, Long>, JpaSpecificationExecutor<AdContent> {
    Page<AdContent> findByAdvertiserContainingIgnoreCase(String advertiser, Pageable pageable);

}
