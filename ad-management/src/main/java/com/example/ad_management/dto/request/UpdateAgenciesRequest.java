package com.example.ad_management.dto.request;

import java.time.LocalDateTime;

public record UpdateAgenciesRequest (
    String agencyName,
    LocalDateTime deletedAt,
    LocalDateTime creationAt,
    boolean isSecureLink
){}
