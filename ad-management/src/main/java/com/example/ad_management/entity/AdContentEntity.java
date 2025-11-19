package com.example.ad_management.entity;

import com.example.ad_management.enums.AdStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ad_content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE ad_content SET deleted_at = now() WHERE id = ?")
@SQLRestriction("deleted_at is null")
public class AdContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "published_at")
    private LocalDate publishedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdStatus status;

    @NotBlank
    @Column(name = "advertiser")
    private String advertiser;

    @Column(name = "published")
    private String published;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @NotBlank
    @Column(name = "advertiser_link")
    private String advertiserLink;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private AdAgencyEntity adAgencyEntity;
}
