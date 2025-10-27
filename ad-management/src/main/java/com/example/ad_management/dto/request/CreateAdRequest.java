package com.example.ad_management.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateAdRequest(
        String advertiser,
        @NotNull
        String advertiserLink,
        @NotNull
        String imageUrl,
        String published
) {}
