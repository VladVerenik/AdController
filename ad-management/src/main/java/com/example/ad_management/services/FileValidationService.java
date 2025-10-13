package com.example.ad_management.services;

import com.example.ad_management.exceptions.ValidationException;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.util.List;

@Service
public class FileValidationService {

    private final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE,
            "image/gif",
            "image/webp"
    );

    private final Tika tika = new Tika();

    public void isValidFileContent(MultipartFile file) throws ValidationException {
        try {
            String detectedMimeType = tika.detect(file.getInputStream());

            if (ALLOWED_MIME_TYPES.contains(detectedMimeType)) {
            } else {
                throw new ValidationException("Обнаруженный тип файла " + detectedMimeType +
                        "не входит в список разрешенных");
            }
        } catch (Exception e) {
            throw new ValidationException("Не удалось определить тип файла", e);
        }
    }
}
