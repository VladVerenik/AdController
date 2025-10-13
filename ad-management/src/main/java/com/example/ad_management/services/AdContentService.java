package com.example.ad_management.services;

import com.example.ad_management.repository.AdContent;
import com.example.ad_management.repository.AdContentRepository;
import com.example.ad_management.repository.AdStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class AdContentService {
    private final AdContentRepository adContentRepository;

    public AdContentService(AdContentRepository adContentRepository) {
        this.adContentRepository = adContentRepository;
    }

    public void deleteAd(Long id, String deleterId) {
        if (deleterId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }

        Optional<AdContent> adContent = adContentRepository.findById(id);
        if(adContent.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого id нету");
        }

        AdContent adToDelete = adContent.get();
        adToDelete.setStatus(AdStatus.DELETED);
        adToDelete.setDateOfDeletion(LocalDate.now());
        adContentRepository.save(adToDelete);

         adContentRepository.deleteById(id);
    }

    public AdContent createAd(String publisherId, AdContent newAd) {
        if (publisherId == null || publisherId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Заголовок X-Published-By обязателен");
        }
        if (newAd.getStatus() == AdStatus.DRAFT ) {
            newAd.setDateOfPublication(null);
        }

        newAd.setCreatorId(publisherId);
        newAd.setLastModifiedBy(publisherId);
        return adContentRepository.save(newAd);
    }

    public Page<AdContent> getAds(
            int pageNumber,
            int pageSize,
            String sortBy,
            String sortDirection,
            String searchTitle
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (searchTitle != null && !searchTitle.isEmpty()) {
            return adContentRepository.findByAdvertiserContainingIgnoreCase(searchTitle, pageable);
        } else {
            return adContentRepository.findAll(pageable);
        }


    }

    public List<AdContent> getAllAds() {
        return adContentRepository.findAll();
    }
}
