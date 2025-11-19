package com.example.ad_management.service;

import com.example.ad_management.entity.AdAgencyEntity;
import com.example.ad_management.mapper.AdMapper;
import com.example.ad_management.dto.request.FilterAdRequest;
import com.example.ad_management.dto.request.UpdateAdRequest;
import com.example.ad_management.dto.response.AdResponse;
import com.example.ad_management.dto.request.CreateAdRequest;
import com.example.ad_management.entity.AdContentEntity;
import com.example.ad_management.repository.AdContentRepository;
import com.example.ad_management.repository.AgencyRepository;
import com.example.ad_management.repository.specification.AdContentSpecifications;
import com.example.ad_management.enums.AdStatus;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class AdServiceImpl implements AdService{
    private final AgencyRepository agencyRepository;
    private final AdContentRepository adContentRepository;
    private final AdMapper mapper;

    @Override
    public AdResponse create(String publisherId, CreateAdRequest createRequest) {
        if (publisherId == null || publisherId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        AdAgencyEntity agency = agencyRepository.findById(createRequest.agenciesId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Агентство не найдено"));

        AdContentEntity newAd = mapper.toAdEntity(createRequest, publisherId);
        checkSecureLink(agency, newAd);
        newAd.setAdAgencyEntity(agency);
        AdContentEntity savedAd = adContentRepository.save(newAd);
        return mapper.toResponse(savedAd);
    }

    @Override
    public Page<AdResponse> findAll(
            FilterAdRequest filterAdRequest,
            String search,
            Pageable pageable
    ) {
        Specification<AdContentEntity> spec = getSpecification(filterAdRequest, search);
        return adContentRepository.findAll(spec, pageable).map(mapper::toResponse);
    }

    @Override
    public AdResponse update(Long id, UpdateAdRequest updateAdRequest, String modifierId) {
        if (modifierId == null || modifierId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        AdContentEntity existingAd = adContentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Рекламная запись с данным ID не найдена"));

        mapper.updateEntity(existingAd, updateAdRequest);
        existingAd.setLastModifiedBy(modifierId);

        if (existingAd.getStatus() == AdStatus.DRAFT) {
            existingAd.setPublishedAt(null);
        }

        if(updateAdRequest.agenciesId() != null) {
            AdAgencyEntity existingAgencies = agencyRepository.findById(updateAdRequest.agenciesId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Агенство с данным ID не найдено"));

            checkSecureLink(existingAgencies, existingAd);
            existingAd.setAdAgencyEntity(existingAgencies);
        }
        AdContentEntity updatedAd = adContentRepository.save(existingAd);
        return mapper.toResponse(updatedAd);
    }

    @Override
    public void delete(Long id, String deleterId) {
        if (deleterId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }
        if (!adContentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого id нету");
        }
        adContentRepository.deleteById(id);
    }

    private Specification<AdContentEntity> getSpecification(
            FilterAdRequest filterAdRequest,
            String search
    ) {
        Specification<AdContentEntity> spec = Specification.not(null);

        if (filterAdRequest.status() != null) {
            spec = spec.and(AdContentSpecifications.adStatus(filterAdRequest.status()));
        }

        if (filterAdRequest.publicationDate() != null) {
            spec = spec.and(AdContentSpecifications.dateOfPublication(filterAdRequest.publicationDate()));
        }

        if (filterAdRequest.startDate() != null) {
            spec = spec.and(AdContentSpecifications.createAfter(filterAdRequest.startDate()));
        }

        if (filterAdRequest.endDate() != null) {
            spec = spec.and(AdContentSpecifications.createBefore(filterAdRequest.endDate()));
        }

        if (search != null && !search.isBlank()) {
            spec = spec.and(AdContentSpecifications.search(search));
        }
        return spec;
    }

    private void checkSecureLink (AdAgencyEntity adAgencyEntity, AdContentEntity adContentEntity) {
        String link = adContentEntity.getAdvertiserLink();
        if (!link.toLowerCase().startsWith("https://") && adAgencyEntity.isSecureLink()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Агентство требует безопасную ссылку (https://)"
            );
        }
    }
}
