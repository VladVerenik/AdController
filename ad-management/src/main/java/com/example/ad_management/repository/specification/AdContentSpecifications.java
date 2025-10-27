package com.example.ad_management.repository.specification;

import com.example.ad_management.enums.AdStatus;
import com.example.ad_management.entity.AdContentEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class AdContentSpecifications {
    public static Specification<AdContentEntity> adStatus(AdStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<AdContentEntity> dateOfPublication(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("dateOfPublication"), date);
        };

    }

    public static Specification<AdContentEntity> createAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate"), date);
        };
    }

    public static Specification<AdContentEntity> createBefore(LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThanOrEqualTo(root.get("creationDate"), date);
        };
    }

    public static Specification<AdContentEntity> search(String search) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate publishedPredicate = criteriaBuilder.like(root.get("published"), search);
            Predicate advertiserPredicate = criteriaBuilder.like(root.get("advertiser"), search);

            return criteriaBuilder.or(publishedPredicate, advertiserPredicate);
        };
    }
}
