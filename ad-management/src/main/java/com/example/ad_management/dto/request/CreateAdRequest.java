package com.example.ad_management.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAdRequest(
        String advertiser,
        @NotBlank
        String advertiserLink,
        @NotBlank
        String imageUrl,
        String published
) {}
