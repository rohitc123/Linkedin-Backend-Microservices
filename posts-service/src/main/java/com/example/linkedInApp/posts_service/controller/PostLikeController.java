package com.example.linkedInApp.posts_service.controller;

import com.example.linkedInApp.posts_service.service.PostLikeService;
import com.example.linkedInApp.posts_service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePosts(@PathVariable Long postId){
        postLikeService.likePost(postId,1L);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<Void> unlikePost(@PathVariable Long postId){
        postLikeService.unlikePost(postId,1L);
        return ResponseEntity.noContent().build();
    }
}
