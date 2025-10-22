package com.example.ad_management.dto.requests;

import lombok.Data;

import java.time.LocalDate;

public record UpdateAdRequest(
        String advertiser,
        String published,
        String imageUrl,
        String advertiserLink
) {}

