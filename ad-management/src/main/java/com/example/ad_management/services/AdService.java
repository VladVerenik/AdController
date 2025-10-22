package com.example.ad_management.services;

import com.example.ad_management.dto.requests.SortAdRequest;
import com.example.ad_management.dto.requests.UpdateAdRequest;
import com.example.ad_management.dto.responses.AdResponse;
import com.example.ad_management.dto.requests.CreateAdRequest;
import com.example.ad_management.enums.AdStatus;
import com.example.ad_management.models.AdContentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface AdService {
    void deleteAd(Long id, String deleterId);
    AdResponse createAd(String publisherId, CreateAdRequest createAdRequest, MultipartFile image);
    AdResponse updateAd(Long id, UpdateAdRequest updatedAdDetails, String modifierId);
    Page<AdResponse> getAds(SortAdRequest sortAdRequest, String search, Pageable pageable);
}
