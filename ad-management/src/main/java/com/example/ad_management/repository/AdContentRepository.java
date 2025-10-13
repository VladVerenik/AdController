package com.example.ad_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdContentRepository extends JpaRepository<AdContent, Long> {
    Page<AdContent> findByAdvertiserContainingIgnoreCase(String advertiser, Pageable pageable);

}
