package com.example.ad_management.dto.requests;

import com.example.ad_management.enums.AdStatus;

import java.time.LocalDate;

public record SortAdRequest(
         AdStatus status,
         LocalDate publicationDate,
         LocalDate startDate,
         LocalDate endDate
){}
