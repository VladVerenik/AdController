package com.example.ad_management.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ad_agencies")
@Entity
public class AdAgencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agency_name")
    private String agencyName;

    @CreatedDate
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "secure_link")
    private boolean isSecureLink;

    @OneToMany(
            mappedBy = "adAgencyEntity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AdContentEntity> ads = new HashSet<>();
}
