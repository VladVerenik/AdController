package com.example.ad_management.dto;

import com.example.ad_management.repositories.AdContent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdMapper {
    public AdContentShortResponse toShortResponse(AdContent adContent) {
        if (adContent == null) {
            return null;
        }
        return new AdContentShortResponse(adContent.getId(), adContent.getAdvertiser());
    }

    public List<AdContentShortResponse> toShortResponseList(List<AdContent> adContents) {
        if (adContents == null) {
            return null;
        }
        return adContents.stream()
                .map(this::toShortResponse)
                .collect(Collectors.toList());
    }
}
