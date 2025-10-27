package com.example.ad_management.dto.response;

import com.example.ad_management.enums.AdStatus;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record AdResponse (Long id,
                          LocalDate publishedAt,
                          LocalDateTime deleteAt,
                          AdStatus status,
                          String advertiser,
                          String published,
                          String creatorId,
                          String lastModifiedBy,
                          String advertiserLink,
                          String imageUrl,
                          AgencyShortResponse agencyShortResponse

){}

