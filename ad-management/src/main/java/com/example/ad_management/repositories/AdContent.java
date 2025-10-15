package com.example.ad_management.repositories;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publication_date")
    private LocalDate dateOfPublication;

    @Column(name = "deletion_date")
    private LocalDate dateOfDeletion;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.ENUM)
    private AdStatus status;

    @NotNull
    private String advertiser;

    private String published;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "advertiser_link")
    private String advertiserLink;

    @NotNull
    @Column(name = "image_url")
    private String imageUrl;
}
