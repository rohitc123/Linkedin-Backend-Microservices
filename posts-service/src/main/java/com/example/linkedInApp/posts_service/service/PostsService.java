package com.example.linkedInApp.posts_service.service;

import com.example.linkedInApp.posts_service.dto.PostDto;
import com.example.linkedInApp.posts_service.entity.Post;
import com.example.linkedInApp.posts_service.exception.BadRequestException;
import com.example.linkedInApp.posts_service.exception.ResourceNotFoundException;
import com.example.linkedInApp.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final ModelMapper modelMapper;
    private final PostsRepository postsRepository;

    public PostDto createPost(PostDto postDto, long userId) {
        log.info("post is creating by user: {} ",userId);
        Post post=modelMapper.map(postDto, Post.class);
        post.setUserId(userId);
        Post savedPost=postsRepository.save(post);
        log.info("post is created by user: {} ",userId);
        return modelMapper.map(savedPost,PostDto.class);
    }


    public PostDto getPostById(Long postId) {
        Post post = postsRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post not found with id: "+postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsByUser(Long userId) {
        log.info("Fetching all posts written by user: {}", userId);
        List<Post> posts=postsRepository.findByUserId(userId);
        return posts
                    .stream()
                    .map((element) -> modelMapper.map(element, PostDto.class))
                    .collect(Collectors.toList());
    }

    public void deletePostById(Long postId,Long userId) {
        log.info("Deleting post {}  by user: {}", postId, userId);
        Post post = postsRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));

        if (!post.getUserId().equals(userId)) {
            throw new BadRequestException("You are not authorized to delete this post.");
        }

        postsRepository.delete(post);

        log.info("Post {} deleted successfully by user: {}", postId, userId);
    }
}
