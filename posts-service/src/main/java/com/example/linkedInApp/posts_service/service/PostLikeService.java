package com.example.linkedInApp.posts_service.service;

import com.example.linkedInApp.posts_service.entity.Post;
import com.example.linkedInApp.posts_service.entity.PostLike;
import com.example.linkedInApp.posts_service.exception.ResourceNotFoundException;
import com.example.linkedInApp.posts_service.repository.PostLikeRepository;
import com.example.linkedInApp.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;

    public void likePost(Long postId, long userId) {
        log.info("Attempting to like post with id: {} by user: {}", postId, userId);

        boolean existPost= postsRepository.existsById(postId);
        if(!existPost){
            throw new ResourceNotFoundException("Cannot like post. Post not found with id: " + postId);
        }

        boolean alreadyLiked=postLikeRepository.existsByPostIdAndUserId(postId,userId);

        if(alreadyLiked){
            throw new ResourceNotFoundException("You have already liked the post");
        }

        PostLike postLike=new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        PostLike savedPost=postLikeRepository.save(postLike);

        log.info("Post with id: {} successfully liked by user: {}", postId, userId);
    }

    public void unlikePost(Long postId, long userId) {
        log.info("Attempting to unlike post with id: {} by user: {}", postId, userId);

        boolean alreadyLiked=postLikeRepository.existsByPostIdAndUserId(postId,userId);
        if(!alreadyLiked){
            throw new ResourceNotFoundException("Cannot unlike. You haven't liked this post yet.");
        }
        postLikeRepository.deleteByPostIdAndUserId(postId, userId);

        log.info("Post with id: {} successfully unliked by user: {}", postId, userId);
    }
}
