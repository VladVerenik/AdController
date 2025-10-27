package com.example.ad_management.service;

import com.example.ad_management.dto.request.*;
import com.example.ad_management.dto.response.AgencyResponse;
import com.example.ad_management.entity.AdAgencyEntity;
import com.example.ad_management.mapper.AgencyMapper;
import com.example.ad_management.repository.AgencyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@Service
public class AgencyServiceImp implements AgencyService {
    private final AgencyRepository repository;
    private final AgencyMapper mapper;

    @Override
    public AgencyResponse create(CreateAgenciesRequest createAgenciesRequest) {
        AdAgencyEntity newAgencies = mapper.toAgenciesEntity(createAgenciesRequest);
        AdAgencyEntity savedAd = repository.save(newAgencies);
        return mapper.toResponse(savedAd);
    }

    @Override
    public Page<AgencyResponse> findAll(
            Pageable pageable
    ) {
        Page<AdAgencyEntity> entity = repository.findAll(pageable);
        return entity.map(mapper::toResponse);
    }

    @Override
    public AgencyResponse update(Long id, UpdateAgenciesRequest updateAgenciesRequest) {
        AdAgencyEntity existingAgencies = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Рекламная запись с данным ID не найдена"));

        mapper.updateEntity(existingAgencies, updateAgenciesRequest);
        AdAgencyEntity updatedAd = repository.save(existingAgencies);
        return mapper.toResponse(updatedAd);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого id нету");
        }
        repository.deleteById(id);
    }
}
