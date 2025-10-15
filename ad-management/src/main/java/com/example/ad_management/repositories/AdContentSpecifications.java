package com.example.ad_management.repositories;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AdContentSpecifications {
    public static Specification<AdContent> adStatus(AdStatus status) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<AdContent> dateOfPublication(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("dateOfPublication"), date);
        };

    }

    public static Specification<AdContent> createAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), date);
        };
    }

    public static Specification<AdContent> createBefore(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), date);
        };
    }
}
