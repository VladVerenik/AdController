package com.example.ad_management.models;

import com.example.ad_management.enums.AdStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.time.LocalDate;

@Entity
@Table(name = "ad_content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE ad_content_entity SET is_deleted = true, deletion_date = CURRENT_DATE WHERE id = ?")
@Where(clause = "is_deleted = false")
public class AdContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "publication_date")
    private LocalDate dateOfPublication;

    @Column(name = "deletion_date")
    private LocalDate dateOfDeletion;

//    @Column(name = "creation_date")
//    private LocalDate creationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
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

    @Column(name = "is_deleted")
    private boolean deleted = false;
}
