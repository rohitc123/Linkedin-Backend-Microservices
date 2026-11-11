package com.example.linkedInApp.posts_service.client;

import com.example.linkedInApp.posts_service.advices.ApiResponse;
import com.example.linkedInApp.posts_service.dto.FileUploadResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "fileupload-service")
public interface FileUploadClient {
    @PostMapping(value = "/file/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ApiResponse<FileUploadResponse> uploadFile(
            @RequestPart("file") MultipartFile file
    );
}
