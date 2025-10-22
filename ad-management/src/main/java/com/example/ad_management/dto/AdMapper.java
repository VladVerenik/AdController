package com.example.ad_management.dto;

import com.example.ad_management.dto.requests.CreateAdRequest;
import com.example.ad_management.dto.requests.UpdateAdRequest;
import com.example.ad_management.dto.responses.AdContentShortResponse;
import com.example.ad_management.dto.responses.AdResponse;
import com.example.ad_management.models.AdContentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {

    public AdContentEntity toEntity(CreateAdRequest requestDto) {
        if (requestDto == null) {
            return null;
        }
        AdContentEntity entity = new AdContentEntity();

        entity.setAdvertiser(requestDto.advertiser());
        entity.setAdvertiserLink(requestDto.advertiserLink());
        entity.setImageUrl(requestDto.imageUrl());
        entity.setPublished(requestDto.published());

        return entity;
    }

    public void updateEntity(AdContentEntity entity, UpdateAdRequest request) {
        if (entity == null || request == null) {
            return;
        }

        if(request.advertiser() != null) {
            entity.setAdvertiser(request.advertiser());
        }

        if(request.advertiserLink() != null) {
            entity.setAdvertiserLink(request.advertiserLink());
        }

        if(request.published() != null) {
            entity.setPublished(request.published());
        }

        if(request.imageUrl() != null) {
            entity.setImageUrl(request.imageUrl());
        }
    }

    public AdContentShortResponse toShortResponse(AdContentEntity adContentEntity) {
        if (adContentEntity == null) {
            return null;
        }
        return new AdContentShortResponse(adContentEntity.getId(), adContentEntity.getAdvertiser());
    }

    public List<AdContentShortResponse> toShortResponseList(List<AdContentEntity> adContentEntities) {
        if (adContentEntities == null) {
            return null;
        }
        return adContentEntities.stream()
                .map(this::toShortResponse)
                .collect(Collectors.toList());
    }

    public AdResponse toResponse(AdContentEntity adContentEntity) {
        if (adContentEntity == null) {
            return null;
        }
        return new AdResponse(adContentEntity.getId(), adContentEntity.getDateOfPublication(), adContentEntity.getDateOfDeletion(), adContentEntity.getStatus(), adContentEntity.getAdvertiser(), adContentEntity.getPublished(), adContentEntity.getCreatorId(), adContentEntity.getLastModifiedBy(), adContentEntity.getAdvertiserLink(), adContentEntity.getImageUrl(), adContentEntity.isDeleted());
    }

    public List<AdResponse> toResponseList(List<AdContentEntity> adContentEntities) {
        if (adContentEntities == null) {
            return null;
        }
        return adContentEntities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
