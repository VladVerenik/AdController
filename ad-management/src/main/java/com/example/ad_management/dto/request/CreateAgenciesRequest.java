package com.example.ad_management.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAgenciesRequest (
        @NotNull
        String agencyName,
        LocalDateTime deletedAt,
        LocalDateTime creationAt,
        @NotNull
        boolean isSecureLink
){}
