package com.example.ad_management.controllers;

import com.example.ad_management.dto.AdContentShortResponse;
import com.example.ad_management.repositories.AdContent;
import com.example.ad_management.repositories.AdStatus;
import com.example.ad_management.services.AdContentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ad")
public class AdContentController {

    private final AdContentService adContentService;

    public AdContentController(AdContentService adContentService) {
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
                pageable.getSort().stream().
                        findFirst()
                        .map(Sort.Order::getProperty)
                        .orElse("id"),
                pageable.getSort().stream()
                        .findFirst().
                        map(order -> order.getDirection().name())
                        .orElse("desc"),
                searchTitle
        );
        return ResponseEntity.ok(ads);
    }

    @GetMapping
    public List<AdContentShortResponse> getAllAds() {
        return adContentService.getAllAds();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdContent> updateAdvertisement(
            @PathVariable Long id,
            @RequestBody AdContent updatedAdDetails,
            @RequestHeader("X-Published-By") String modifierId
    ) {
        AdContent updatedAd = adContentService.updateAd(id, updatedAdDetails, modifierId);
        return ResponseEntity.ok(updatedAd);
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

//    @GetMapping("/byStatus")
//    public List<AdContent> getAdByStatus(@RequestParam AdStatus status) {
//        return adContentService.findAdByStatus(status);
//    }
//
//    @GetMapping("/byDate")
//    public List<AdContent> getAdByStatus(@RequestParam LocalDate date) {
//        return adContentService.findByDate(date);
//    }

    @GetMapping("/search")
    public List<AdContent> searchAds(
            @RequestParam AdStatus status,
            @RequestParam LocalDate publishedDate,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
       return adContentService.adFiltered(status, publishedDate, startDate, endDate);
    }
}
