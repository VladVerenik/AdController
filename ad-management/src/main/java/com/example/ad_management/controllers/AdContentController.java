package com.example.ad_management.controllers;

import com.example.ad_management.dto.AdMapper;
import com.example.ad_management.dto.requests.SortAdRequest;
import com.example.ad_management.dto.requests.UpdateAdRequest;
import com.example.ad_management.dto.responses.AdResponse;
import com.example.ad_management.dto.requests.CreateAdRequest;
import com.example.ad_management.services.AdService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Data
@RequestMapping("/api/v1/ad")
public class AdContentController {

    private final AdService adService;
    private final AdMapper adMapper;

    @GetMapping("/page")
    public Page<AdResponse> getAds(
            @SortDefault(
                    sort = "dateOfPublication",
                    direction = Sort.Direction.DESC
            )
            SortAdRequest sortAdRequest,
            String search,
            Pageable pageable
    ) {
        return adService.getAds(
                sortAdRequest,
                search,
                pageable
        );
    }

    @PutMapping("/{id}")
    public AdResponse updateAdvertisement(
            @PathVariable Long id,
            @RequestBody UpdateAdRequest updateAdRequest,
            @RequestHeader("X-Published-By") String modifierId
    ) {
        return adService.updateAd(id, updateAdRequest, modifierId);
    }

    @PostMapping
    public AdResponse createAd(
            @RequestBody CreateAdRequest createRequest,
            @RequestBody MultipartFile image,
            @RequestHeader("X-Published-By") String publisherId
    ) {
        return adService.createAd(publisherId, createRequest, image);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAd(
            @PathVariable Long id,
            @RequestHeader("X-Published-By") String deleterId

    ) {
        adService.deleteAd(id, deleterId);
        return new ResponseEntity<>("Ad deleted", HttpStatus.NO_CONTENT);
    }
}
