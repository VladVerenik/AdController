package com.example.ad_management.services;

import com.example.ad_management.dto.AdMapper;
import com.example.ad_management.repositories.AdContent;
import com.example.ad_management.repositories.AdContentRepository;
import com.example.ad_management.dto.AdContentShortResponse;
import com.example.ad_management.repositories.AdContentSpecifications;
import com.example.ad_management.repositories.AdStatus;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AdContentService {
    private final AdContentRepository adContentRepository;
    private final AdMapper adMapper;

    public AdContentService(AdContentRepository adContentRepository, AdMapper adMapper) {
        this.adContentRepository = adContentRepository;
        this.adMapper = adMapper;
    }

    public void deleteAd(Long id, String deleterId) {
        if (deleterId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        Optional<AdContent> adContent = adContentRepository.findById(id);
        if(adContent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого id нету");
        }

        AdContent adToDelete = adContent.get();
        adToDelete.setStatus(AdStatus.DELETED);
        adToDelete.setDateOfDeletion(LocalDate.now());
        adContentRepository.save(adToDelete);
    }

    public AdContent createAd(String publisherId, AdContent newAd) {
        if (publisherId == null || publisherId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }
        if (newAd.getStatus() == AdStatus.DRAFT ) {
            newAd.setDateOfPublication(null);
        }

        newAd.setCreatorId(publisherId);
        newAd.setLastModifiedBy(publisherId);
        newAd.setStatus(AdStatus.PUBLISHED);
        return adContentRepository.save(newAd);
    }

    public AdContent updateAd(Long id, AdContent updatedAdDetails, String modifierId) {
        if (modifierId == null || modifierId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        AdContent existingAd = adContentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Рекламная запись с данным ID не найдена"));


        existingAd.setDateOfPublication(updatedAdDetails.getDateOfPublication());
        existingAd.setDateOfDeletion(updatedAdDetails.getDateOfDeletion());
        existingAd.setAdvertiser(updatedAdDetails.getAdvertiser());
        existingAd.setPublished(updatedAdDetails.getPublished());
        existingAd.setStatus(updatedAdDetails.getStatus());
        existingAd.setLastModifiedBy(modifierId);

        if (existingAd.getStatus() == AdStatus.DRAFT) {
            existingAd.setDateOfPublication(null);
        }

        if(existingAd.getStatus() == AdStatus.DELETED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Объявление удалено ");
        }

        return adContentRepository.save(existingAd);
    }

    public Page<AdContent> getAds(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDirection,
            String searchTitle
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (searchTitle != null && !searchTitle.isEmpty()) {
            return adContentRepository.findByAdvertiserContainingIgnoreCase(searchTitle, pageable);
        } else {
            return adContentRepository.findAll(pageable);
        }


    }

    public List<AdContentShortResponse> getAllAds() {
        List<AdContent> allAds = adContentRepository.findAll();
        return adMapper.toShortResponseList(allAds);
    }

//    public List<AdContent> findAdByStatus(AdStatus status) {
//        return adContentRepository.findAll(AdContentSpecifications.adStatus(status));
//    }
//
//    public List<AdContent> findByDate(LocalDate date) {
//        return adContentRepository.findAll(AdContentSpecifications.dateOfPublication(date));
//
//    }

    public  List<AdContent> adFiltered (
            AdStatus status,
            LocalDate publicationDate,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (status != null) {
            return adContentRepository.findAll(AdContentSpecifications.adStatus(status));
        }
        if (publicationDate == null){

            if (startDate != null) {
                return adContentRepository.findAll(AdContentSpecifications.createAfter(startDate));
            }

            if (endDate != null) {
                return adContentRepository.findAll(AdContentSpecifications.createBefore(endDate));
            }
        }

        if (startDate == null && endDate == null) {
            return adContentRepository.findAll(AdContentSpecifications.dateOfPublication(publicationDate));
        }

        return adContentRepository.findAll();
    }
}
