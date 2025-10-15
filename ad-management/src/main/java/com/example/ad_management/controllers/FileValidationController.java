package com.example.ad_management.controllers;

import com.example.ad_management.exceptions.ValidationException;

import com.example.ad_management.services.FileValidationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


@RestController
@RequestMapping("/files")
public class FileValidationController {
    private final FileValidationService fileValidationService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public FileValidationController(FileValidationService fileValidationService) {
        this.fileValidationService = fileValidationService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("Пожалуйста, выберите файл для загрузки", HttpStatus.BAD_REQUEST);
        }

        try {
            fileValidationService.isValidFileContent(file);
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            String fileName = file.getOriginalFilename() + "_" + UUID.randomUUID().toString();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return new ResponseEntity<>("Файл загружен", HttpStatus.OK);
        } catch (ValidationException | IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = Files.probeContentType(filePath);
            HttpHeaders headers = new HttpHeaders();

            if (contentType.startsWith("image/")) {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
            } else {
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .headers(headers)
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
