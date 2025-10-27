package com.example.ad_management.repository;

import com.example.ad_management.entity.AdContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AdContentRepository extends JpaRepository<AdContentEntity, Long>, JpaSpecificationExecutor<AdContentEntity> {

}
