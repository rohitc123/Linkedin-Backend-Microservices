package com.example.linkedInApp.posts_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadResponse {
    private String secureUrl;
    private String publicId;
    private String resourceType;
}
