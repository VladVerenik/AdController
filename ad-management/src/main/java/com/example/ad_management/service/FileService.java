package com.example.ad_management.service;

import com.example.ad_management.dto.request.FileResource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String store (MultipartFile file);
    FileResource loadAsResource(String filename);
    void validateFileContent(MultipartFile file);
}
