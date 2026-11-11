package com.example.linkedInApp.posts_service.service;

import com.example.linkedInApp.posts_service.advices.ApiResponse;
import com.example.linkedInApp.posts_service.auth.UserContextHolder;
import com.example.linkedInApp.posts_service.client.ConnectionsClient;
import com.example.linkedInApp.posts_service.client.FileUploadClient;
import com.example.linkedInApp.posts_service.dto.*;
import com.example.linkedInApp.posts_service.entity.Post;
import com.example.linkedInApp.posts_service.entity.PostMedia;
import com.example.linkedInApp.posts_service.event.PostCreatedEvent;
import com.example.linkedInApp.posts_service.exception.BadRequestException;
import com.example.linkedInApp.posts_service.exception.ResourceNotFoundException;
import com.example.linkedInApp.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final ModelMapper modelMapper;
    private final PostsRepository postsRepository;
    private final ConnectionsClient connectionsClient;
    private final FileUploadClient fileUploadClient;

    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTemplate;

    public PostResponseDto  createPost( PostCreateRequestDto postCreateRequest, MultipartFile file, long userId) {
        log.info("post is creating by user: {} ",userId);
        Post post=modelMapper.map(postCreateRequest, Post.class);
        post.setUserId(userId);

        if (file != null && !file.isEmpty()) {

            ApiResponse<FileUploadResponse> uploadResponse =
                    fileUploadClient.uploadFile(file);


            PostMedia postMedia = getPostMedia(uploadResponse, post);
            post.getMedia().add(postMedia);

        }

        Post savedPost=postsRepository.save(post);
        PostCreatedEvent postCreatedEvent= PostCreatedEvent.builder()
                .creatorId(userId)
                .content(savedPost.getContent())
                .postId(savedPost.getId())
                .build();

        kafkaTemplate.send("post-created-topic",postCreatedEvent);


        return modelMapper.map(savedPost, PostResponseDto.class);

    }

    private static PostMedia getPostMedia(ApiResponse<FileUploadResponse> uploadResponse, Post post) {
        FileUploadResponse uploadResponses = uploadResponse.getData();

        // 3. Create PostMedia entity
//            PostMedia postMedia = modelMapper.map(
//                    uploadResponse, PostMedia.class
//            );

        PostMedia postMedia = new PostMedia();

        postMedia.setSecureUrl(uploadResponses.getSecureUrl());
        postMedia.setPublicId(uploadResponses.getPublicId());
        postMedia.setResourceType(uploadResponses.getResourceType());

        postMedia.setPost(post);
        return postMedia;
    }


    public PostResponseDto  getPostById(Long postId) {
        log.info("Retrieving post by id {}",postId);
        Post post = postsRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post not found with id: "+postId));
        return modelMapper.map(post, PostResponseDto .class);
    }

    public List<PostResponseDto > getAllPostsByUser(Long userId) {
        log.info("Fetching all posts written by user: {}", userId);
        List<Post> posts=postsRepository.findByUserId(userId);
        return posts
                    .stream()
                    .map((element) -> modelMapper.map(element, PostResponseDto .class))
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
