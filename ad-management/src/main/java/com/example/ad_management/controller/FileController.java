package com.example.ad_management.controller;

import com.example.ad_management.dto.request.FileResource;
import com.example.ad_management.service.FileService;
import lombok.Data;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@RestController
@RequestMapping("/api/v1/files")
public class FileController {
    private final FileService service;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return service.store(file);

    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        FileResource fileResource = service.loadAsResource(filename);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileResource.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResource.resource().getFilename() + "\"")
                .body(fileResource.resource());
    }
}