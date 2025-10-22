package com.example.ad_management.dto.responses;

import com.example.ad_management.enums.AdStatus;


import java.time.LocalDate;

public record AdResponse (Long id,
                          LocalDate dateOfPublication,
                          LocalDate dateOfDeletion,
                          AdStatus status,
                          String advertiser,
                          String published,
                          String creatorId,
                          String lastModifiedBy,
                          String advertiserLink,
                          String imageUrl,
                          boolean deleted
){}

