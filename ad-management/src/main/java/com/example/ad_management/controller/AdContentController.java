package com.example.ad_management.controller;

import com.example.ad_management.mapper.AdMapper;
import com.example.ad_management.dto.request.FilterAdRequest;
import com.example.ad_management.dto.request.UpdateAdRequest;
import com.example.ad_management.dto.response.AdResponse;
import com.example.ad_management.dto.request.CreateAdRequest;
import com.example.ad_management.service.AdService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/v1/ad")
public class AdContentController {

    private final AdService service;
    private final AdMapper mapper;

    @PostMapping
    public AdResponse create(
            @RequestBody CreateAdRequest createRequest,
            @RequestBody String image,
            @RequestHeader("X-Published-By") String publisherId,
            @RequestBody Long id
    ) {
        return service.create(publisherId, createRequest, image, id);
    }

    @GetMapping
    public Page<AdResponse> findAll(
            @SortDefault(
                    sort = "dateOfPublication",
                    direction = Sort.Direction.DESC
            )
            FilterAdRequest filterAdRequest,
            String search,
            Pageable pageable
    ) {
        return service.findAll(
                filterAdRequest,
                search,
                pageable
        );
    }

    @PutMapping("/{id}")
    public AdResponse update(
            @PathVariable Long id,
            @RequestBody UpdateAdRequest updateAdRequest,
            @RequestHeader("X-Published-By") String modifierId
    ) {
        return service.update(id, updateAdRequest, modifierId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(
            @PathVariable Long id,
            @RequestHeader("X-Published-By") String deleterId
    ) {
        service.delete(id, deleterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
