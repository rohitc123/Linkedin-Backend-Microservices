package com.example.linkedInApp.posts_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDto {
    private Long id;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;

    private List<PostMediaDto> media;
}
