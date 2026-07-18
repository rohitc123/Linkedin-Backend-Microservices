package com.example.linkedInApp.posts_service.controller;

import com.example.linkedInApp.posts_service.dto.PostDto;
import com.example.linkedInApp.posts_service.entity.Post;
import com.example.linkedInApp.posts_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, HttpServletRequest httpServletRequest){
        PostDto createdPost= postsService.createPost(postDto,1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId){
        PostDto post= postsService.getPostById(postId);
        return post!=null ? ResponseEntity.ok(post) : ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable Long userId) {
        List<PostDto> userPosts = postsService.getAllPostsByUser(userId);
        return ResponseEntity.ok(userPosts);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long postId){
        postsService.deletePostById(postId,1L);
        return ResponseEntity.noContent().build();
    }
}
