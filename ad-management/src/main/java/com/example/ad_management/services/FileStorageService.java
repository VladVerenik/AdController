package com.example.ad_management.services;

import com.example.ad_management.dto.requests.FileResource;
import com.example.ad_management.exceptions.StorageException;
import com.example.ad_management.exceptions.StorageFileNotFoundException;
import com.example.ad_management.exceptions.ValidationException;
import lombok.Data;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Service
public class FileStorageService implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private final Tika tika;
    private final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            "image/gif",
            "image/webp"
    );

    @Override
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ValidationException("Файл не должен быть пустым");
        }

        validateFileContent(file);

        try {
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            String fileName = file.getOriginalFilename() + "_" + UUID.randomUUID();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (IOException e) {
            throw new StorageException("Ошибка при сохранении файла " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public FileResource loadAsResource(String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new StorageFileNotFoundException("Файл не найден: " + filename);
            }

            String contentType;
            try (InputStream inputStream = resource.getInputStream()) {
                contentType = tika.detect(inputStream);
            }
            return new FileResource(resource, contentType);

        } catch (IOException e) {
            throw new StorageException("Ошибка при чтении файла: " + filename, e);
        }
    }

    @Override
    public void validateFileContent(MultipartFile file) {
        try {
            String detectedMimeType = tika.detect(file.getInputStream());
            if (!ALLOWED_MIME_TYPES.contains(detectedMimeType)) {
                throw new ValidationException("Тип файла '" + detectedMimeType + "' не поддерживается.");

            }
        } catch (IOException e) {
            throw new StorageException("Не удалось прочитать файл для проверки типа", e);
        }
    }

    @Override
    public String buildImageUrl(String fileName) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(fileName)
                .toUriString();
    }
}

