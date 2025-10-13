package com.example.ad_management.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table
public class AdContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publication_date")
    private LocalDate dateOfPublication;

    @Column(name = "deletion_date")
    private LocalDate dateOfDeletion;

    @NotNull
    @Enumerated(EnumType.STRING)
//    @JdbcTypeCode(SqlTypes.ENUM)
    private AdStatus status;

    private String advertiser;

    private String published;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public LocalDate getDateOfPublication() {
        return dateOfPublication;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setDateOfPublication(LocalDate dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public AdStatus getStatus() {
        return status;
    }

    public void setStatus(AdStatus status) {
        this.status = status;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public LocalDate getDateOfDeletion() {
        return dateOfDeletion;
    }

    public void setDateOfDeletion(LocalDate dateOfDeletion) {
        this.dateOfDeletion = dateOfDeletion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdContent(
            Long id,
            LocalDate dateOfPublication,
            String advertiser,
            String lastModifiedBy,
            String creatorId,
            String published,
            LocalDate dateOfDeletion,
            AdStatus status
    ) {
        this.id = id;
        this.dateOfPublication = dateOfPublication;
        this.advertiser = advertiser;
        this.lastModifiedBy = lastModifiedBy;
        this.creatorId = creatorId;
        this.published = published;
        this.dateOfDeletion = dateOfDeletion;
        this.status = status;
    }

    public AdContent() {
    }

    @Override
    public String toString() {
        return "AdContent{" +
                "id=" + id +
                ", dateOfPublication=" + dateOfPublication +
                ", dateOfDeletion=" + dateOfDeletion +
                ", advertiser='" + advertiser + '\'' +
                ", published='" + published + '\'' +
                ", status=" + status +
                '}';
    }
}
