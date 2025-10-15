package com.example.ad_management.dto;


import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

public record AdContentShortResponse (
        Long id,
        String name
){ }
