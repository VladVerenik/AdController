package com.example.ad_management.services;

import com.example.ad_management.dto.AdMapper;
import com.example.ad_management.dto.requests.SortAdRequest;
import com.example.ad_management.dto.requests.UpdateAdRequest;
import com.example.ad_management.dto.responses.AdResponse;
import com.example.ad_management.dto.requests.CreateAdRequest;
import com.example.ad_management.models.AdContentEntity;
import com.example.ad_management.repositories.AdContentRepository;
import com.example.ad_management.specifications.AdContentSpecifications;
import com.example.ad_management.enums.AdStatus;
import lombok.Data;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Data
@Service
public class AdContentService implements AdService{
    private final AdContentRepository adContentRepository;
    private final AdMapper adMapper;
    private final FileService fileService;

    @Override
    public void deleteAd(Long id, String deleterId) {
        if (deleterId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        if (!adContentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого id нету");
        }

        adContentRepository.deleteById(id);
    }

    @Override
    public AdResponse createAd(String publisherId, CreateAdRequest createRequest, MultipartFile image) {
        if (publisherId == null || publisherId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }
        String url = fileService.buildImageUrl(fileService.store(image));

        AdContentEntity newAd = adMapper.toEntity(createRequest);
        newAd.setCreatorId(publisherId);
        newAd.setLastModifiedBy(publisherId);
        newAd.setStatus(AdStatus.DRAFT);
        newAd.setImageUrl(url);
        AdContentEntity savedAd = adContentRepository.save(newAd);

        return adMapper.toResponse(savedAd);
    }

    @Override
    public AdResponse updateAd(Long id, UpdateAdRequest updateAdRequest, String modifierId) {
        if (modifierId == null || modifierId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        AdContentEntity existingAd = adContentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Рекламная запись с данным ID не найдена"));

        adMapper.updateEntity(existingAd, updateAdRequest);
        existingAd.setLastModifiedBy(modifierId);

        if (existingAd.getStatus() == AdStatus.DRAFT) {
            existingAd.setDateOfPublication(null);
        }

        AdContentEntity updatedAd = adContentRepository.save(existingAd);
        return adMapper.toResponse(updatedAd);

    }

    @Override
    public Page<AdResponse> getAds(
            SortAdRequest sortAdRequest,
            String search,
            Pageable pageable
    ) {
        Specification<AdContentEntity> spec = Specification.not(null);

        if (sortAdRequest.status() != null) {
            spec = spec.and(AdContentSpecifications.adStatus(sortAdRequest.status()));
        }

        if (sortAdRequest.publicationDate() != null) {
            spec = spec.and(AdContentSpecifications.dateOfPublication(sortAdRequest.publicationDate()));
        }

        if (sortAdRequest.startDate() != null) {
            spec = spec.and(AdContentSpecifications.createAfter(sortAdRequest.startDate()));
        }

        if (sortAdRequest.endDate() != null) {
            spec = spec.and(AdContentSpecifications.createBefore(sortAdRequest.endDate()));
        }

        if (search != null && !search.isBlank()) {
            spec = spec.and(AdContentSpecifications.search(search));
        }

        Page<AdContentEntity> entity = adContentRepository.findAll(spec, pageable);
        return entity.map(adMapper::toResponse);
    }
}
