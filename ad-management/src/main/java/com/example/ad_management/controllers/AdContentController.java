package com.example.ad_management.controllers;

import com.example.ad_management.repository.AdContent;
import com.example.ad_management.repository.AdContentRepository;
import com.example.ad_management.services.AdContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ad")
public class AdContentController {

    private final AdContentService adContentService;

    public AdContentController(AdContentService adContentService, AdContentRepository adContentRepository) {
        this.adContentService = adContentService;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<AdContent>> getAdsById(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "dateOfPublication",
                    direction = Sort.Direction.DESC

            )
            Pageable pageable,
            @RequestParam(required = false) String searchTitle
            ) {
        Page<AdContent> ads = adContentService.getAds(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSort().stream().findFirst().map(Sort.Order::getProperty).orElse("id"),
                pageable.getSort().stream().findFirst().map(order -> order.getDirection().name()).orElse("desc"),
                searchTitle
        );
        return ResponseEntity.ok(ads);
    }

    @GetMapping
    public List<AdContent> getAllAds() {
        return adContentService.getAllAds();
    }

    @PostMapping
    public ResponseEntity<AdContent> createAd(
            @RequestBody AdContent newAd,
            @RequestHeader("X-Published-By") String publisherId
    ) {
        AdContent createdAd = adContentService.createAd(publisherId, newAd);
        return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAd(
            @PathVariable Long id,
            @RequestHeader("X-Published-By") String deleterId

    ) {
        adContentService.deleteAd(id, deleterId);
        return new ResponseEntity<>("Ad deleted", HttpStatus.NO_CONTENT);
    }

}
