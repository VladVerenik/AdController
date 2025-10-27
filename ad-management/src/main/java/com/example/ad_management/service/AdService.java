package com.example.ad_management.service;

import com.example.ad_management.dto.request.FilterAdRequest;
import com.example.ad_management.dto.request.UpdateAdRequest;
import com.example.ad_management.dto.response.AdResponse;
import com.example.ad_management.dto.request.CreateAdRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdService {
    void delete(Long id, String deleterId);
    AdResponse create(String publisherId, CreateAdRequest createAdRequest, String imageUrl,  Long id);
    AdResponse update(Long id, UpdateAdRequest updatedAdDetails, String modifierId);
    Page<AdResponse> findAll(FilterAdRequest filterAdRequest, String search, Pageable pageable);
}
