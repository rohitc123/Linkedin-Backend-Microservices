package com.example.linkedInApp.fileupload_service.controller;

import com.example.linkedInApp.fileupload_service.advices.ApiResponse;
import com.example.linkedInApp.fileupload_service.dto.FileDeleteRequest;
import com.example.linkedInApp.fileupload_service.dto.FileUploadResponse;
import com.example.linkedInApp.fileupload_service.service.FileUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploaderService fileUploaderService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<FileUploadResponse>> uploadFile(@RequestParam MultipartFile file){
        FileUploadResponse response = fileUploaderService.upload(file);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<String>> deleteFile(
            @RequestBody FileDeleteRequest request) {

        String result = fileUploaderService.delete(request.getPublicId(), request.getResourceType());

        return ResponseEntity.ok(new ApiResponse(result));
    }

}
