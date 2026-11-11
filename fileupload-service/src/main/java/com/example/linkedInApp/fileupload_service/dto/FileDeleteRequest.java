package com.example.linkedInApp.fileupload_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDeleteRequest {
    private String publicId;
    private String resourceType;
}
