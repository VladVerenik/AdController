package com.example.ad_management.mapper;

import com.example.ad_management.dto.request.CreateAgenciesRequest;
import com.example.ad_management.dto.request.UpdateAgenciesRequest;
import com.example.ad_management.dto.response.AgencyResponse;
import com.example.ad_management.dto.response.AgencyShortResponse;
import com.example.ad_management.entity.AdAgencyEntity;
import org.springframework.stereotype.Component;

@Component
public class AgencyMapper {
    public AdAgencyEntity toAgenciesEntity(CreateAgenciesRequest requestDto) {
        if (requestDto == null) {
            return null;
        }
        AdAgencyEntity entity = new AdAgencyEntity();
        entity.setAgencyName(requestDto.agencyName());
        entity.setSecureLink(requestDto.isSecureLink());

        return entity;
    }

    public void updateEntity(AdAgencyEntity entity, UpdateAgenciesRequest request) {
        if (entity == null || request == null) {
            return;
        }
        entity.setAgencyName(request.agencyName());
        entity.setSecureLink(request.isSecureLink());
    }

    public AgencyResponse toResponse(AdAgencyEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AgencyResponse(
                entity.getId(),
                entity.getAgencyName(),
                entity.getDeletedAt(),
                entity.getCreatedAt(),
                entity.isSecureLink()
        );
    }

    public AgencyShortResponse toShortResponse(AdAgencyEntity entity) {
        if (entity == null) {
            return null;
        }
        return new AgencyShortResponse(entity.getId(), entity.getAgencyName());
    }
}
