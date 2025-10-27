package com.example.ad_management.dto.request;

public record UpdateAdRequest(
        String advertiser,
        String published,
        String imageUrl,
        String advertiserLink,
        Long agenciesId
) {}

