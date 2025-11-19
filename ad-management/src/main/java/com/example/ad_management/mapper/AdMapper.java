package com.example.ad_management.mapper;

import com.example.ad_management.dto.request.CreateAdRequest;
import com.example.ad_management.dto.request.UpdateAdRequest;
import com.example.ad_management.dto.response.AdResponse;
import com.example.ad_management.entity.AdAgencyEntity;
import com.example.ad_management.entity.AdContentEntity;
import com.example.ad_management.enums.AdStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AdMapper {
    private final AgencyMapper mapper;

    public AdContentEntity toAdEntity(CreateAdRequest requestDto, String publisherId) {
        if (requestDto == null) {
            return null;
        }
        AdContentEntity entity = new AdContentEntity();
        entity.setCreatorId(publisherId);
        entity.setLastModifiedBy(publisherId);
        entity.setStatus(AdStatus.DRAFT);
        entity.setAdvertiser(requestDto.advertiser());
        entity.setAdvertiserLink(requestDto.advertiserLink());
        entity.setPublished(requestDto.published());
        entity.setImageUrl(requestDto.imageUrl());
        return entity;
    }

    public void updateEntity(AdContentEntity entity, UpdateAdRequest request) {
        if (entity == null || request == null) {
            return;
        }
            entity.setAdvertiser(request.advertiser());
            entity.setAdvertiserLink(request.advertiserLink());
            entity.setPublished(request.published());
            entity.setImageUrl(request.imageUrl());
    }

    public AdResponse toResponse(AdContentEntity adContentEntity) {
        if (adContentEntity == null) {
            return null;
        }
        AdAgencyEntity entity = adContentEntity.getAdAgencyEntity();
        return new AdResponse(
                adContentEntity.getId(),
                adContentEntity.getPublishedAt(),
                adContentEntity.getDeletedAt(),
                adContentEntity.getStatus(),
                adContentEntity.getAdvertiser(),
                adContentEntity.getPublished(),
                adContentEntity.getCreatorId(),
                adContentEntity.getLastModifiedBy(),
                adContentEntity.getAdvertiserLink(),
                adContentEntity.getImageUrl(),
                mapper.toShortResponse(entity)
        );
    }
}
