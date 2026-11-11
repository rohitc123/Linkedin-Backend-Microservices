package com.example.linkedInApp.fileupload_service.service;

import com.example.linkedInApp.fileupload_service.dto.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploaderService {
    FileUploadResponse upload(MultipartFile file);

    String delete(String publicId, String resourceType);
}
