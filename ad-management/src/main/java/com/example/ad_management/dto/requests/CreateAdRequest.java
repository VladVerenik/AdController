package com.example.ad_management.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record CreateAdRequest(
        String advertiser,
        String advertiserLink,
        String imageUrl,
        String published
) {}
