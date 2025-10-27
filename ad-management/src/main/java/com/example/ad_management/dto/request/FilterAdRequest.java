package com.example.ad_management.dto.request;

import com.example.ad_management.enums.AdStatus;

import java.time.LocalDate;

public record FilterAdRequest(
         AdStatus status,
         LocalDate publicationDate,
         LocalDate startDate,
         LocalDate endDate
){}
