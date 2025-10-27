package com.example.ad_management.dto.response;

import java.time.LocalDateTime;

public record AgencyResponse(
        Long id,
        String agencyName,
        LocalDateTime deletedAt,
        LocalDateTime creationAt,
        Boolean isSecureLink
){}
