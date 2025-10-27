package com.example.ad_management.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAgenciesRequest (
        @NotBlank
        String agencyName,
        boolean isSecureLink
){}
