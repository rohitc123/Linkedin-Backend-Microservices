package com.example.linkedInApp.fileupload_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.linkedInApp.fileupload_service.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryFileUploaderService implements FileUploaderService{
    private final Cloudinary cloudinary;

    private static final long MAX_IMAGE_SIZE = 10 * 1024 * 1024;
    private static final long MAX_VIDEO_SIZE = 100 * 1024 * 1024;
    private static final long MAX_PDF_SIZE = 10 * 1024 * 1024;

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        try {

            log.info("uploading the file ");
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Please select a file to upload");
            }
            validateFileSize(file);
            Map uploadResult = cloudinary.uploader()
                    .upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            String resourceType = uploadResult.get("resource_type").toString();

            String secureUrl = uploadResult.get("secure_url").toString();

            String publicId = uploadResult.get("public_id").toString();

            log.info("File uploaded successfully: secureUrl{},resourceType{}", secureUrl,resourceType);
            log.info(resourceType);
            return new FileUploadResponse(secureUrl, publicId,resourceType);

        } catch (IOException ex) {
            log.error("Failed to upload file to Cloudinary", ex);
            throw new RuntimeException("File upload failed", ex);
        }
    }

    private void validateFileSize(MultipartFile file) {

        long fileSize = file.getSize();


        String contentType = file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("File content type is missing");
        }

        long maxSize;

        if (contentType.startsWith("image/")) {
            maxSize = MAX_IMAGE_SIZE;
        } else if (contentType.startsWith("video/")) {
            maxSize = MAX_VIDEO_SIZE;
        } else if (contentType.equals("application/pdf")) {
            maxSize = MAX_PDF_SIZE;
        } else {
            throw new IllegalArgumentException("Only images, videos and PDFs are allowed");
        }
        if (fileSize > maxSize) {
            throw new IllegalArgumentException("File size exceeds the allowed limit");
        }
    }

    @Override
    public String delete(String publicId, String resourceType) {
        log.info("deleting file with publicId:{}",publicId);
        try {
            Map<?, ?> result = cloudinary.uploader().destroy(
                    publicId,
                    Map.of(
                            "resource_type", resourceType,
                            "invalidate", true
                    )
            );

            return result.get("result").toString();

        } catch (Exception ex) {
            log.error("Failed to delete file from Cloudinary", ex);
            throw new RuntimeException("File deletion failed", ex);
        }
    }
}
