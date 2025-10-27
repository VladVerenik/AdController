package com.example.ad_management.controller;

import com.example.ad_management.dto.request.CreateAgenciesRequest;
import com.example.ad_management.dto.request.UpdateAgenciesRequest;
import com.example.ad_management.dto.response.AgencyResponse;
import com.example.ad_management.mapper.AgencyMapper;
import com.example.ad_management.service.AgencyService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/v1/agencies")
public class AgencyController {

    private final AgencyService service;
    private final AgencyMapper mapper;

    @PostMapping
    public AgencyResponse create(@RequestBody CreateAgenciesRequest createAgenciesRequest) {
        return service.create(createAgenciesRequest);
    }

    @GetMapping
    public Page<AgencyResponse> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PutMapping("/{id}")
    public AgencyResponse update(@PathVariable Long id, @RequestBody UpdateAgenciesRequest updateAgenciesRequest) {
        return service.update(id, updateAgenciesRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
