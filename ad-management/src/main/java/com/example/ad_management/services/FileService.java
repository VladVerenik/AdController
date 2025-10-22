package com.example.ad_management.services;

import com.example.ad_management.dto.requests.FileResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String store (MultipartFile file);
    FileResource loadAsResource(String filename);
    String buildImageUrl(String fileName);
    void validateFileContent(MultipartFile file);
}
