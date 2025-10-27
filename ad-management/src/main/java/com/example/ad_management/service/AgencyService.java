package com.example.ad_management.service;

import com.example.ad_management.dto.request.*;
import com.example.ad_management.dto.response.AgencyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgencyService {
    void delete(Long id);
    AgencyResponse create(CreateAgenciesRequest createAgenciesRequest);
    AgencyResponse update(Long id, UpdateAgenciesRequest updateAgenciesRequest);
    Page<AgencyResponse> findAll(Pageable pageable);
}
