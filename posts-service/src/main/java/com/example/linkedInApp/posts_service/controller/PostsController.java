package com.example.linkedInApp.posts_service.controller;

import com.example.linkedInApp.posts_service.auth.UserContextHolder;
import com.example.linkedInApp.posts_service.dto.PostCreateRequestDto;
import com.example.linkedInApp.posts_service.dto.PostDto;
import com.example.linkedInApp.posts_service.dto.PostResponseDto;
import com.example.linkedInApp.posts_service.entity.Post;
import com.example.linkedInApp.posts_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostsController {

    private final PostsService postsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> createPost(
            @RequestPart("post") PostCreateRequestDto postCreateRequest,
            @RequestPart(value = "file", required = false) MultipartFile file,
            HttpServletRequest httpServletRequest) {

        Long userId = UserContextHolder.getCurrentUserId();

        PostResponseDto createdPost =
                postsService.createPost(postCreateRequest, file, userId);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto > getPostById(@PathVariable Long postId){
        Long userId=UserContextHolder.getCurrentUserId();
        log.info("Authenticated userId from context: {}", userId);
        PostResponseDto  post= postsService.getPostById(postId);
        return post!=null ? ResponseEntity.ok(post) : ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getAllPostsByUser(@PathVariable Long userId) {
        List<PostResponseDto > userPosts = postsService.getAllPostsByUser(userId);
        return ResponseEntity.ok(userPosts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId){
        postsService.deletePostById(postId,1L);
        return ResponseEntity.noContent().build();
    }
}
