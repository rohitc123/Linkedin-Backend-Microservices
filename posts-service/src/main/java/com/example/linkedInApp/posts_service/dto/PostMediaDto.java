package com.example.linkedInApp.posts_service.dto;

import lombok.Data;

@Data
public class PostMediaDto {
    private Long id;
    private String secureUrl;
    private String resourceType;
}
